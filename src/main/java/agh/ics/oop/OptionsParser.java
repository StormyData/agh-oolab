package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    MoveDirection[] parse(String[] strArr)
    {
        MoveDirection[] array = new MoveDirection[strArr.length];
        int i=0;
        for(String str : strArr)
        {
            switch (str)
            {
                case "f":
                case "forward":
                    array[i]=MoveDirection.FORWARD;
                    i++;
                    break;
                case "b":
                case "backward":
                    array[i]=MoveDirection.BACKWARD;
                    i++;
                    break;
                case "l":
                case "left":
                    array[i]=MoveDirection.LEFT;
                    i++;
                    break;
                case "r":
                case "right":
                    array[i]=MoveDirection.RIGHT;
                    i++;
                    break;
                default:
                    break;
            }
        }
        return Arrays.copyOfRange(array,0,i);
    }
}
