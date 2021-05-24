package chips;

import Model.Data;
import Model.Spieler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Chips extends JFrame {

    JPanel panel=new JPanel();

    JButton btn1=new JButton();
    JButton btn2=new JButton();
    JButton btn3=new JButton();
    JButton btn4=new JButton();
    JButton btn5=new JButton();
    JButton btn6=new JButton();
    JButton ok=new JButton("BET");
    JButton allin=new JButton("ALL IN");
    JButton reduce=new JButton("REDUCE");

    JLabel player;

    private int bet=0;
    private ArrayList<Integer> betlist=new ArrayList<>();

    ArrayList<JButton> btnlist=new ArrayList<>();
    String playername;
    int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    int whichPlayer;
    Color colorPanel;
    int playerNumber = Data.valueMap.get("spieler")+Data.valueMap.get("bot");
    int iconSize =screenHeight;
    Timer t1;

    public Chips(String playername, int whichPlayer, Color colorPanel){

        if(playerNumber==1)
            iconSize/=7;
        else if(playerNumber==2)
            iconSize/=8;
        else if(playerNumber==3)
            iconSize/=9;
        else if(playerNumber==4)
            iconSize/=11;
        else if(playerNumber==5)
            iconSize/=12;

        this.playername=playername;
        this.whichPlayer = whichPlayer;
        this.colorPanel = colorPanel;

        setSize(iconSize *3, iconSize *3);
        setLocation(0, screen.height - iconSize * 3);

        panel.setLayout(null);
        setIconPaths();

        panel.setBackground(colorPanel);

        player=new JLabel(playername,SwingConstants.CENTER);
        player.setText(playername + ", Geld: " + Data.spielerMap.get(whichPlayer).getGeld());
        player.setBounds(iconSize *3/4, iconSize *5/2, iconSize *3/2, iconSize /3);
        player.setFont(new Font("Comic Sans",Font.BOLD, iconSize /7));
        player.setBorder(new LineBorder(Color.BLACK,1));
        player.setForeground(Color.BLACK);
        panel.add(player);

        btnlist.add(btn1);
        btnlist.add(btn2);
        btnlist.add(btn3);
        btnlist.add(btn4);
        btnlist.add(btn5);
        btnlist.add(btn6);
        setActionListeners();
        addComponents();

        placeOkbtn();

        add(panel);

        setUndecorated(true);
        setVisible(true);
        setAlwaysOnTop(true);
        validate();
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Data.valueMap.put("openStages", 1);
                e.getWindow().dispose();
            }
        });

    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void setIconPaths(){
        ImageIcon image=new ImageIcon("src/pngchips/image_1_1 (1).png");
        btn1.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn1.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_1_2 (1).png");
        btn2.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn2.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_1_3.png");
        btn3.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn3.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_1.png");
        btn4.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn4.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_2.png");
        btn5.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn5.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_3_prev_ui.png");
        btn6.setIcon(resizeIcon(createIcon(image), iconSize, iconSize));
        btn6.setBackground(colorPanel);
    }

    private void animation() throws InterruptedException {
        t1=new Timer(15, new AbstractAction() {
            int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
            int y = getY();

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (getY()<= screenHeight) {
                    y += 10;
                    setLocation(getX(), y);
                }
                else {
                    dispose();
                    t1.stop();
                }
            }
        });
        t1.start();
    }

    private void addComponents() {

        Border emptyBorder = BorderFactory.createEmptyBorder();

        int x = 0;
        int y = 0;

        btnlist.get(0).setBounds(0, 0, iconSize, iconSize);
        panel.add(btnlist.get(0));

        for (int i = 0; i < btnlist.size(); i++) {
            panel.add(btnlist.get(i));
            btnlist.get(i).setBorder(emptyBorder);
            btnlist.get(i).setBounds(x, y, iconSize, iconSize);
            addMouseHover(btnlist.get(i));
            x += btnlist.get(i).getWidth();
            if (i == 2) {
                y = btnlist.get(i).getHeight();
                x = 0;
            }

        }
    }

    private void addMouseHover(JButton button)
    {
        Color color = this.colorPanel;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private void setActionListeners(){

        btn1.addActionListener(e -> {
            betlist.add(1);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

        btn2.addActionListener(e -> {
            betlist.add(5);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

        btn3.addActionListener(e -> {
            betlist.add(25);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

        btn4.addActionListener(e -> {
            betlist.add(50);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

        btn5.addActionListener(e -> {
            betlist.add(100);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

        btn6.addActionListener(e -> {
            betlist.add(500);
            player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
        });

    }

    private void placeOkbtn(){

        ok.setBounds(iconSize, iconSize *2, iconSize, iconSize /3);
        setButton(ok);
        ok.addActionListener(e -> {
            if(getTotalBet() <= Data.spielerMap.get(whichPlayer).getGeld() && getTotalBet() > 0) {
                //save bet to betMap
                Data.betMap.put(whichPlayer, getTotalBet());
                //save new money (Money before - bet)
                Spieler spieler = new Spieler(Data.spielerMap.get(whichPlayer).getSpielername(), Data.spielerMap.get(whichPlayer).getID(), Data.spielerMap.get(whichPlayer).getSpieleAnzahl(), Data.spielerMap.get(whichPlayer).getSiegeAnzahl(), Data.spielerMap.get(whichPlayer).getGeld() - getTotalBet());
                Data.spielerMap.put(whichPlayer, spieler);
                if(whichPlayer == Data.valueMap.get("spieler")-1)
                    Data.valueMap.put("openStages", 1);
                try {
                    animation();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }else{
                ok.setText("Not enough money");
            }
        });
        panel.add(ok);

        allin.setBounds(iconSize *2, iconSize *2, iconSize, iconSize /3);
        setButton(allin);
        allin.addActionListener(e -> {
            //save bet to betMap
            Data.betMap.put(whichPlayer, Data.spielerMap.get(whichPlayer).getGeld());
            //save new money (1)
            Spieler spieler = new Spieler(Data.spielerMap.get(whichPlayer).getSpielername(), Data.spielerMap.get(whichPlayer).getID(), Data.spielerMap.get(whichPlayer).getSpieleAnzahl(), Data.spielerMap.get(whichPlayer).getSiegeAnzahl(), 0);
            Data.spielerMap.put(whichPlayer, spieler);
            if(whichPlayer == Data.valueMap.get("spieler")-1)
                Data.valueMap.put("openStages", 1);
            try {
                animation();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        panel.add(allin);

        reduce.setBounds(0, iconSize *2, iconSize, iconSize /3);
        setButton(reduce);
        reduce.addActionListener(e -> {
            if(!betlist.isEmpty()) {
                betlist.remove(betlist.size() - 1);
                player.setText(playername + ", Geld: " + (Data.spielerMap.get(whichPlayer).getGeld()-getTotalBet()));
            }
        });
        panel.add(reduce);

    }

    private void setButton(JButton button)
    {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(this.colorPanel);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Comic Sans",Font.BOLD, iconSize /7));
        button.setBorder(new LineBorder(Color.BLACK));
        button.setFocusable(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(getColorPanel().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(getColorPanel());
            }
        });
    }

    private ImageIcon createIcon(ImageIcon imgicon){

        Image img=imgicon.getImage();
        Image newimg=img.getScaledInstance(iconSize, iconSize,Image.SCALE_SMOOTH);
        ImageIcon newIcon=new ImageIcon(newimg);
        return newIcon;
    }

    public int getTotalBet(){
        bet=0;
        for(int i:betlist)
            bet+=i;
        return bet;
    }

    public Color getColorPanel()
    {
        return this.colorPanel;
    }

}