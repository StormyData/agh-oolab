package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.AbstractNonFlatWorldMap;
import agh.ics.oop.proj1.AbstractWorldMapElement;
import agh.ics.oop.proj1.Animal;
import agh.ics.oop.proj1.observers.IWorldMapElementRemovedObserver;
import javafx.scene.control.Tab;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MapElementInfoTab extends Tab implements IWorldMapElementRemovedObserver {
    protected final AbstractWorldMapElement element;
    private final MapElementInfoBox box;

    public MapElementInfoTab(AbstractWorldMapElement element, AbstractNonFlatWorldMap map) {
        map.addObserver(this);

        this.element = element;
        Map<Class<? extends AbstractWorldMapElement>, Function<AbstractWorldMapElement, MapElementInfoBox>> boxConstructors = Map.ofEntries(
                Map.entry(Animal.class, mapElement -> new AnimalInfoBox((Animal) mapElement))
        );
        box = boxConstructors.
                getOrDefault(element.getClass(), MapElementInfoBox::new).apply(element);
        setText(ResourceBundle.getBundle("strings").getString("mapElementInfoTab.title"));
        setContent(box);
        setOnClosed(
                event -> box.close()
        );
    }

    @Override
    public void elementRemoved(AbstractWorldMapElement element) {
        if (this.element == element) {
            box.close();

        }
    }
}
