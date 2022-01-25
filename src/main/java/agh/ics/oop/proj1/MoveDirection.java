package agh.ics.oop.proj1;

public enum MoveDirection {
    FORWARD(0),
    RIGHT45(1),
    RIGHT(2),
    RIGHT135(3),
    BACKWARD(0),
    LEFT135(-3),
    LEFT(-2),
    LEFT45(-1);

    final private int facingDelta;
    MoveDirection(int facingDelta) {
        this.facingDelta = facingDelta;
    }
    public MapDirection newFacing(MapDirection facing)
    {
        return facing.next(facingDelta);
    }
    public Vector2d newPosition(MapDirection facing, Vector2d oldPosition)
    {
        return switch (this)
                {
                    case FORWARD -> oldPosition.add(facing.toUnitVector());
                    case BACKWARD -> oldPosition.subtract(facing.toUnitVector());
                    default -> oldPosition;
                };
    }

}
