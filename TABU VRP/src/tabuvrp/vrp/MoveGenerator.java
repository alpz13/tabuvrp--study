package tabuvrp.vrp;

import java.util.Set;
import java.util.HashSet;


public class MoveGenerator {

    protected final Integer[][] genMat;
    protected final Integer size;
    protected MoveGenFilter filter;

    public MoveGenerator(Integer[][] generationMatrix) {
        size = generationMatrix.length;
        genMat = generationMatrix;
    }

    public Set<Integer> extract(Integer row, int count) {
        HashSet<Integer> extSet = new HashSet<Integer>();
        int i = 0;
        while (extSet.size() < count &&
               i < genMat[row].length) {
            if (filter != null &&
                !filter.filter(row, genMat[row][i])) {
                extSet.add(genMat[row][i]);
            }
            i += 1;
        }
        return extSet;
    }

    public void setFilter(MoveGenFilter filter) {
        this.filter = filter;
    }

}
