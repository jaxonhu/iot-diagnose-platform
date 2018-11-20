package device;

public abstract class Device {

    // 动态变化指标
    public Object[] fields;

    public String[] names;

    public String[] types;

    public int fieldsNum;

    // feature功能指标
    public Object[] features;

    public String[] feature_names;

    public String[] feature_type;

    public int feature_num;

    // dump current state
    public abstract void onServiceChange();
    // notify device's fields change
    public abstract void onStateChange();
    // receive command and do some reaction
    public abstract void recvCommand(String command);

}
