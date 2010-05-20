package tabuvrp.core.move;

import tabuvrp.core.move.Move;
import tabuvrp.core.Path;


public final class Move10 extends Move {

    private final Path sourcePath;
    private final Integer sourceNode;
    private final Path targetPath;
    private final Integer targetNode;
    private final int position;

    public Move10(Path sourcePath, Integer sourceNode,
                  Path targetPath, Integer targetNode,
                  int position,
                  double deltaCost,
                  int deltaDemandBalance) {
        super(deltaCost, deltaDemandBalance);
        this.sourcePath = sourcePath;
        this.sourceNode = sourceNode;
        this.targetPath = targetPath;
        this.targetNode = targetNode;
        this.position = position;
    }

    public final Path getSourcePath() {
        return sourcePath;
    }

    public final Integer getSourceNode() {
        return sourceNode;
    }

    public final Path getTargetPath() {
        return targetPath;
    }

    public final Integer getTargetNode() {
        return targetNode;
    }
    
    public final int getPosition() {
        return position;
    }
}
