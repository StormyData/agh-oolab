package agh.ics.oop;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;
    public String toString()
    {
        return  switch (this)
                {
                    case NORTH -> "Północ";
                    case EAST ->  "Wshód";
                    case WEST -> "Zachód";
                    case SOUTH -> "Południe";
                };
    }
    public MapDirection next()
    {
        return MapDirection.values()[(this.ordinal()+1) % MapDirection.values().length];
    }
    public MapDirection previous()
    {
        return MapDirection.values()[(this.ordinal()-1 + MapDirection.values().length) % MapDirection.values().length];
    }
    public Vector2d toUnitVector()
    {
        return  switch (this)
                {
                    case NORTH -> new Vector2d(0,1);
                    case EAST ->  new Vector2d(1,0);
                    case WEST -> new Vector2d(-1,0);
                    case SOUTH -> new Vector2d(0,-1);
                };
    }
}
