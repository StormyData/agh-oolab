package agh.ics.oop;


import java.io.IOError;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        try
        {
            run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("system zakończył działanie");


    }
    private static void run()
    {
        Vector2d position1 = new Vector2d(1, 2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        MapDirection md = MapDirection.EAST;
        for (int i = 0; i < 4; i++)
        {
            System.out.println(md);
            md=md.next();
        }
        for (int i = 0; i < 4; i++)
        {
            System.out.println(md);
            md=md.previous();
        }

    }


}
