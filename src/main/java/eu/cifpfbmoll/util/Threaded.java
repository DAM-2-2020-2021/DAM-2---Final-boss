package eu.cifpfbmoll.util;

public abstract class Threaded implements Runnable {
    private Thread thread = null;
    protected volatile boolean run = false;
    protected volatile boolean paused = false;

    /**
     * Start thread.
     * If the thread is not created, it will initialize it.
     * If the thread is already created, it will resume execution.
     *
     * @see Threaded#resume()
     */
    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this, getClass().getSimpleName());
            this.run = true;
            this.thread.start();
        } else {
            resume();
        }
    }

    /**
     * Resume the execution of the thread.
     */
    public void resume() {
        this.paused = false;
    }

    /**
     * Wait for the thread to end.
     *
     * @return true if there were no errors, false otherwise
     */
    public boolean join() {
        return join(-1);
    }

    /**
     * Wait for the thread to end with a timeout.
     *
     * @param millis Timeout in milliseconds
     * @return true if there were no errors, false otherwise
     */
    public boolean join(int millis) {
        if (this.thread == null) return false;
        try {
            if (millis < 0)
                this.thread.join();
            else
                this.thread.join(millis);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Stop the execution of the thread.
     */
    public void stop() {
        this.run = false;
        this.thread = null;
    }
}
