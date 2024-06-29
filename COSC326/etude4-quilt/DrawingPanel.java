
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

/**
 * This Panel class contains implementation/drawing to generate quilt patterns (recursive squares drawn on corners) 
 * @author Beckham Wilson 7564267
 */
public class DrawingPanel extends JPanel {
    private double[] scalingArr;
    private int panelSize;
    private Color[] depthToColor;
    private double scalingFactor;
    private BufferedImage quiltImage;

    /**
     * Creates DrawingPanel for quilt pattern
     * @param size
     * @param scalingArr
     * @param depthToColorArr
     */
    DrawingPanel(int size, double[] scalingArr, Color[] depthToColorArr) {
        this.panelSize = size;
        this.scalingArr = scalingArr;
        this.depthToColor = depthToColorArr;
        this.setPreferredSize(new Dimension(size, size));
        double scalingArrSum = 0;
        for (double eachScaling : scalingArr){
            scalingArrSum = scalingArrSum + eachScaling;
        } 
        this.scalingFactor = (double) size / (scalingArrSum);
        this.quiltImage = new BufferedImage(panelSize, panelSize, BufferedImage.TYPE_INT_RGB);
        setImageBackground();
    }

    /**
     * Paints quilt pattern to Buffered Image then JPanel
     * @param g Graphics object for JPanel
     */
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = quiltImage.createGraphics();

        ArrayList<int[]> squaresCoords = new ArrayList<int[]>();
        addSquareCoordinates(squaresCoords, this.scalingArr.length-1, panelSize / 2, panelSize / 2);
        squaresCoords.sort(Comparator.comparingInt(each -> (-1) * each[0]));

        for (int[] squareCoord : squaresCoords) {
            int depth = squareCoord[0];
            int sideLength = (int) (scalingFactor * scalingArr[depth]);
            int centerX = squareCoord[1];
            int centerY = squareCoord[2];
            Color squareColor = depthToColor[depth];
            g2D.setColor(squareColor);
            drawSquare(g2D, sideLength, centerX, centerY);
        }
        g2D.dispose();
        g.drawImage(quiltImage, 0, 0, this);

    }

    /**
     * Draws the square based on its side length and center coordinates.
     * 
     * @param g
     * @param sideLength
     * @param centerX
     * @param centerY
     */
    private void drawSquare(Graphics2D g, int sideLength, int centerX, int centerY) {
        g.fillRect(centerX - (sideLength / 2), centerY - (sideLength / 2), sideLength, sideLength);
    }

    /**
     * Recursively adds the required squares to create a recursive pattern where
     * squares are drawn on its corners.
     * Each square is represented via its depth and center coordinates. (depth is
     * counting from 0 being the squares shown on top )
     * 
     * @param listOfSquares
     * @param depth
     * @param centerX
     * @param centerY
     */
    private void addSquareCoordinates(ArrayList<int[]> listOfSquares, int depth, int centerX, int centerY) {

        int[] squareDesc = { depth, centerX, centerY };
        listOfSquares.add(squareDesc);
        int sideLength = (int) (scalingFactor * scalingArr[depth]);
        int halfSideLength = sideLength / 2;
        depth--;
        if (depth == -1) {
            return;
        }
        int[] topLeftCorner = { depth, centerX - halfSideLength, centerY - halfSideLength };
        int[] topRightCorner = { depth, centerX + halfSideLength, centerY - halfSideLength };
        int[] bottomLeftCorner = { depth, centerX - halfSideLength, centerY + halfSideLength };
        int[] bottomRightCorner = { depth, centerX + halfSideLength, centerY + halfSideLength };
        addSquareCoordinates(listOfSquares, depth, topLeftCorner[1], topLeftCorner[2]);
        addSquareCoordinates(listOfSquares, depth, topRightCorner[1], topRightCorner[2]);
        addSquareCoordinates(listOfSquares, depth, bottomLeftCorner[1], bottomLeftCorner[2]);
        addSquareCoordinates(listOfSquares, depth, bottomRightCorner[1], bottomRightCorner[2]);

    }


    /**
     * Draws a white background since default background of image is black
     */
    public void setImageBackground() {
        Graphics g = quiltImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panelSize, panelSize);
        g.dispose();
    }

    public BufferedImage getImage() {
        return quiltImage;
    }

}