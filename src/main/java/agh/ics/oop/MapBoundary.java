package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;
// ladniejsza implementacja z sorted multiset<Vector2d>
// multiset np. na SotredMap<vector2d,int> gdzie int to ilosc wystapien
public class MapBoundary {

    protected static class BoundaryXElement implements Comparable<BoundaryXElement>,IPositionChangeObserver
    {
        private final int elementClassHashCode;
        private Vector2d pos;
        private final SortedSet<BoundaryXElement> treeSet;
        BoundaryXElement(BaseWorldMapElement element, SortedSet<BoundaryXElement> treeSet)
        {
            pos=element.getPosition();
            elementClassHashCode=element.getClass().hashCode();
            this.treeSet = treeSet;
            this.treeSet.add(this);
            if(element instanceof IPositionChangedObservable observable)
                observable.addObserver(this);
        }
        @Override
        public int compareTo(BoundaryXElement o) {
            if(pos.x!=o.pos.x)
                return Integer.compare(pos.x,o.pos.x);
            if(pos.y!=o.pos.y)
                return Integer.compare(pos.y,o.pos.y);
            return Integer.compare(elementClassHashCode,o.elementClassHashCode);
        }
        public Vector2d getPosition()
        {
            return pos;
        }

        @Override
        public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
            treeSet.remove(this);
            pos=newPosition;
            treeSet.add(this);
        }
    }
    protected static class BoundaryYElement implements Comparable<BoundaryYElement>,IPositionChangeObserver
    {
        private final int elementClassHashCode;
        private Vector2d pos;
        private final SortedSet<BoundaryYElement> treeSet;
        BoundaryYElement(BaseWorldMapElement element, SortedSet<BoundaryYElement> treeSet)
        {
            pos=element.getPosition();
            elementClassHashCode=element.getClass().hashCode();
            this.treeSet = treeSet;
            this.treeSet.add(this);
            if(element instanceof IPositionChangedObservable observable)
                observable.addObserver(this);
        }
        @Override
        public int compareTo(BoundaryYElement o) {
            if(pos.y!=o.pos.y)
                return Integer.compare(pos.y,o.pos.y);
            if(pos.x!=o.pos.x)
                return Integer.compare(pos.x,o.pos.x);
            return Integer.compare(elementClassHashCode,o.elementClassHashCode);
        }
        public Vector2d getPosition()
        {
            return pos;
        }
        @Override
        public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
            treeSet.remove(this);
            pos=newPosition;
            treeSet.add(this);
        }
    }

    SortedSet<BoundaryXElement> xPositions = new TreeSet<>();
    SortedSet<BoundaryYElement> yPositions = new TreeSet<>();


    public void addMapObject(BaseWorldMapElement el)
    {
        new BoundaryXElement(el, xPositions);
        new BoundaryYElement(el, yPositions);
    }
    public Vector2d getLowerLeft()
    {
        return new Vector2d(xPositions.first().getPosition().x,yPositions.first().getPosition().y);
    }
    public Vector2d getUpperRight()
    {
        return new Vector2d(xPositions.last().getPosition().x,yPositions.last().getPosition().y);
    }
}
