package agh.ics.oop.proj2;

import agh.ics.oop.Vector2d;

public record HighlightData(Vector2d pos, HighlightType type) {
    public enum HighlightType {
        PATH,
        BEGIN,
        RESET,
        COMMIT
    }
}
