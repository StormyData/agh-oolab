package agh.ics.oop;

public class World {
    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        run(args);
        System.out.println("system zakończył działanie");
    }
    static void run(String[] args)
    {
        for(String arg : args)
        {
            switch(arg)
            {
                case "f":
                    System.out.println("zwierzak idzie do przodu");
                    break;
                case "b":
                    System.out.println("zwierzak idzie do tyłu");
                    break;
                case "r":
                    System.out.println("zwierzak skręca w prawo");
                    break;
                case "l":
                    System.out.println("zwierzak skręca w lewo");
                    break;
            }
        }
    }
}
