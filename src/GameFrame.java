import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
    boolean isGP;
    GameFrame gf;
    GamePanel gp;
    GameFrame(){
        super("Snake 2");
        gf=this;
        JButton startButton=new JButton("START");
        startButton.setBounds(400,250,200,100);
        startButton.setBackground(Color.green);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setForeground(Color.white);
        startButton.setFont(new Font("qwe",Font.BOLD,40));
        startButton.addActionListener(e -> {
            remove(startButton);
            gp=new GamePanel();
            isGP=true;
            add(gp);
            SwingUtilities.updateComponentTreeUI(gf);
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGP){
                    gp.keyPressed(e);
                }
            }
        });



        add(startButton);
        setFocusable(true);
        setLayout(null);
        this.setSize(1000,628);
        this.setDefaultCloseOperation(GameFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }
}
