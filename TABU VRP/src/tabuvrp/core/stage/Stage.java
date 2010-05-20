package tabuvrp.core.stage;

import java.util.HashSet;


public abstract class Stage {

    private HashSet<StageListener> listeners;
    private boolean stopRequired;
    private boolean started;
    private boolean stopped;
    private long startTime;
    private long stopTime;

    public Stage() {
        listeners = new HashSet<StageListener>();
        stopRequired = false;
        started = false;
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
        if (started) {
            return;
        }
        started = true;
        startTime = System.nanoTime();
        notifyAll_StageStarted();
        while (!stopRequired) {
            if (doStep()) {
                notifyAll_StepDone();
            }
            else {
                notifyAll_StepDone();
                break;
            }
        }
        stopped = true;
        stopTime = System.nanoTime();
        notifyAll_StageStopped();
    }

    public void stopStage() {
        stopRequired = true;
    }

    public long getStartTime() {
        return started? startTime : 0;
    }

    public long getStopTime() {
        return stopped? stopTime : 0;
    }

    public long getElaborationTime() {
        if (started && stopped) {
            return stopTime - startTime;
        }
        else return 0;
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
