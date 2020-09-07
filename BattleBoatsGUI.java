import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BattleBoatsGUI implements ActionListener, MouseListener {
    public static BattleBoatsGUI game;
    public grid[][] ListGrid;
    public boolean reveal = false;
    paint p;
    public boolean gameOver = false;
    public grid isReady;
    public grid oldReady;
    int[][] board;
    int[] boardCollection;
    private int[] numHit;
    private Button message;
    public BattleBoatsGUI(){
        BattleBoatsBoard1 b = new BattleBoatsBoard1("Standard");
        board = b.getBoard();
        boardCollection = b.boardCollection;
        numHit = new int[8];
        JFrame f = new JFrame();
        f.setSize(600,600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        p = new paint();
        p.setLayout(null);
        Button button = new Button("Reveal Board");
        Button fire = new Button("Fire");
        message = new Button("Welcome to Battle Boat");
        message.setForeground(Color.black);
        message.setBackground(Color.white);
        button.setActionCommand("Reveal");
        button.addActionListener(this);
        p.add(button);
        p.add(fire);
        p.add(message);
        button.setBounds(470,50,80,50);
        fire.setBounds(470,100,80,50);
        message.setBounds(200,10,150,30);
        p.addMouseListener(this);
        f.add(p);
        f.setVisible(true);
        ListGrid = new grid[8][8];
        Color[] colorBoat = new Color[]{Color.orange,Color.BLUE,Color.CYAN,Color.yellow,Color.gray,Color.MAGENTA,Color.DARK_GRAY,
                                        Color.PINK,Color.green,Color.red,Color.WHITE,Color.LIGHT_GRAY};
        int startY = 50;
        for (int i = 0; i < 8; i++){
            int startX = 50;
            for (int j = 0; j < 8; j++){
                ListGrid[i][j] = new grid(i,j,startX,startY);
                int nameBoat = board[i][j];
                System.out.println(nameBoat);
                if (nameBoat - 1 >= 0) {
                    System.out.println("yes");
                    ListGrid[i][j].color = colorBoat[nameBoat-1];
                }
                startX = startX + 50;
            }
            startY = startY + 50;
        }
        p.repaint();

    }
    public void fire(){
        int rows = isReady.rows;
        int cols = isReady.cols;



    }
    public void repaint(Graphics g){
        if (reveal) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int xCoord = ListGrid[i][j].xCoord;
                    int yCoord = ListGrid[i][j].yCoord;
                    g.setColor(ListGrid[i][j].color);
                    g.fillRect(xCoord, yCoord, 50, 50);
                    g.setColor(Color.white);
                    g.drawRect(xCoord, yCoord, 50, 50);
                }
            }
        } else {
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    g.setColor(Color.black);
                    int xCoord = ListGrid[i][j].xCoord;
                    int yCoord = ListGrid[i][j].yCoord;
                    if (ListGrid[i][j].isReady){
                        g.setColor(Color.lightGray);
                        g.fillRect(xCoord, yCoord, 50, 50);
                        g.setColor(Color.white);
                        g.drawRect(xCoord, yCoord, 50, 50);
                    }else {
                        g.drawRect(xCoord, yCoord, 50, 50);
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        game = new BattleBoatsGUI();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals("Reveal")){
            reveal = true;
            gameOver = true;
            message.setLabel("Game over!");
            p.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int xCoord = mouseEvent.getX();
        int yCoord = mouseEvent.getY();
        int rows = (yCoord - 50)/50;
        int cols = (xCoord - 50)/50;
        if (!gameOver && yCoord - 50 > 0){
            if(0<=rows && rows < 8 && 0<=cols && cols<8){
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
