package agh.ics.oop;

import java.util.Arrays;

public class World {
    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        run(arg_converter(args));
        System.out.println("system zakończył działanie");
    }

    private static Direction[] arg_converter(String[] args) {
        Direction[] arr= new Direction[args.length];
        int i=0;
        for(String arg : args)
        {
            switch (arg)
            {
                case "f":
                    arr[i++]=Direction.FORWARD;
                    break;
                case "b":
                    arr[i++]=Direction.BACKWARD;
                    break;
                case "l":
                    arr[i++]=Direction.LEFT;
                    break;
                case "r":
                    arr[i++]=Direction.RIGHT;
                    break;

            }
        }
        return Arrays.copyOfRange(arr,0,i);
    }

    private static void run(Direction[] args)
    {
        for(Direction arg : args)
        {
            System.out.println(switch(arg)
            {
                case FORWARD -> "zwierzak idzie do przodu";
                case BACKWARD -> "zwierzak idzie do tyłu";
                case RIGHT -> "zwierzak skręca w prawo";
                case LEFT -> "zwierzak skręca w lewo";
            });
        }
    }
}
