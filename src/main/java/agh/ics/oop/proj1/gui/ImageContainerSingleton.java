package agh.ics.oop.proj1.gui;

import javafx.scene.image.Image;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageContainerSingleton {
    private Map<String,Image> imageMap = new HashMap<>();


    private static ImageContainerSingleton singleton = null;

    private ImageContainerSingleton()
    {
        //private constructor
    }
    public static ImageContainerSingleton getSingleton()
    {
        if(singleton == null)
            singleton = new ImageContainerSingleton();
        return singleton;
    }

    //returns null if the image name is also null
    //otherwise returns Image associated with given name
    public Image getImageForName(String name)
    {
        if(name == null)
            return null;
        if(!imageMap.containsKey(name))
            loadImage(name);
        return imageMap.get(name);
    }

    private void loadImage(String name) {
        InputStream stream = Thread.currentThread().getContextClassLoader().
                getResourceAsStream(name);
        if (stream == null)
            imageMap.put(name,null);
        imageMap.put(name,new Image(stream));
    }

}
