package tabuvrp.core.stage;

import java.util.HashSet;


public abstract class Stage {

    private HashSet<StageListener> listeners;
    private boolean stopRequired;
    private boolean stopped;

    public Stage() {
        listeners = new HashSet<StageListener>();
        stopRequired = false;
        stopped = false;
    }

    public void addStageListener(StageListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("cannot add null as listener");
        }
        listeners.add(listener);
    }

    public void remStageListener(StageListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void runStage() {
        if (stopped) {
            return;
        }
        notifyAll_StageStarted();
        while (!stopRequired && doStep()) {
            notifyAll_StepDone();
        }
        stopped = true;
        notifyAll_StageStopped();
    }

    public void stopStage() {
        stopRequired = true;
    }

    protected void notifyAll_StageStarted() {
        for (StageListener listener : listeners) {
            listener.stageStarted(this);
        }
    }

    protected void notifyAll_StepDone() {
        for (StageListener listener : listeners) {
            listener.stepDone(this);
        }
    }

    protected void notifyAll_StageStopped() {
        for (StageListener listener : listeners) {
            listener.stageStopped(this);
        }
    }

    protected abstract boolean doStep();

}
