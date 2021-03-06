package util;

public class Utils {


    public static Object castValue(String type, Object origin) {

        Object res = null;
        switch (type) {
            case "String":
                res = (String) origin;
            case "int":
                res = Integer.valueOf(String.valueOf(origin));
            case "double":
                res = Double.valueOf(String.valueOf(origin));
            default:    // 默认透传
                res = origin;
        }

        return res;
    }
}
