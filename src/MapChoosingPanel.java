import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MapChoosingPanel extends JPanel {
    GameFrame gameFrame;
    private final int SCREEN_WIDTH=1000,SCREEN_HEIGHT=650;
    private final int BUTTON_WIDTH=150,BUTTON_HEIGHT=170,H_GAP=25,V_GAP=20;
    ArrayList<String>addresses=new ArrayList<>();
    ArrayList<String>names=new ArrayList<>();

    MapChoosingPanel(GameFrame gameFrame,int delay,int snakeLength){
        this.gameFrame=gameFrame;

        BackButton back=new BackButton(gameFrame,new MainPanel(gameFrame),false);
        add(back);

        addMapButtons(delay,snakeLength);
        setLayout(null);
        setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setFocusable(true);
    }

    private void addMapButtons(int delay,int snakeLength){
        readMaps();
        int currentX=75;
        int currentY=75;
        for(int i=0;i<addresses.size();i++){
            SnakeButton button=new SnakeButton(names.get(i));
            button.setBounds(currentX,currentY,BUTTON_WIDTH,BUTTON_HEIGHT);
            int finalI = i;
            button.addActionListener(e->gameFrame.changePanel(new GamePanel(gameFrame,delay,addresses.get(finalI),false,true,snakeLength, new MapChoosingPanel(gameFrame,delay,snakeLength)),true));
            if(i%5==4) {
                currentX=75;
                currentY+=BUTTON_HEIGHT+V_GAP;
            }
            else{
                currentX+=BUTTON_WIDTH+H_GAP;
            }
            add(button);
        }
    }

    private void readMaps() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/Maps.txt"));
            int n=Integer.parseInt(in.readLine());
            for(int i=0;i<n;i++) {
                String address = in.readLine();
                String name = in.readLine();
                addresses.add(address);
                names.add(name);
            }

        }
        catch (Exception e){
            System.out.println("File was not Found");
        }
    }
}
