package agh.ics.oop.proj2;

public enum Side {
    PLAYER_RED,
    PLAYER_BLACK;

    public Side other() {
        return switch (this)
        {
            case PLAYER_RED -> PLAYER_BLACK;
            case PLAYER_BLACK -> PLAYER_RED;
        };
    }
}
