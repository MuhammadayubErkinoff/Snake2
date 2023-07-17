import javax.swing.*;

public class BackButton extends SnakeButton{
    BackButton( GameFrame gameFrame, JPanel to,boolean isGamePanel){
        super("BACK");
        setBounds(0,0,150,48);
        addActionListener(e->gameFrame.changePanel(to,isGamePanel));
    }
}
