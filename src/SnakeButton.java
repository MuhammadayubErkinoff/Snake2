import javax.swing.*;
import java.awt.*;

public class SnakeButton extends JButton {
    SnakeButton(String label){
        super(label);
        setBackground(Color.green);
        setOpaque(true);
        setBorderPainted(false);
        setForeground(Color.white);
        setFont(new Font("qwe",Font.BOLD,25));
    }

}
