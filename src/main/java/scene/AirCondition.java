package scene;

import device.TraceNode;
import util.Utils;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class AirCondition extends Device {


    public boolean[] isRunning;

    public ExecutorService threadPool;


    public AirCondition(String config) {
        parseDescription(config);
        threadPool = Executors.newFixedThreadPool(this.fieldsNum);
    }

    @Override
    public void onServiceChange() {
        System.out.println("device airCondition service " + feature_names[1] + " Change to ——>" + (String)features[1]);
    }

    @Override
    public void onStateChange() {
        System.out.println("device airCondition change state");
    }

    @Override
    public void recvCommand(String command) {
        //Todo 调用service


        switch(command) {
            case "cold":
                //Todo;
            case "warm":
                //Todo;
            default:
                //Todo;
        }
    }

    @Override
    public String dumpCurrentTrace(TraceNode head) {
        return null;
    }

    public void dataBindFields1(int fieldId, Function<Device, Object> input) {
        isRunning = new boolean[this.fieldsNum];
        Arrays.fill(isRunning, true);
        Runnable r = () -> {
            while(isRunning[fieldId]) {
                Object value = input.apply(this);
                String type = types[fieldId];
                Object castedValue = Utils.castValue(type, value);
                fields[fieldId] = castedValue;
                try {
                    onStateChange();
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(r);

    }


}
