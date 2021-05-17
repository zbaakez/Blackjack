package Game;

import Game.BlackJack;
import Model.Data;
import Model.Spieler;
import chips.Chips;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Frame  extends JFrame {
    int player;
    int scene = Data.valueMap.get("szene");
    Field field = new Field(this, scene);
    int turn = 0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Label[] textfieldsPlayername = new Label[field.getBlackjack().getPlayers().length];
    private Label[] textfieldsPoints = new Label[field.getBlackjack().getPlayers().length];
    private Label[] textfieldsWager = new Label[field.getBlackjack().getPlayers().length];

    public Frame() throws IOException {
        //this.scene=scene;
        //this.player=players;
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setButtons();
        setTextfields();
        this.add(field);
        field.setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());
        this.setVisible(true);
        field.start();

        //now get the bet of all players!
        Chips[] chips = new Chips[Data.valueMap.get("spieler")];
        for(int i = chips.length-1; i>=0; i--){
            chips[i] = new Chips(Data.spielerMap.get(i).getSpielername());
        }
    }

    public void startGame() {


    }

    public void setTextfields() {

        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            textfieldsPlayername[i] = new Label();
            textfieldsPoints[i] = new Label();
            textfieldsWager[i] = new Label();
            if(!Data.spielerMap.containsKey(i)){
                Spieler spieler = new Spieler("bot"+String.valueOf(i-Data.valueMap.get("spieler")+1), i, 0, 0, 9999999); // bot has unlimited money
                Data.spielerMap.put(i, spieler);
            }
            textfieldsPlayername[i].setText(Data.spielerMap.get(i).getSpielername());
            textfieldsWager[i].setText("Einsatz!"); // put variable for input here
        }

        for (int j = 0; j < field.getBlackjack().getPlayers().length; j++) {
            textfieldsPlayername[j].setBounds(field.getXPos(j) + 10, field.getYPos(j) - 120, 100, 30);
            textfieldsPoints[j].setBounds(field.getXPos(j) + 10, field.getYPos(j) - 80, 100, 30);
            textfieldsWager[j].setBounds(field.getXPos(j) + 10, field.getYPos(j) - 40, 100, 30);
            textfieldsWager[j].setFont(new Font("BODONI MT BLACK", Font.BOLD, 20));
            textfieldsPoints[j].setFont(new Font("BODONI MT BLACK", Font.BOLD, 20));
            textfieldsPlayername[j].setFont(new Font("BODONI MT BLACK", Font.BOLD, 20));

        }

        for (int o = 0; o < field.getBlackjack().getPlayers().length; o++) {
            this.add(textfieldsPlayername[o]);
            this.add(textfieldsPoints[o]);
            this.add(textfieldsWager[o]);
        }

    }

    public void setValueToTextfields(int turn) {
        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            textfieldsPoints[i].setText(String.valueOf(field.getBlackjack().getValue(field.getBlackjack().getPlayers()[i])));
            if(i==turn) {
                textfieldsPoints[i].setBackground(Color.RED);
                textfieldsWager[i].setBackground(Color.RED);
                textfieldsPlayername[i].setBackground(Color.RED);

            }
            else {
                textfieldsPoints[i].setBackground(Color.WHITE);
                textfieldsWager[i].setBackground(Color.WHITE);
                textfieldsPlayername[i].setBackground(Color.WHITE);
            }
        }

    }


    public void setButtons() throws IOException {
        JButton hit = new JButton("Hit");
        JButton stand = new JButton("Stand");
        JButton split = new JButton("Split");
        JButton dble = new JButton("Double");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        hit.setBounds((int) Math.round(width - (width*0.92)) , (int) Math.round(height - (height*0.65)), 200, 100);
        stand.setBounds(hit.getX() + 300 , (int) Math.round(height - (height*0.65)), 200, 100);
        split.setBounds((int) Math.round((width*0.92) - 200) , (int) Math.round(height - (height*0.65)), 200, 100);
        dble.setBounds((int) Math.round((width*0.92) - 500) , (int) Math.round(height - (height*0.65)), 200, 100);

        setJButtons(hit);
        setJButtons(stand);
        setJButtons(split);
        setJButtons(dble);

        this.add(hit);
        this.add(stand);
        this.add(dble);
        this.add(split);

        hit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction("Hit");
            }
        });
        stand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction("Stand");
            }
        });
        split.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction("Split");
            }
        });
        dble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction("Double");
            }
        });

    }

    public void setJButtons(JButton bt){
        bt.setBackground(Color.YELLOW);
        bt.setForeground(Color.BLACK);
        bt.setFocusPainted(false);
        bt.setFont(new Font("BODONI MT BLACK", Font.BOLD, 40));
        bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bt.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bt.setBackground(Color.YELLOW);
            }
        });
    }

    public void handleButtonAction(String button) {
        if (button.equals("Hit")) {
            field.getBlackjack().action(BlackJack.Action.HIT);
        } else if (button.equals("Stand")) {
            field.getBlackjack().action(BlackJack.Action.STAND);
        } else if (button.equals("Split")) {
            field.getBlackjack().action(BlackJack.Action.SPLIT);
        } else if (button.equals("Double")) {
            field.getBlackjack().action(BlackJack.Action.DOUBLE_DOWN);
        }
    }
}