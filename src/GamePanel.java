import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private GameFrame gameFrame;
    private final JLabel score;
    private final int SCREEN_WIDTH=1000,SCREEN_HEIGHT=650,SQUARE_SIZE=25;
    private final int SQUARE_NUMBER=SCREEN_HEIGHT*SCREEN_WIDTH/SQUARE_SIZE;
    private final int Y=(SCREEN_HEIGHT-50)/SQUARE_SIZE,X=SCREEN_WIDTH/SQUARE_SIZE;
    private final int DELAY;
    private final String map;
    private Timer timer;
    private boolean snakeGrowing;
    private int appleX,appleY;
    private int snakeLength, applesEaten=0;
    private char chosenDirection='R';
    private char currentDirection='R';
    private boolean running=false;
    private final char[][] grid=new char[X][Y];
    private final int[] snakeX=new int[SQUARE_NUMBER];
    private final int[] snakeY=new int[SQUARE_NUMBER];
    private final Random random=new Random();


    GamePanel(GameFrame gameFrame, int delay, String map, boolean snakeGrowing,int snakeLength,JPanel to){
        this.gameFrame=gameFrame;
        this.DELAY=delay;
        this.map=map;
        this.snakeGrowing=snakeGrowing;
        this.snakeLength=snakeLength;

        BackButton back=new BackButton(gameFrame,to,false);

        score=new JLabel("Your Score is 0");
        score.setBounds(400,0,300,50);
        score.setFont(new Font("qwe",Font.BOLD,30));
        score.setAlignmentX(CENTER_ALIGNMENT);


        add(back);
        add(score);
        setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setLayout(null);
        setFocusable(true);
        start();
    }

    private void start(){
        running=true;
        readMap(map);
        snakeX[0]=1;
        snakeY[0]=0;
        snakeX[1]=0;
        snakeY[1]=0;
        newApple();
        timer=new Timer(DELAY,this);
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
            System.out.println("File was not found");
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
        g.setColor(Color.GRAY);
        g.drawLine(0,49,SCREEN_WIDTH,49);
        g.drawLine(0,50,SCREEN_WIDTH,50);
        g.setColor(new Color(0,100,0));
        g.fillRect(snakeX[0]*SQUARE_SIZE,snakeY[0]*SQUARE_SIZE+50,SQUARE_SIZE,SQUARE_SIZE);
        g.setColor(new Color(0,180,0));
        for(int i=1;i<snakeLength;i++){
            g.fillRect(snakeX[i]*SQUARE_SIZE,snakeY[i]*SQUARE_SIZE+50,SQUARE_SIZE,SQUARE_SIZE);
        }
        g.setColor(Color.gray);
        for(int i=0;i<Y;i++){
            for(int j=0;j<X;j++){
                if(grid[j][i]=='1'){
                    g.fillRect(j*SQUARE_SIZE,i*SQUARE_SIZE+50,SQUARE_SIZE,SQUARE_SIZE);
                }
            }
        }
        g.setColor(Color.red);
        g.fillOval(appleX*SQUARE_SIZE,appleY*SQUARE_SIZE+50,SQUARE_SIZE,SQUARE_SIZE);
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
            score.setText("Your Score is "+applesEaten);
            if(snakeGrowing){
                snakeX[snakeLength]=snakeX[snakeLength-1];
                snakeY[snakeLength]=snakeY[snakeLength-1];
                snakeLength++;
            }
            newApple();
        }
    }
    private void stopGame(){
        running=false;
        timer.stop();
    }
    private void checkCollisions(){
        if(grid[snakeX[0]][snakeY[0]]=='1'){
            stopGame();
            return;
        }
        for(int i=1;i<snakeLength;i++){
            if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]&&snakeX[i]!=0){
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

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                if(currentDirection!='R')chosenDirection = 'L';
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if(currentDirection!='L')chosenDirection = 'R';
            }
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                if(currentDirection!='D')chosenDirection = 'U';
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if(currentDirection!='U')chosenDirection = 'D';
            }
        }
    }
}
