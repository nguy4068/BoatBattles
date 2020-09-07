import java.awt.*;

public class grid extends Rectangle {
    public int rows;
    public int cols;
    public int xCoord;
    public int yCoord;
    public boolean isReady;
    Color color;
    Color originalColor;
    public boolean isFired;
    public grid(int rows, int cols, int xCoord, int yCoord){
        this.rows = rows;
        this.cols = cols;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        color = Color.BLACK;
        originalColor = Color.black;
        isReady = false;
        isFired = false;
    }
}
