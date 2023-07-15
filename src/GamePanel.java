import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int SCREEN_WIDTH=1000,SCREEN_HEIGHT=600,SQUARE_SIZE=25;
    private final int SQUARE_NUMBER=SCREEN_HEIGHT*SCREEN_WIDTH/SQUARE_SIZE;
    private final int Y=SCREEN_HEIGHT/SQUARE_SIZE,X=SCREEN_WIDTH/SQUARE_SIZE;
    private int DELAY=150;
    private Timer timer=new Timer(DELAY,this);
    private boolean snakeGrowing=true;
    private int appleX=0,appleY=0;
    private int snakeLength=6, applesEaten=0;
    private char chosenDirection='D';
    private char currentDirection='R';
    private boolean running=false;
    private final char[][] grid=new char[X][Y];
    private final int[] snakeX=new int[SQUARE_NUMBER];
    private final int[] snakeY=new int[SQUARE_NUMBER];
    private final Random random=new Random();


    GamePanel(){
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(new SnakeKeyAdapter());
        start();
    }

    private void start(){
        running=true;
        readMap("/Users/muhammadayubxon/IdeaProjects/Snake 2/src/1.txt");
        snakeX[0]=2;
        snakeY[0]=1;
        snakeX[1]=1;
        snakeY[1]=1;
        timer.start();
    }
    private void readMap(String fileName){
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            for(int i=0;i<Y;i++){
                for(int j=0;j<X;j++){
                    grid[j][i]=(char)in.read();
                }
                in.read();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){
        /* Lines for showing the grid
        g.setColor(Color.black);
        for(int i=0;i<=SCREEN_HEIGHT/SQUARE_SIZE;i++){
            g.drawLine(0,i*SQUARE_SIZE,SCREEN_WIDTH,i*SQUARE_SIZE);
        }
        for(int i=0;i<SCREEN_WIDTH/SQUARE_SIZE;i++){
            g.drawLine(i*SQUARE_SIZE,0,i*SQUARE_SIZE,SCREEN_HEIGHT);
        }
         */
        g.setColor(new Color(0,100,0));
        g.fillRect(snakeX[0]*SQUARE_SIZE,snakeY[0]*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        g.setColor(new Color(0,180,0));
        for(int i=1;i<snakeLength;i++){
            g.fillRect(snakeX[i]*SQUARE_SIZE,snakeY[i]*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        }
        g.setColor(Color.gray);
        for(int i=0;i<Y;i++){
            for(int j=0;j<X;j++){
                if(grid[j][i]=='1'){
                    g.fillRect(j*SQUARE_SIZE,i*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
                }
            }
        }
    }
    private void move(){
        currentDirection=chosenDirection;
        for(int i=snakeLength-1;i>0;i--){
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }
        switch (currentDirection) {
            case 'U' -> snakeY[0]+=Y-1;
            case 'D' -> snakeY[0]++;
            case 'R' -> snakeX[0]++;
            case 'L' -> snakeX[0] += X - 1;
        }
        snakeY[0]%=Y;
        snakeX[0]%=X;
    }

    private boolean isInSnake(int x, int y){
        boolean ret=false;
        for (int i=0;i<snakeLength;i++){
            if(snakeY[i]==y&&snakeX[i]==x){
                ret=true;
                break;
            }
        }
        return ret;
    }

    private void newApple(){
        appleX=random.nextInt(0,X);
        appleY=random.nextInt(0,Y);
        while(grid[appleX][appleY]!='0'||isInSnake(appleX,appleY)){
            appleX=random.nextInt(0,X);
            appleY=random.nextInt(0,Y);
        }
    }
    private void checkApples(){
        if(isInSnake(appleX,appleY)){
            applesEaten++;
            if(snakeGrowing)snakeLength++;
            newApple();
        }
    }
    private void stopGame(){
        running=false;
        timer.stop();
    }
    private void checkCollisions(){
        if(grid[snakeX[0]][snakeY[0]]=='1'){
            System.out.println(1);
            stopGame();
            return;
        }
        for(int i=1;i<snakeLength;i++){
            if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]&&snakeX[i]!=0){
                System.out.println(2);
                stopGame();
                return;
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApples();
            checkCollisions();
        }
        if(running)repaint();
    }

    private class SnakeKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                    System.out.println(1);
                    if(currentDirection!='R')chosenDirection = 'L';
                }
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                    System.out.println(2);
                    if(currentDirection!='L')chosenDirection = 'R';
                }
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    System.out.println(3);
                    if(currentDirection!='D')chosenDirection = 'U';
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    System.out.println(4);
                    if(currentDirection!='U')chosenDirection = 'D';
                }
            }
        }
    }
}
