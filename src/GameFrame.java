import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    GameFrame gf;
    GameFrame(){
        super("Snake 2");
        gf=this;
        JButton startButton=new JButton("START");
        startButton.setPreferredSize(new Dimension(1000,600));
        startButton.setBackground(Color.green);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setForeground(Color.white);
        startButton.setFont(new Font("qwe",Font.BOLD,40));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(startButton);
                add(new GamePanel());
                SwingUtilities.updateComponentTreeUI(gf);
            }
        });
        add(startButton);
        //this.setSize(1000,628);
        this.setDefaultCloseOperation(GameFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
