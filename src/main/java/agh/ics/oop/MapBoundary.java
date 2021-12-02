package agh.ics.oop;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver{

    static private final Comparator<Vector2d> comparatorX=new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            if(o1.x!=o2.x)
                return Integer.compare(o1.x,o2.x);
            return Integer.compare(o1.y,o2.y);
        }
    };
    static private final Comparator<Vector2d> comparatorY=new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            if(o1.y!=o2.y)
                return Integer.compare(o1.y,o2.y);
            return Integer.compare(o1.x,o2.x);
        }
    };
    private SortedMap<Vector2d,Integer> xPositions = new TreeMap<>(comparatorX);
    private SortedMap<Vector2d,Integer> yPositions = new TreeMap<>(comparatorY);

    private void addAtPos(Vector2d pos)
    {
        if(xPositions.containsKey(pos))
            xPositions.put(pos,xPositions.get(pos)+1);
        else
            xPositions.put(pos,1);

        if(yPositions.containsKey(pos))
            yPositions.put(pos,yPositions.get(pos)+1);
        else
            yPositions.put(pos,1);
    }
    private void removeFromPos(Vector2d pos)
    {
        if(!xPositions.containsKey(pos))
            throw new IllegalArgumentException(String.format("unable to remove object from %s",pos));
        if(!yPositions.containsKey(pos))
            throw new IllegalArgumentException(String.format("unable to remove object from %s",pos));
        if(xPositions.get(pos)==1)
            xPositions.remove(pos);
        else
            xPositions.put(pos,xPositions.get(pos)-1);
        if(yPositions.get(pos)==1)
            yPositions.remove(pos);
        else
            yPositions.put(pos,yPositions.get(pos)-1);
    }
    public void addMapObject(BaseWorldMapElement el)
    {
        if(el instanceof IPositionChangedObservable observable)
            observable.addObserver(this);
        addAtPos(el.getPosition());
    }
    public Vector2d getLowerLeft()
    {
        return new Vector2d(xPositions.firstKey().x,yPositions.firstKey().y);
    }
    public Vector2d getUpperRight()
    {
        return new Vector2d(xPositions.lastKey().x,yPositions.lastKey().y);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        removeFromPos(oldPosition);
        addAtPos(newPosition);
    }
}
