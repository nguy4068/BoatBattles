import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BattleBoatsGUI1 implements ActionListener, MouseListener {
    public static BattleBoatsGUI1 game;
    public grid[][] ListGrid;
    public boolean reveal = false;
    paint p;
    public boolean gameOver = false;
    public grid isReady;
    public grid oldReady;
    int[][] board;
    int[] boardCollection;
    private int[] numHit;
    private Label message;
    public int numBoatLeft;
    public int turns;
    private Label message1;
    public BattleBoatsGUI1(){
        BattleBoatsBoard1 b = new BattleBoatsBoard1("Standard");
        board = b.getBoard();
        boardCollection = b.boardCollection;
        numHit = new int[12];
        numBoatLeft = 12;
        JFrame f = new JFrame();
        f.setSize(600,600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p = new paint();
        p.setBounds(50,50,400,400);
        Button button = new Button("Reveal Board");
        Button fire = new Button("Fire");
        Button missile = new Button("Missile");
        fire.addActionListener(this);
        fire.setActionCommand("Fire");
        message = new Label("Welcome to Battle Boat");
        message1 = new Label("Turns: " + turns);
        message1.setBounds(100,10,50,30);
        button.setActionCommand("Reveal");
        button.addActionListener(this);
        button.setBounds(470,50,80,50);
        fire.setBounds(470,100,80,50);
        message.setBounds(200,10,150,30);
        f.add(message1);
        f.add(button);
        f.add(fire);
        f.add(message);
        f.add(p);
        p.addMouseListener(this);
        f.setVisible(true);
        ListGrid = new grid[20][20];
        Color[] colorBoat = new Color[]{Color.orange,Color.BLUE,Color.CYAN,Color.yellow,Color.gray,Color.MAGENTA,Color.DARK_GRAY,
                Color.PINK,Color.green,Color.red,Color.WHITE,Color.LIGHT_GRAY};
        int startY = 50;
        for (int i = 0; i < 20; i++){
            int startX = 50;
            for (int j = 0; j < 20; j++){
                ListGrid[i][j] = new grid(i,j,startX,startY);
                int nameBoat = board[i][j];
                System.out.println(nameBoat);
                if (nameBoat - 1 >= 0) {
                    System.out.println("yes");
                    ListGrid[i][j].originalColor = colorBoat[nameBoat-1];
                }
                startX = startX + 20;
            }
            startY = startY + 20;
        }
        p.repaint();

    }
    public void fire(){
        int rows = isReady.rows;
        int cols = isReady.cols;



    }
    public void repaint(Graphics g){
        if (reveal) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    int xCoord = ListGrid[i][j].xCoord;
                    int yCoord = ListGrid[i][j].yCoord;
                    g.setColor(ListGrid[i][j].originalColor);
                    g.fillRect(xCoord, yCoord, 20, 20);
                    g.setColor(Color.white);
                    g.drawRect(xCoord, yCoord, 20, 20);
                }
            }
        } else {
            for (int i = 0; i < 20; i++){
                for (int j = 0; j < 20; j++){
                    g.setColor(Color.black);
                    int xCoord = ListGrid[i][j].xCoord;
                    int yCoord = ListGrid[i][j].yCoord;
                    if (ListGrid[i][j].isReady){
                        g.setColor(Color.lightGray);
                        g.fillRect(xCoord, yCoord, 20, 20);
                        g.setColor(Color.white);
                        g.drawRect(xCoord, yCoord, 20, 20);
                    }else {
                        if (ListGrid[i][j].isFired){
                            g.setColor(ListGrid[i][j].originalColor);
                            g.fillRect(xCoord,yCoord,20,20);
                        }
                        g.drawRect(xCoord, yCoord, 20, 20);
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        game = new BattleBoatsGUI1();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals("Reveal")){
            reveal = true;
            gameOver = true;
            message.setText("Game over!");
            p.repaint();
        }else if (event.equals("Fire")){
            int rows = isReady.rows;
            int cols = isReady.cols;
            isReady.isReady = false;
            isReady.isFired = true;
            if(board[rows][cols] != 0){
                int name = board[rows][cols];
                numHit[name-1] = numHit[name-1] + 1;
                System.out.println(numHit[name-1]);
                if (numHit[name-1] == boardCollection[name-1]){
                    message.setText("You sink one boat!");
                    numBoatLeft = numBoatLeft - 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    message.setText("There are " + numBoatLeft + " boats left");
                }else{
                    message.setText("You hit!");
                }
            }else{
                message.setText("You missed!");
            }
            turns = turns + 1;
            message1.setText("Turns: " + turns);
            p.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int xCoord = mouseEvent.getX();
        int yCoord = mouseEvent.getY();
        int rows = (yCoord - 50)/20;
        int cols = (xCoord - 50)/20;
        if (!gameOver && yCoord - 50 > 0){
            if(0<=rows && rows < 20 && 0<=cols && cols<20){
                if(isReady == null){
                    isReady = ListGrid[rows][cols];
                    isReady.color = Color.lightGray;
                    isReady.isReady = true;
                    oldReady = isReady;
                }else{
                    oldReady = isReady;
                    isReady.color = Color.white;
                    isReady.isReady = false;
                    isReady = ListGrid[rows][cols];
                    isReady.isReady = true;
                }
                p.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}