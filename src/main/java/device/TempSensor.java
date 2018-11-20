package device;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.javatuples.Tuple;
import util.Tuple3;
import util.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

public class TempSensor extends Device {


    public boolean[] isRunning = new boolean[this.fieldsNum];

    public TempSensor(String config) {
        Arrays.fill(isRunning, true);
        parseDescription(config);
    }

    public void parseDescription(String dslJson) {

        JsonIterator iter = JsonIterator.parse(dslJson);
        JsonIterator iter2 = JsonIterator.parse(dslJson);
        try{
            //read fields num
            Any conf = iter.readAny();
            int size = conf.get("fields").size();
            //initialize
            fields = new Object[size];
            names = new String[size];
            types = new String[size];
            //
            for(String field = iter.readObject() ; field != null ; field = iter.readObject()) {
                switch(field) {
                    case "fields":
                        while(iter.readArray()) {
                            int index = 0;
                            String name = "";
                            String type = "";
                            for(String field2 = iter.readObject() ; field2 != null ; field2 = iter.readObject()) {
                                switch(field2) {
                                    case "index":
                                        index = Integer.valueOf(field2);
                                        break;
                                    case "name":
                                        name = field2;
                                        break;
                                    case "type":
                                        type = field2;
                                        break;
                                    default:
                                        System.out.println("parse error");
                                }
                            }
                            names[index] = name;
                            types[index] = type;
                        }
                        break;
                    case "features":
                        //Todo
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceChange() {
        //Todo
        // dump 状态图 json 串

    }

    @Override
    public void onStateChange() {
        //Todo
        // write to file or database  json串
        // 进程通信 socket

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

    public ExecutorService threadPool = Executors.newFixedThreadPool(this.fieldsNum);

    public void dataBindFields1(int fieldId, Function<Integer, Object> input) {

        Runnable r = () -> {
            while(isRunning[fieldId]) {
                Object value = input.apply(fieldId);
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
        threadPool.submit(r);
    }



}
