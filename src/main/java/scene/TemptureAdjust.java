package scene;

import device.Device;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TemptureAdjust {

    public Device airCondition;

    public Device tempSensor;

    private static boolean isRunning = false;

    private Condition condition;

    public ExecutorService es = Executors.newSingleThreadExecutor();

    private Runnable task = new CheckTask(condition, tempSensor, airCondition);


    public void run() {
        es.submit(task);
    }

    class CheckTask implements  Runnable {

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
                if(condition.ifCondition(ifDevice))
                    condition.trigger(thenDevice);
            }
        }
    }


    class Condition {

        public boolean ifCondition(Device deviceSource) {
            //Todo
            return true;
        }

        public void trigger(Device deviceTarget) {
            //todo
            //触发某个service
        }

    }

}
