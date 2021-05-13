package Model;

import Game.BlackJack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame  extends JFrame {
    Field field = new Field(this, Data.valueMap.get("szene"));
    int turn =0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();


    public Frame() throws InterruptedException {
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setButtons();
        setTextfields();
        this.add(field);
        field.setBounds(0, 0, (int)size.getWidth(), (int)size.getHeight() );
        this.setVisible(true);
        field.start();
    }

    public void startGame(){


    }

    public void setTextfields(){

        Label[] textfields = new Label[field.getBlackjack().getPlayers().length];
        Label value1 = new Label();
        Label value2 = new Label();
        Label value3 = new Label();
        Label value4 = new Label();
        Label value5 = new Label();

        textfields[0] = value1;
        textfields[1] = value2;
        textfields[2] = value3;
        textfields[3] = value4;
        textfields[4] = value5;

        textfields[0].setBounds(100, 100, 70, 30);
        textfields[1].setBounds(190, 100, 70, 30);
        textfields[2].setBounds(280, 100, 70, 30);
        textfields[3].setBounds(370, 100, 70, 30);
        textfields[4].setBounds(460, 100, 70, 30);
        this.add(textfields[0]);
        this.add(textfields[1]);
        this.add(textfields[2]);
        this.add(textfields[3]);
        this.add(textfields[4]);

        String value;
        for(int i=0;i<field.getBlackjack().getPlayers().length;i++){
            textfields[i].setText(String.valueOf(field.getBlackjack().getValue(i)));



        }

    }


    //Textfields
    public void setButtons(){
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");
        Button split = new Button("Split");
        Button dble = new Button("double");
        hit.setBounds(field.scaleX(993), field.scaleY(1050), 35, 35);
        stand.setBounds(field.scaleX(1033), field.scaleY(1050), 35, 35);
        split.setBounds(field.scaleX(1073), field.scaleY(1050), 35, 35);
        dble.setBounds(field.scaleX(1113), field.scaleY(1050), 35, 35);
        this.add(hit);
        this.add(stand);
        this.add(dble);
        this.add(split);


        hit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction(hit.getLabel());
            }
        });
        stand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction(stand.getLabel());
            }
        });
        split.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction(split.getLabel());
            }
        });
        dble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction(dble.getLabel());
            }
        });

    }

    public void handleButtonAction(String button){
        if(button.equals("Hit")){
            field.getBlackjack().action(BlackJack.Action.HIT);
        }
        else if(button.equals("Stand")){
            field.getBlackjack().action(BlackJack.Action.STAND);
        }
        else if(button.equals("Split")){
            field.getBlackjack().action(BlackJack.Action.SPLIT);
        }
        else if(button.equals("Double")){
            field.getBlackjack().action(BlackJack.Action.DOUBLE_DOWN);
        }

        setTextfields();
    }









}
