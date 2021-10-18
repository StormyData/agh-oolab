package agh.ics.oop;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.Arrays;

public class World {
    public static void main(String[] args)
    {
        System.out.println("system wystartował");
        run(arg_converter(Arrays.stream(args)));
        System.out.println("system zakończył działanie");
    }

    private static Stream<Direction> arg_converter(Stream<String> args) {
        final Collection<String> symbols = Arrays.asList("f", "b", "l", "r");
        return args.filter(x -> symbols.contains(x)).map(x->
            switch (x) {
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "l" -> Direction.LEFT;
                case "r" -> Direction.RIGHT;
                default -> Direction.FORWARD;
            });
    }

    private static void run(Stream<Direction> args)
    {
        args.map(x->switch(x)
            {
                case FORWARD -> "zwierzak idzie do przodu";
                case BACKWARD -> "zwierzak idzie do tyłu";
                case RIGHT -> "zwierzak skręca w prawo";
                case LEFT -> "zwierzak skręca w lewo";
            }).forEach(x->System.out.println(x));
    }
}
