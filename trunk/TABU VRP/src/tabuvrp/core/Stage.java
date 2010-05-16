package tabuvrp.core;

import java.util.HashSet;


public abstract class Stage {

    private HashSet<StageListener> listeners;

    public Stage() {
        listeners = new HashSet<StageListener>();
    }

    public void addStageListener(StageListener listener) {
        if (listeners == null) {
            throw new IllegalArgumentException("cannot add null as listener");
        }
        listeners.add(listener);
    }

    public void remStageListener(StageListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public void notifyAll_StepDone() {
        for (StageListener listener : listeners) {
            listener.stepDone();
        }
    }

    public abstract void runStage();

    public abstract void stopStage();
}
