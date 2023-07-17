import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMapCreatingPanel extends JPanel {
     CustomMapCreatingPanel(GameFrame gameFrame){

         //back button
         BackButton back=new BackButton(gameFrame,new MainPanel(gameFrame),false);
         add(back);

         //isSnakeGrows checkbox
         JCheckBox isSnakeGrows=new JCheckBox("Snake Grows",true);
         isSnakeGrows.setBounds(50,60,150,50);
         add(isSnakeGrows);

         //snakeBots checkbox
         JCheckBox snakeBots=new JCheckBox("Snake Bots",false);
         snakeBots.setBounds(50,130,150,50);
         add(snakeBots);

         //Length slider
         JLabel lengthLabel=new JLabel("Length");
         lengthLabel.setBounds(250,60,50,50);
         add(lengthLabel);
         JSlider length=new JSlider(JSlider.HORIZONTAL,4,40,20);
         length.setMinorTickSpacing(4);
         length.setMajorTickSpacing(12);
         length.setPaintTicks(true);
         length.setPaintLabels(true);
         length.setBounds(300,60,300,50);
         length.setName("Length");
         add(length);

         //Speed slider
         JLabel speedLabel=new JLabel("Speed");
         speedLabel.setBounds(250,130,50,50);
         add(speedLabel);
         JSlider speed=new JSlider(JSlider.HORIZONTAL,5,20,10);
         speed.setMinorTickSpacing(1);
         speed.setMajorTickSpacing(5);
         speed.setPaintTicks(true);
         speed.setPaintLabels(true);
         speed.setBounds(300,130,300,50);
         speed.setName("Speed");
         add(speed);

         //CustomMapPanel
         CustomMapPanel customMap=new CustomMapPanel();
         customMap.setBounds(50,200,600,360);
         add(customMap);

         //Refresh button
         SnakeButton refresh=new SnakeButton("Refresh");
         refresh.setBounds(125,560,200,50);
         refresh.addActionListener(e->{customMap.refresh();});
         add(refresh);

         //Start button
         SnakeButton start=new SnakeButton("Start");
         start.setBounds(375,560,200,50);
         start.addActionListener(e->{customMap.writeMap();gameFrame.changePanel(new GamePanel(gameFrame,1500/speed.getValue(),"src/Maps/custom.txt",isSnakeGrows.isSelected(),length.getValue(),this),true);});
         add(start);


         setSize(1000,650);
         setLayout(null);
     }
}
