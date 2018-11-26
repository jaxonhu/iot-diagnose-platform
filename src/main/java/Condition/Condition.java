package Condition;

import scene.Device;

public interface Condition {

    public boolean ifCondition(Device deviceSource);

    public void trigger(Device deviceTarget);
}
