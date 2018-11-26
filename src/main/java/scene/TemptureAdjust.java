package scene;

import Condition.Condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TemptureAdjust {

    public Device ifDevice;

    public Device thenDevice;

    private static boolean isRunning = true;

    private Condition condition;

    public ExecutorService es = Executors.newSingleThreadExecutor();

    private Runnable task;

    public TemptureAdjust(Device ifDevice, Device thenDevice) {
        this.ifDevice = ifDevice;
        this.thenDevice = thenDevice;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void run() {
        assert condition != null;
        assert ifDevice != null;
        assert thenDevice != null;
        task = new CheckTask(condition, ifDevice, thenDevice);
        es.execute(task);
    }

    class CheckTask implements Runnable {

        private Condition condition;
        private Device ifDevice;
        private Device thenDevice;

        public CheckTask(Condition condition, Device ifDevice, Device thenDevice) {
            this.condition = condition;
            this.ifDevice = ifDevice;
            this.thenDevice = thenDevice;
        }

        @Override
        public void run() {
            while(isRunning) {
                if(condition.ifCondition(ifDevice)) {
                    condition.trigger(thenDevice);
                   thenDevice.onServiceChange();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
