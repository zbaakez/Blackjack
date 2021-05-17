import Game.BlackJack;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Frame  extends JFrame {
    int player;
    int scene;
    Field field = new Field(this, 0);
    int turn = 0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Label[] textfields = new Label[field.getBlackjack().getPlayers().length];

    //public Frame(int players, int scene) throws InterruptedException {

    public Frame() throws InterruptedException {
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
    }

    public void startGame() {


    }

    public void setTextfields() {


        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            textfields[i] = new Label();
        }

        int c = 0;
        for (int j = 0; j < field.getBlackjack().getPlayers().length; j++) {
            textfields[j].setBounds(field.scaleX(270 + c), field.scaleY(700), 70, 30);
            c += 270;
        }

        for (int o = 0; o < field.getBlackjack().getPlayers().length; o++) {
            this.add(textfields[o]);
        }


    }

    public void setValueToTextfields() {
        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            textfields[i].setText(String.valueOf(field.getBlackjack().getValue(field.getBlackjack().getPlayers()[i])));
        }

    }


    public void setButtons() {
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