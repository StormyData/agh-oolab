package agh.ics.oop;


public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        try
        {
            run(args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("system zakończył działanie");


    }
    private static void run(String[] args)
    {
        OptionsParser parser=new OptionsParser();
        Animal animal=new Animal();
        MoveDirection[] arr = parser.parse(args);
        for(MoveDirection moveDir : arr)
        {
            animal.move(moveDir);
        }
        System.out.println(animal);
    }


}
