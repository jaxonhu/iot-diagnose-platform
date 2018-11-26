package scene;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import device.TraceNode;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Device {

    // 动态变化指标
    public Object[] fields;

    public String[] names;

    public String[] types;

    int fieldsNum;

    // feature功能指标
    public Object[] features;

    public String[] feature_names;

    public String[] feature_type;

    public int feature_num;

    //保存执行轨迹 json 结构化
    public List<String> traces;

    //邻接表表示状态图 (应该是一条无分支trace)
    public TraceNode head, tail;

    // dump current state
    protected abstract void onServiceChange();
    // notify device's fields change
    protected abstract void onStateChange();
    // receive command and do some reaction
    public abstract void recvCommand(String command);
    //dump trace to string
    public abstract String dumpCurrentTrace(TraceNode head);

    void parseDescription(String dslJson) {

        JsonIterator iter = JsonIterator.parse(dslJson);
        JsonIterator iter2 = JsonIterator.parse(dslJson);
        try{
            //read fields num
            Any conf = iter2.readAny();
            int size = conf.get("fields").size();
            int feature_size = conf.get("feature").size();
            fieldsNum = size;
            //initialize
            fields = new Object[size+1];
            names = new String[size+1];
            types = new String[size+1];
            features = new Object[feature_size+1];
            feature_names = new String[feature_size+1];
            feature_type = new String[feature_size+1];
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
                                        index = iter.readInt();
                                        break;
                                    case "name":
                                        name = iter.readString();
                                        break;
                                    case "type":
                                        type = iter.readString();
                                        break;
                                    default:
                                        System.out.println("parse error");
                                }
                            }
                            names[index] = name;
                            types[index] = type;
                        }
                        break;
                    case "feature":
                        //Todo
                        while(iter.readArray()) {
                            iter.skip();
                        }
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
