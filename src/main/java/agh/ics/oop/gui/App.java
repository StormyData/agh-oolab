package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
    static final private double cell_width = 20;
    static final private double cell_height = 20;
    private Vector2d size;
    AbstractWorldMap map;
    private Vector2d bottomLeft;

    public void init()
    {
        map=new GrassField(10);
        new Animal(map,new Vector2d(2,2),MapDirection.NORTH);
        new Animal(map,new Vector2d(5,2),MapDirection.NORTH);

        bottomLeft=map.getLowerBound();
        Vector2d upperRight=map.getUpperBound();
        size=upperRight.subtract(bottomLeft).add(new Vector2d(1,1));
    }

    public void start(Stage primaryStage)
    {

        Label labelyx = new Label("y/x");
        GridPane gridPane =new GridPane();

        gridPane.setGridLinesVisible(true);
        gridPane.add(labelyx,0,0);
        GridPane.setHalignment(labelyx, HPos.CENTER);
        for(int i=0;i<size.x;i++)
        {
            Label labelx = new Label(Integer.toString(i+ bottomLeft.x));
            GridPane.setHalignment(labelx, HPos.CENTER);
            gridPane.add(labelx,1+i,0);
        }
        for(int i=0;i<size.y;i++)
        {
            Label labely = new Label(Integer.toString(size.y-i+bottomLeft.y));
            GridPane.setHalignment(labely, HPos.CENTER);
            gridPane.add(labely,0,1+i);
        }
        for(int x=0;x<size.x;x++)
        {
            for(int y=0;y<size.y;y++)
            {
                Label obj = new Label(drawObject(bottomLeft.add(new Vector2d(x,size.y-y))));
                GridPane.setHalignment(obj, HPos.CENTER);
                gridPane.add(obj,1+x,1+y);
            }
        }
        for(int i=0;i<= size.x;i++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(cell_width));
        for(int i=0;i<= size.y;i++)
            gridPane.getRowConstraints().add(new RowConstraints(cell_height));
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private String drawObject(Vector2d currentPosition) {
        String result;
        if (this.map.isOccupied(currentPosition)) {
            Object object = this.map.objectAt(currentPosition);
            if (object != null) {
                result = object.toString();
            } else {
                result = " ";
            }
        } else {
            result = " ";
        }
        return result;
    }
}
