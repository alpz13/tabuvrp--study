package tabuvrp.vrp;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class PriorityGenerator {

    protected final Integer[][] genMat;
    protected final Integer size;

    public PriorityGenerator(Integer[][] generationMatrix) {
        size = generationMatrix.length;
        genMat = generationMatrix;
    }

    public Set<Integer> extract(Integer row, int count, Filter<Integer, Integer> filter) {
        HashSet<Integer> extSet = new HashSet<Integer>();
        int i = 0;
        while (extSet.size() < count &&
               i < genMat[row].length) {
            if (!filter.filter(row, genMat[row][i])) {
                extSet.add(genMat[row][i]);
            }
            i += 1;
        }
        return extSet;
    }

}
