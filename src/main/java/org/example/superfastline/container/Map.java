package org.example.superfastline.container;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.superfastline.container.Walls.ClosedBox;
import org.example.superfastline.container.Walls.OpenBox;
import org.example.superfastline.container.Walls.Box;

import java.util.Random;


public class Map extends Pane {
    private BoxContainer parent ;
    private int size;
    private Circle startCircle, endCircle;
    private double innerS;
    private Box[][] map;
    private int startPosX, startPosY, endPosX, endPosY;

    public Map(int size, BoxContainer parent) {
        if (size  == 0) return;
        this.size = size;
        this.parent = parent;
        init();
    }

    private void init() {
        map = new Box[size-1][size-1];
        this.setStyle("-fx-border-color: black");
        setInnerSize();
        generateMap();
        randomStartAndEndPoint();
    }

    private void setInnerSize() {
        BoxContainer boxContainer = ((BoxContainer) this.getParent());
        if (parent == null) System.out.println("Parent is null");
        innerS =  (double) ApplicationBox.WIDTH /size;
    }

    private int randomNumb() {

        return (int) (Math.random() * 10);
        }
        private void randomStartAndEndPoint () {
             Random random = new Random();
             int startRow, startCol;
             do {
                 startRow = random.nextInt(size-1);
                 startCol = random.nextInt(size-1);
                 startPosX = startRow;
                 startPosY = startCol;
             } while (isClosedBox(startRow, startCol) || (startRow > size-1 || startCol > size-1));
             double startX = (startCol * innerS) + (innerS / 2);
             double startY = (startRow * innerS) + (innerS / 2);

             // Generate random end circle coordinates not at the edges
            int endRow, endCol;
            do {
                endRow = random.nextInt(size-1);
                endCol = random.nextInt(size -1);
                endPosX = endRow;
                endPosY = endCol;
            } while ( isClosedBox(endRow, endCol) ||  (endRow > size-1 || endCol > size-1));
            double endX = (endCol *innerS)  + innerS / 2;
            double endY = (endRow * innerS) + (innerS / 2);

            // Create start circle (green)
            startCircle = new Circle(startX, startY, innerS / 4, Color.GREEN);
            this.getChildren().add(startCircle);

            // Create end circle (red)
            endCircle = new Circle(endX, endY, innerS / 4, Color.RED);
            this.getChildren().add(endCircle);
        }

        private void generateMap()  {
            for (int row = 0; row  < map.length; row++) {
                for (int col = 0; col < map.length; col++) {
                    boolean isEdge = isEdge(row, col);
                    if (isEdge) {
                        map[row][col] = new ClosedBox(innerS, row*innerS, col*innerS);
                    }
                   else if (randomNumb() > 8) {
                        map[row][col] = new ClosedBox(innerS, row*innerS, col*innerS);
                    } else {
                        map[row][col] = new OpenBox(innerS, row*innerS, col*innerS);
                    }
                    this.getChildren().add(map[row][col]);
                }
            }
        }

        private boolean isEdge(int row, int col) {
            return row == 0 || col == 0 || row == map.length -1  || col == map.length-1;
        }

        private boolean isClosedBox(int row , int col) {
            if (map[col][row] instanceof ClosedBox) {
                return true;
            }
            return false;
        }

        public Box[][] getWalls() {
            return map;
        }
        public double getInnerSize() {
            return innerS;
        }
        public double getStartCircleX() {
            return startCircle.getCenterX();
        }
        public double getStartCircleY() {
            return startCircle.getCenterY();
        }
        public double getEndCircleX() {
            return endCircle.getCenterX();
        }
        public double getEndCircleY() {
            return endCircle.getCenterY();
        }
}