package scene;

import device.TraceNode;
import util.Utils;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class TempSensor extends Device {


    public boolean[] isRunning = new boolean[this.fieldsNum+1];

    public ExecutorService threadPool;

    public TempSensor(String config) {
        parseDescription(config);
        threadPool = Executors.newFixedThreadPool(this.fieldsNum);
    }



    @Override
    public void onServiceChange() {
        //Todo
        // dump 状态图 json 串
        System.out.println("device tempSensor change service to ——>");

    }

    @Override
    public void onStateChange() {
        //Todo
        // write to file or database  json串
        // 进程通信 socket
        System.out.println("device tempSensor change state to ——>" + fields[1]);
    }

    @Override
    public void recvCommand(String command) {
        //根据command 控制 feature

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
        isRunning = new boolean[this.fieldsNum+1];
        Arrays.fill(isRunning, true);
        Runnable r = () -> {
            while(isRunning[fieldId]) {
                Object value = input.apply(this);
                String type = types[fieldId];
                Object castedValue = Utils.castValue(type, value);
                fields[fieldId] = castedValue;
                try {
                    onStateChange();
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.execute(r);
    }

    public TempSensor(boolean[] isRunning, ExecutorService threadPool) {
        this.isRunning = isRunning;
        this.threadPool = threadPool;
    }



}
