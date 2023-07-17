import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    boolean isGamePanel=false;
    GameFrame gf;
    JPanel currentPanel;
    GameFrame(){
        super("Snake 2");
        isGamePanel=false;
        gf=this;
        currentPanel=new MainPanel(gf);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGamePanel){
                    ((GamePanel)currentPanel).keyPressed(e);
                }
            }
        });
        add(currentPanel);
        setFocusable(true);
        setLayout(null);
        this.setSize(1000,678);
        this.setDefaultCloseOperation(GameFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }
    public void changePanel(JPanel newPanel, boolean isGamePanel){
        remove(currentPanel);
        currentPanel=newPanel;
        add(currentPanel);
        this.isGamePanel=isGamePanel;
        SwingUtilities.updateComponentTreeUI(gf);
    }
}
