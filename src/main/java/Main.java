import Condition.Condition;
import scene.AirCondition;
import scene.Device;
import scene.TempSensor;
import scene.TemptureAdjust;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String tempSensorConf = "{\"fields\":[{\"index\":1,\"name\":\"tempture\",\"type\":\"int\"}]}";
        String airConditionConf = "{\"fields\":[{\"index\":1,\"name\":\"tempture\",\"type\":\"int\"}],\"feature\":[{\"index\":1,\"name\":\"mode\",\"type\":\"string\"}]}";

        Device airCondition = new AirCondition(airConditionConf);
        Device tempSensor = new TempSensor(tempSensorConf);

//        ((AirCondition) airCondition).dataBindFields1(1, device -> {
//            AirCondition ac = (AirCondition) device;readString
//            String feature = (String)ac.features[0]; // 制冷 or 制热
//            int temp = 0;
//            if(feature.equals("warm")) {
//                 temp = new Random(System.currentTimeMillis()).nextInt(10) + 20;
//            }else if(feature.equals("cold")) {
//                 temp = new Random(System.currentTimeMillis()).nextInt(10) + 10;
//            }
//            return temp;
//        });

        ((TempSensor) tempSensor).dataBindFields1(1, device -> {
            TempSensor ts = (TempSensor) device;
            int temp = new Random(System.currentTimeMillis()).nextInt(20) + 10;
            return temp;
        });


        TemptureAdjust ta = new TemptureAdjust(tempSensor, airCondition);

        ta.setCondition(new Condition() {
            @Override
            public boolean ifCondition(Device deviceSource) {
                int threshold = 25;
                if((int)deviceSource.fields[1] > threshold)
                    return true;
                return false;
            }

            @Override
            public void trigger(Device deviceTarget) {
                deviceTarget.features[1] = "making cold";

            }
        });

        ta.run();


    }

 }
