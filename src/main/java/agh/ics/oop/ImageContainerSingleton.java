package agh.ics.oop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class ImageContainerSingleton {
    record ImageDescriptor(String path,int x,int y,int w,int h){}


    private HashMap<String,ImageDescriptor> descriptors = new HashMap<>();
    private HashMap<String, ImageIcon> icons = new HashMap<>();
    private static ImageContainerSingleton singleton = null;
    private ImageContainerSingleton() throws IOException {
        //TODO: credit image authors
        //https://opengameart.org/content/grass
        descriptors.put("*",new ImageDescriptor("gras.png",0,0,32,32));
        //https://opengameart.org/content/lpc-style-farm-animals
        descriptors.put("N",new ImageDescriptor("sheep_walk.png",0,128*0,128,128));
        descriptors.put("E",new ImageDescriptor("sheep_walk.png",0,128*3,128,128));
        descriptors.put("S",new ImageDescriptor("sheep_walk.png",0,128*2,128,128));
        descriptors.put("W",new ImageDescriptor("sheep_walk.png",0,128*1,128,128));
        //https://opengameart.org/content/lpc-cats-and-dogs


        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        for(Entry<String,ImageDescriptor> entry : descriptors.entrySet())
        {
            ImageDescriptor imageDescriptor = entry.getValue();
            BufferedImage image = ImageIO.read(classLoader.getResourceAsStream(imageDescriptor.path));
            icons.put(entry.getKey(),  new ImageIcon(image.getSubimage(imageDescriptor.x,imageDescriptor.y,imageDescriptor.w,imageDescriptor.h)));
        }
    }
    public static ImageContainerSingleton getInstance()
    {
        if(singleton == null) {
            try {
                singleton = new ImageContainerSingleton();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return singleton;
    }


    public Icon iconOf(String objName) {
        return icons.get(objName);
    }
}
