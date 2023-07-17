import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private GameFrame gameFrame;
    private final JLabel score;
    private final boolean snakeBots;
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
    private char botDirection='R';
    private boolean running=false;
    private final char[][] grid=new char[X][Y];
    private final int[] botX=new int[6];
    private final int[] botY=new int[6];
    private final int[] snakeX=new int[SQUARE_NUMBER];
    private final int[] snakeY=new int[SQUARE_NUMBER];
    private final Random random=new Random();


    GamePanel(GameFrame gameFrame, int delay, String map, boolean snakeGrowing,boolean snakeBots,int snakeLength,JPanel to){
        this.gameFrame=gameFrame;
        this.DELAY=delay;
        this.map=map;
        this.snakeGrowing=snakeGrowing;
        this.snakeBots=snakeBots;
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
        if(snakeBots) {
            for (int i = 0; i < 6; i++) {
                botX[i] = 0;
                botY[i] = Y - 1;
            }
        }
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
        if(snakeBots) {
            g.setColor(Color.YELLOW);
            for (int i = 1; i < 6; i++) {
                g.fillRect(botX[i] * SQUARE_SIZE, botY[i] * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);
            }
            g.setColor(Color.ORANGE);
            g.fillRect(botX[0] * SQUARE_SIZE, botY[0] * SQUARE_SIZE + 50, SQUARE_SIZE, SQUARE_SIZE);
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

    private boolean chooseDirection(){
        ArrayList<Character>a=new ArrayList<Character>();
        if(botDirection=='D'&&checkCollisionsBot(botX[0],(botY[0]+1)%Y)){
            for(int i=0;i<10;i++)a.add('D');
        }
        else if(botDirection=='U'&&checkCollisionsBot(botX[0],(botY[0]-1+Y)%Y)){
            for(int i=0;i<10;i++)a.add('U');
        }
        else if(botDirection=='R'&&checkCollisionsBot((botX[0]+1)%X,botY[0])){
            for(int i=0;i<10;i++)a.add('R');
        }
        else if(botDirection=='L'&&checkCollisionsBot((botX[0]+X-1)%X,botY[0])){
            for(int i=0;i<10;i++)a.add('L');
        }
        if(botDirection=='D'||botDirection=='U'){
            if(checkCollisionsBot((botX[0]+1)%X,botY[0])){
                a.add('R');
            }
            if(checkCollisionsBot((botX[0]+X-1)%X,botY[0])) {
                a.add('L');
            }
        }
        else{
            if(checkCollisionsBot(botX[0],(botY[0]+1)%Y)){
                a.add('D');
            }
            if(checkCollisionsBot(botX[0],(botY[0]-1+Y)%Y)){
                a.add('U');
            }
        }
        a.add(botDirection);
        if(a.size()==1){
            botDirection='R';
            for(int i=0;i<6;i++) {
                botX[i] = 0;
                botY[i] = Y - 1;
            }
            return false;
        }
        botDirection=a.get(random.nextInt(0,a.size()-1));
        return true;
    }

    private void moveBot(){
        if(chooseDirection()){
            for(int i=5;i>0;i--){
                botX[i]=botX[i-1];
                botY[i]=botY[i-1];
            }
            switch (botDirection) {
                case 'U' -> botY[0]+=Y-1;
                case 'D' -> botY[0]++;
                case 'R' -> botX[0]++;
                case 'L' -> botX[0] += X - 1;
            }
            botY[0]%=Y;
            botX[0]%=X;
        }
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
    private boolean isInBot(int x, int y){
        boolean ret=false;
        for (int i=0;i<6;i++){
            if(botY[i]==y&&botX[i]==x){
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
    private void checkApplesBot(){
        if(isInBot(appleX,appleY)){
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
        if(snakeBots) {
            for (int i = 0; i < 6; i++) {
                if (snakeX[0] == botX[i] && snakeY[0] == botY[i]) {
                    stopGame();
                    return;
                }
            }
        }
    }
    private boolean checkCollisionsBot(int x,int y){
        if(grid[x][y]=='1'){
            return false;
        }
        for(int i=0;i<snakeLength;i++){
            if(x==snakeX[i]&&y==snakeY[i]){
                return false;
            }
        }
        for(int i=0;i<6;i++){
            if(x==botX[i]&&y==botY[i]){
                return false;
            }
        }

        return true;
    }
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApples();
            checkCollisions();
            if(snakeBots) {
                moveBot();
                checkApplesBot();
            }
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
