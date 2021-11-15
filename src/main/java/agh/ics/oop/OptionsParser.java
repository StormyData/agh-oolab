package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    MoveDirection[] parse(String[] strArr)
    {
        MoveDirection[] array = new MoveDirection[strArr.length];
        int i=0;
        for(String str : strArr)
        {
            switch (str) {
                case "f", "forward" -> {
                    array[i] = MoveDirection.FORWARD;
                    i++;
                }
                case "b", "backward" -> {
                    array[i] = MoveDirection.BACKWARD;
                    i++;
                }
                case "l", "left" -> {
                    array[i] = MoveDirection.LEFT;
                    i++;
                }
                case "r", "right" -> {
                    array[i] = MoveDirection.RIGHT;
                    i++;
                }
                default -> {
                }
            }
        }
        return Arrays.copyOfRange(array,0,i);
    }
}
