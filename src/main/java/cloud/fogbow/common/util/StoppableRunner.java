package cloud.fogbow.common.util;

public abstract class StoppableRunner implements Runnable {

    private boolean mustStop;
    private boolean isActive;
    private long sleepTime;

    public StoppableRunner(Long sleepTime) {
        this.sleepTime = sleepTime;
        this.isActive = false;
        this.mustStop = false;
    }
    
    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }
    
    public void stop() {
        this.mustStop = true;
        while (isActive()) {
            try {
                // TODO Currently this stop method only works with this sleep
                // Needs further investigation
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }            
        this.mustStop = false;
    }

    private void checkIfMustStop() { 
        if (this.mustStop) {
            this.isActive = false;
        }
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    @Override
    public void run() {
        this.isActive = true;
        while (isActive) {
            try {
                doRun();
                Thread.sleep(this.sleepTime);
                checkIfMustStop();
            } catch (InterruptedException e) {
                isActive = false;
            }           
        }
    }
    
    public abstract void doRun() throws InterruptedException;
}
