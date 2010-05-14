package tabuvrp.vrp;

import java.util.HashSet;
import java.util.HashMap;


public class TabuIndex<S, T> {

    protected final HashMap<T, HashSet<S>> tgtToSrc;
    protected final HashMap<Integer, HashSet<Tuple2<S, T>>> expirations;
    protected final int maxExpTime;
    protected int now;

    public TabuIndex(int maxExpirationTime) {
        tgtToSrc = new HashMap<T, HashSet<S>>();
        expirations = new HashMap<Integer, HashSet<Tuple2<S, T>>>();
        maxExpTime = maxExpirationTime;
        now = 0;
    }

    public boolean isTabu(S src, T tgt) {
        return src != null &&
               tgt != null &&
               tgtToSrc.containsKey(tgt) &&
               tgtToSrc.get(tgt).contains(src);
    }

    public void setTabu(S src, T tgt, int expireAfter) {
        if (   expireAfter < 0
            || expireAfter > maxExpTime) {
            return;
        }
        if (   src == null
            || tgt == null ) {
            throw new IllegalArgumentException("cannot set null values as tabu");
        }

        HashSet<S> set = tgtToSrc.get(tgt);
        if (set == null) {
            /* Target DOESN'T HAVE a tabu set */
            //   1) create a new tabu set:
            set = new HashSet<S>();
            //   2) link it to the target:
            tgtToSrc.put(tgt, set);
        }
        else {
            /* Target HAS a tabu set */
            if (set.contains(src)) {
                // source is already in target's tabu set:
                return;
            }
        }

        /* The set is now linked to target */
        //   3) add source to target's tabu set:
        set.add(src);


        /* Schedule the expiration */
        Tuple2<S, T> expRecord = new Tuple2<S, T>(src, tgt);
        Integer expTime = (now + expireAfter + 1) % (maxExpTime + 1);
        HashSet<Tuple2<S, T>> expSet;

        if (expirations.containsKey(expTime)) {
            expSet = expirations.get(expTime);
        }
        else {
            expSet = new HashSet<Tuple2<S, T>>();
            expirations.put(expTime, expSet);
        }

        /* The expiration set is now linked to the expiration date */
        //   add the expiration record to the set:
        expSet.add(expRecord);
    }

    public void step() {
        now = (now + 1) % (maxExpTime + 1);
        
        if (!expirations.containsKey(now)) {
            return;
        }
        
        HashSet<Tuple2<S, T>> expSet = expirations.get(now);
        for (Tuple2<S, T> expRecord : expSet) {
            HashSet<S> sources = tgtToSrc.get(expRecord.getSecond());
            if (sources != null) {
                sources.remove(expRecord.getFirst());
            }
        }
        expSet.clear();
    }

}
