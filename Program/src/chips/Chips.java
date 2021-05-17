package chips;

import Model.Data;
import Model.Spieler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    final int ICONSIZE=200;
    private int bet=0;
    private ArrayList<Integer> betlist=new ArrayList<>();

    int[] array={1,5,25,50,100,500};
    int counter=0;
    int betset=0;

    ArrayList<JButton> btnlist=new ArrayList<>();
    String playername;
    int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    int whichPlayer;
    Color colorPanel=new Color(2, 102, 47);
    Timer t1;

    public Chips(String playername, int whichPlayer){
        this.playername=playername;
        this.whichPlayer = whichPlayer;

        setSize(ICONSIZE*3,ICONSIZE*3);
        setLocation(0,(int)screenHeight-ICONSIZE*3);

        panel.setLayout(null);
        setIconPaths();

        panel.setBackground(colorPanel);

        player=new JLabel(playername,SwingConstants.CENTER);
        player.setText(playername);
        player.setBounds(ICONSIZE*3/4,ICONSIZE*5/2,ICONSIZE*3/2,ICONSIZE/3);
        player.setFont(new Font("Comic Sans",Font.BOLD,25));
        player.setBorder(BorderFactory.createLineBorder(new Color(0, 54, 18),5));
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
        //setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
        validate();

    }

    private void setIconPaths(){
        ImageIcon image=new ImageIcon("src/pngchips/image_1_1 (1).png");
        btn1.setIcon(createIcon(image));
        btn1.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_1_2 (1).png");
        btn2.setIcon(createIcon(image));
        btn2.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_1_3.png");
        btn3.setIcon(createIcon(image));
        btn3.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_1.png");
        btn4.setIcon(createIcon(image));
        btn4.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_2.png");
        btn5.setIcon(createIcon(image));
        btn5.setBackground(colorPanel);

        image=new ImageIcon("src/pngchips/image_2_3_prev_ui.png");
        btn6.setIcon(createIcon(image));
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

        btnlist.get(0).setBounds(0, 0, ICONSIZE, ICONSIZE);
        panel.add(btnlist.get(0));

        for (int i = 0; i < btnlist.size(); i++) {
            panel.add(btnlist.get(i));
            btnlist.get(i).setBorder(emptyBorder);
            btnlist.get(i).setBounds(x, y, ICONSIZE, ICONSIZE);
            x += btnlist.get(i).getWidth();
            if (i == 2) {
                y = btnlist.get(i).getHeight();
                x = 0;
            }

        }
    }

    private void setActionListeners(){

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(1);
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(5);
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(25);
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(50);
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(100);
            }
        });

        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betlist.add(500);
                System.out.println(betlist);
            }
        });

    }

    private void placeOkbtn(){

        ok.setBounds(ICONSIZE,ICONSIZE*2,ICONSIZE,ICONSIZE/3);
        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        ok.setBackground(Color.GREEN);
        ok.setForeground(Color.BLACK);
        ok.setFocusable(false);
        ok.setFont(new Font("Comic Sans",Font.BOLD,25));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getTotalBet() <= Data.spielerMap.get(whichPlayer).getGeld()) {
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
            }
        });
        panel.add(ok);

        allin.setBounds(ICONSIZE*2,ICONSIZE*2,ICONSIZE,ICONSIZE/3);
        allin.setAlignmentX(Component.CENTER_ALIGNMENT);
        allin.setBackground(Color.GREEN);
        allin.setForeground(Color.BLACK);
        allin.setFont(new Font("Comic Sans",Font.BOLD,25));
        allin.setFocusable(false);
        allin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save bet to betMap
                Data.betMap.put(whichPlayer, Data.spielerMap.get(whichPlayer).getGeld());
                //save new money (1)
                Spieler spieler = new Spieler(Data.spielerMap.get(whichPlayer).getSpielername(), Data.spielerMap.get(whichPlayer).getID(), Data.spielerMap.get(whichPlayer).getSpieleAnzahl(), Data.spielerMap.get(whichPlayer).getSiegeAnzahl(), 1);
                Data.spielerMap.put(whichPlayer, spieler);
                if(whichPlayer == Data.valueMap.get("spieler")-1)
                    Data.valueMap.put("openStages", 1);
                try {
                    animation();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        panel.add(allin);

        reduce.setBounds(0,ICONSIZE*2,ICONSIZE,ICONSIZE/3);
        reduce.setAlignmentX(Component.CENTER_ALIGNMENT);
        reduce.setBackground(Color.GREEN);
        reduce.setForeground(Color.BLACK);
        reduce.setFont(new Font("Comic Sans",Font.BOLD,25));
        reduce.setFocusable(false);
        reduce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!betlist.isEmpty()) {
                    betlist.remove(betlist.size() - 1);
                    System.out.println(betlist);
                }
            }
        });
        panel.add(reduce);

    }

    private ImageIcon createIcon(ImageIcon imgicon){

        Image img=imgicon.getImage();
        Image newimg=img.getScaledInstance(ICONSIZE,ICONSIZE,Image.SCALE_SMOOTH);
        ImageIcon newIcon=new ImageIcon(newimg);
        return newIcon;
    }

    public int getTotalBet(){
        bet=0;
        for(int i:betlist)
            bet+=i;
        return bet;
    }

}

