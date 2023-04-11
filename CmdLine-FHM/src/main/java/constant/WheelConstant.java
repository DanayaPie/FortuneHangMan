package constant;

import java.util.ArrayList;
import java.util.List;

public class WheelConstant {

    public static final List<String> wheel() {

        List<String> wheel = new ArrayList<>();
        wheel.add("500");
        wheel.add("550");
        wheel.add("600");
        wheel.add("650");
        wheel.add("700");
        wheel.add("900");
        wheel.add("1000");
        wheel.add("LOSE A TURN");
        wheel.add("500");
        wheel.add("550");
        wheel.add("BANKRUPT");
        wheel.add("600");
        wheel.add("650");
        wheel.add("700");
        wheel.add("900");
        wheel.add("1000");
        wheel.add("LOSE A TURN");
        wheel.add("3500");
        wheel.add("BANKRUPT");
        wheel.add("TOKEN");

        return wheel;
    }

    public static final List<String> noneNumWheelResult() {

        List<String> noneNumWheelResult = new ArrayList<>();
        noneNumWheelResult.add("LOSE A TURN");
        noneNumWheelResult.add("BANKRUPT");
        noneNumWheelResult.add("TOKEN");

        return noneNumWheelResult;
    }
}
