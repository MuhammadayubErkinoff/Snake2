import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private final int SCREEN_WIDTH=1000,SCREEN_HEIGHT=650;
    private GameFrame gameFrame;
    MainPanel(GameFrame gameFrame){
        this.gameFrame=gameFrame;

        //classical game button
        SnakeButton classical=new SnakeButton("Classical");
        classical.addActionListener(e -> gameFrame.changePanel(new GamePanel(gameFrame,150,"/Users/muhammadayubxon/IdeaProjects/Snake 2/src/Maps/0.txt",true,false,6,new MainPanel(gameFrame)),true));
        classical.setBounds(350,100,300,60);
        add(classical);

        //easy game button
        SnakeButton easy=new SnakeButton("Easy");
        easy.setBounds(350,180,300,60);
        easy.addActionListener(e->gameFrame.changePanel(new MapChoosingPanel(gameFrame,200,10),false));
        add(easy);

        //norm game button
        SnakeButton norm=new SnakeButton("Normal");
        norm.setBounds(350,260,300,60);
        norm.addActionListener(e->gameFrame.changePanel(new MapChoosingPanel(gameFrame,125,15),false));
        add(norm);

        //hard game button
        SnakeButton hard=new SnakeButton("Hard");
        hard.setBounds(350,340,300,60);
        hard.addActionListener(e->gameFrame.changePanel(new MapChoosingPanel(gameFrame,75,25),false));
        add(hard);

        //custom game button
        SnakeButton custom=new SnakeButton("Custom");
        custom.setBounds(350,420,300,60);
        custom.addActionListener(e->gameFrame.changePanel(new CustomMapCreatingPanel(gameFrame),false));
        add(custom);

        setLayout(null);
        setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setFocusable(true);
    }
}
