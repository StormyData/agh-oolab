package agh.ics.oop;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class SwingImageBasedMapVisualizationEngine implements IMapVisualizationEngine {
    private static final String EMPTY_CELL=" ";
    private static final int PIXELS_PER_CELL=128;


    private final IWorldMap map;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final int width;
    private final int height;
    private final JFrame frame;
    private final JLabel[][] displayData;

    SwingImageBasedMapVisualizationEngine(IWorldMap map, Vector2d lowerLeft, Vector2d upperRight) {
        this.map = map;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.width=this.upperRight.x-this.lowerLeft.x+1;
        this.height=this.upperRight.y-this.lowerLeft.y+1;
        this.frame = new JFrame();
        displayData = new JLabel[width][height];


        for(int y=0;y<height;y++)
        {
            for(int x=0;x<width;x++)
            {
                displayData[x][y]=new JLabel();
                displayData[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                displayData[x][y].setVerticalAlignment(SwingConstants.CENTER);
                this.frame.add(displayData[x][y]);
            }
        }
        frame.setLayout(new GridLayout(height,width));
        frame.setSize(PIXELS_PER_CELL*width,PIXELS_PER_CELL*height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void draw()
    {
        redraw();
    }
    @Override
    public void redraw()
    {
        for(int x=0; x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                String objName =drawObject(new Vector2d(x+ lowerLeft.x,upperRight.y-y));
                displayData[x][y].setIcon(ImageContainerSingleton.getInstance().iconOf(objName));
            };
        }
        frame.repaint();
    }
    private String drawObject(Vector2d currentPosition) {
        String result;
        if (this.map.isOccupied(currentPosition)) {
            Object object = this.map.objectAt(currentPosition);
            if (object != null) {
                result = object.toString();
            } else {
                result = EMPTY_CELL;
            }
        } else {
            result = EMPTY_CELL;
        }
        return result;
    }
    @Override
    public void close()
    {
        frame.dispose();
    }
}
