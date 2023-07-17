import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CustomMapPanel extends JPanel {

    private final int SCREEN_WIDTH=600,SCREEN_HEIGHT=360,SQUARE_SIZE=15;
    private final int Y=SCREEN_HEIGHT/SQUARE_SIZE,X=SCREEN_WIDTH/SQUARE_SIZE;
    private int[][] grid=new int[X][Y];
    CustomMapPanel(){

        readMap();
        addMouseListener(new SnakeMouseAdapter());

        setLayout(null);
        setSize(600,360);
    }
    private void readMap(){
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("src/Maps/custom.txt"));
            for(int i=0;i<Y;i++){
                for(int j=0;j<X;j++){
                    grid[j][i]=in.read()-'0';
                }
                in.read();
            }
        }
        catch (Exception e){
            System.out.println("File was not found");
        }
        grid[0][0]=2;
        grid[1][0]=2;
        grid[2][0]=2;
        grid[3][0]=2;
        grid[4][0]=2;
        grid[5][0]=2;
        grid[0][Y-1]=2;
        grid[1][Y-1]=2;
    }
    public void writeMap(){
        try{
            BufferedWriter out=new BufferedWriter(new FileWriter("src/Maps/custom.txt"));
            for(int i=0;i<Y;i++){
                for(int j=0;j<X;j++){
                    out.write('0'+grid[j][i]);
                }
                out.newLine();
            }
            out.flush();
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public void refresh(){
        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                grid[i][j]=0;
            }
        }
        grid[0][0]=2;
        grid[1][0]=2;
        grid[2][0]=2;
        grid[3][0]=2;
        grid[4][0]=2;
        grid[5][0]=2;
        grid[0][Y-1]=2;
        grid[1][Y-1]=2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.GRAY);

        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                if(grid[i][j]==1){
                    g.fillRect(i*SQUARE_SIZE,j*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
                }
            }
        }
        g.setColor(new Color(180,50,50));
        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                if(grid[i][j]==2){
                    g.fillRect(i*SQUARE_SIZE,j*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
                }
            }
        }
        g.setColor(Color.black);
        for(int i=0;i<=SCREEN_HEIGHT/SQUARE_SIZE;i++){
            g.drawLine(0,i*SQUARE_SIZE,SCREEN_WIDTH,i*SQUARE_SIZE);
        }
        for(int i=0;i<=SCREEN_WIDTH/SQUARE_SIZE;i++){
            g.drawLine(i*SQUARE_SIZE,0,i*SQUARE_SIZE,SCREEN_HEIGHT);
        }
    }

    private void change(int x,int y){
        if(grid[x][y]==1){
            grid[x][y]=0;
            repaint();
        }
        else if(grid[x][y]==0){
            grid[x][y]=1;
            repaint();
        }
    }


    private class SnakeMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            change(e.getX()/SQUARE_SIZE,e.getY()/SQUARE_SIZE);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            change(e.getX()/SQUARE_SIZE,e.getY()/SQUARE_SIZE);
        }
    }
}
