package device;

public class AirCondition extends Device {

    @Override
    public void onChange() {
        //Todo 保存 snapshot
    }

    @Override
    public void recvCommand(String command) {
        //Todo 调用service

        this.onChange();
    }


    public void dataBindFields1() {

    }
}
