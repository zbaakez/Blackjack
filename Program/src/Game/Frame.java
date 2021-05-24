package Game;

import Game.BlackJack;
import Model.CryptoException;
import Model.Data;
import Model.Spieler;
import chips.Chips;
import javafx.application.Platform;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Frame extends JFrame implements KeyListener {
    int player;
    int scene = Data.valueMap.get("szene");
    Field field = new Field(this, scene);
    int turn = 0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private JLabel[] textfieldsPlayername = new JLabel[field.getBlackjack().getPlayers().length];
    private JLabel[] textfieldsPoints = new JLabel[field.getBlackjack().getPlayers().length];
    private JLabel[] textfieldsWager = new JLabel[field.getBlackjack().getPlayers().length];
    private JLabel textFieldMaxPoints = new JLabel("", SwingConstants.CENTER);
    private boolean drawReady=false;

    /**
     * constructor of frame which sets frame to fullscreen,
     * positions the field based on screen size
     * sets the buttons and gets the chips of the players bet
     * @throws IOException
     */
    public Frame() throws IOException {
        //this.scene=scene;
        //this.player=players;
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setButtons();
        setTextfields();
        field.setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());
        this.setVisible(true);
        field.start();
        field.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //now get the bet of all players!
        Chips[] chips = new Chips[Data.valueMap.get("spieler")];
        Data.valueMap.put("openStages", 2);
        for(int i = chips.length-1; i>=0; i--){
            chips[i] = new Chips(Data.spielerMap.get(i).getSpielername(), i, field.getSceneColor());
        }
    }


    /**
     * tree labels per player are created and the text set depending on the players data
     */
    public void deleteTextfields(){
        for (int o = 0; o < field.getBlackjack().getPlayers().length-1; o++) {
            this.remove(textfieldsPlayername[o]);
            this.remove(textfieldsPoints[o]);
            this.remove(textfieldsWager[o]);
        }

        textfieldsPlayername = new JLabel[field.getBlackjack().getPlayers().length];
        textfieldsPoints = new JLabel[field.getBlackjack().getPlayers().length];
        textfieldsWager = new JLabel[field.getBlackjack().getPlayers().length];
        drawReady=false;
        this.remove(field);
    }

    /**
     * tree labels per player are created and the text set depending on the players data
     */
    public void setTextfields() {

        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            textfieldsPlayername[i] = new JLabel("", SwingConstants.CENTER);
            textfieldsPoints[i] = new JLabel("", SwingConstants.CENTER);
            textfieldsWager[i] = new JLabel("", SwingConstants.CENTER);
            if(!Data.spielerMap.containsKey(field.getBlackjack().getPlayers()[i].getId())){
                Spieler spieler = new Spieler("bot"+ (i - Data.valueMap.get("spieler") + 1), i, 0, 0, 9999999); // bot has unlimited money
                Data.spielerMap.put(i, spieler);
            }
            textfieldsPlayername[i].setText(Data.spielerMap.get(field.getBlackjack().getPlayers()[i].getId()).getSpielername());
            textfieldsWager[i].setText("Einsatz!"); // put variable for input here
        }

        for (int j = 0; j < field.getBlackjack().getPlayers().length; j++) {
            textfieldsPlayername[j].setBounds(field.scaleX(field.getXPos(j) + 10), field.scaleY(field.getYPos(j) - 120), field.scaleX(100), field.scaleY(30));
            textfieldsPoints[j].setBounds(field.scaleX(field.getXPos(j) + 10), field.scaleY(field.getYPos(j) - 80), field.scaleX(100), field.scaleY(30));
            textfieldsWager[j].setBounds(field.scaleX(field.getXPos(j) + 10), field.scaleY(field.getYPos(j) - 40), field.scaleX(100), field.scaleY(30));
            setLabel(j);
        }

        for (int o = 0; o < field.getBlackjack().getPlayers().length; o++) {
            this.add(textfieldsPlayername[o]);
            this.add(textfieldsPoints[o]);
            this.add(textfieldsWager[o]);
        }

        textFieldMaxPoints.setOpaque(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        textFieldMaxPoints.setBounds(field.scaleX((int) Math.round(width - (width*0.92))) , field.scaleY((int) Math.round(height - (height*0.90))), field.scaleX(200)+30, field.scaleY(100));
        textFieldMaxPoints.setText("MAX: " + String.valueOf(Data.valueMap.get("maxPoints")));
        textFieldMaxPoints.setFont(new Font("BODONI MT BLACK", Font.BOLD, 37));
        textFieldMaxPoints.setForeground(Color.BLACK);
        textFieldMaxPoints.setBackground(field.getSceneColor());
        textFieldMaxPoints.setBorder(new LineBorder(Color.BLACK, 2));
        this.add(textFieldMaxPoints);

        this.add(field);
        drawReady=true;
    }

    /**
     * returns if draw is ready
     * @return
     */
    public boolean isDrawReady() {
        return drawReady;
    }

    /**
     * the label of the bet is set to the players bet
     * the labels turn red when its the players turn
     * @param turn, indicates which players turn it is
     */
    public void setValueToTextfields(int turn) {
        for (int i = 0; i < field.getBlackjack().getPlayers().length; i++) {
            if(Data.betMap.get(i) != null)
                textfieldsWager[i].setText(String.valueOf(Data.betMap.get(i)));
            else
                textfieldsWager[i].setText("0");
            textfieldsPoints[i].setText(String.valueOf(field.getBlackjack().getValue(field.getBlackjack().getPlayers()[i])));
            if(i==turn) {
                textfieldsPoints[i].setBackground(field.getSceneColor().brighter().brighter());
                textfieldsWager[i].setBackground(field.getSceneColor().brighter().brighter());
                textfieldsPlayername[i].setBackground(field.getSceneColor().brighter().brighter());
            }
            else {
                textfieldsPoints[i].setBackground(field.getSceneColor());
                textfieldsWager[i].setBackground(field.getSceneColor());
                textfieldsPlayername[i].setBackground(field.getSceneColor());
            }
            setLabel(i);
        }
    }

    private void setLabel(int index)
    {
        textfieldsWager[index].setFont(new Font("Comic Sans", Font.BOLD, 20));
        textfieldsPoints[index].setFont(new Font("Comic Sans", Font.BOLD, 20));
        textfieldsPlayername[index].setFont(new Font("Comic Sans", Font.BOLD, 20));
        textfieldsWager[index].setForeground(Color.BLACK);
        textfieldsPoints[index].setForeground(Color.BLACK);
        textfieldsPlayername[index].setForeground(Color.BLACK);
        textfieldsWager[index].setBorder(new LineBorder(Color.BLACK, 2));
        textfieldsPoints[index].setBorder(new LineBorder(Color.BLACK, 2));
        textfieldsPlayername[index].setBorder(new LineBorder(Color.BLACK, 2));
        textfieldsWager[index].setOpaque(true);
        textfieldsPoints[index].setOpaque(true);
        textfieldsPlayername[index].setOpaque(true);
    }

    /**
     * the buttons to perform the game actions are displayed,
     * and their ActionListeners are added, with stand the player cannot take another card,
     * hit he takes another card, split he gets two hands and double he doubles his bet and
     * automatically gets another card
     * @throws IOException
     */
    public void setButtons() throws IOException {
        JButton hit = new JButton("Hit");
        JButton stand = new JButton("Stand");
        JButton split = new JButton("Split");
        JButton dble = new JButton("Double");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
        hit.setBounds(field.scaleX((int) Math.round(width - (width*0.92))) , field.scaleY((int) Math.round(height - (height*0.65))), field.scaleX(200), field.scaleY(100));
        stand.setBounds(field.scaleX(hit.getX() + 300 ), field.scaleY((int) Math.round(height - (height*0.65))), field.scaleX(200), field.scaleY(100));
        split.setBounds(field.scaleX((int) Math.round((width*0.92) - 200) ), field.scaleY((int) Math.round(height - (height*0.65))), field.scaleX(200), field.scaleY(100));
        dble.setBounds(field.scaleX((int) Math.round((width*0.92) - 500)) , field.scaleY((int) Math.round(height - (height*0.65))), field.scaleX(200), field.scaleY(100));

        setJButtons(hit);
        setJButtons(stand);
        setJButtons(split);
        setJButtons(dble);

        this.add(hit);
        this.add(stand);
        this.add(dble);
        this.add(split);

        hit.addActionListener(e -> {
            if(Data.valueMap.get("openStages")==1) {
                try {
                    handleButtonAction("Hit");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (CryptoException cryptoException) {
                    cryptoException.printStackTrace();
                }
            }
        });
        stand.addActionListener(e -> {
            if(Data.valueMap.get("openStages")==1) {
                try {
                    handleButtonAction("Stand");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (CryptoException cryptoException) {
                    cryptoException.printStackTrace();
                }
            }
        });
        split.addActionListener(e -> {
            if(Data.valueMap.get("openStages")==1) {
                try {
                    handleButtonAction("Split");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (CryptoException cryptoException) {
                    cryptoException.printStackTrace();
                }
            }
        });
        dble.addActionListener(e -> {
            if(Data.valueMap.get("openStages")==1) {
                try {
                    handleButtonAction("Double");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (CryptoException cryptoException) {
                    cryptoException.printStackTrace();
                }
            }
        });
    }

    /**
     * style of button is set and a MouseListener added to change
     * the color when hovered over it
     * @param bt, JButton parameter
     */
    public void setJButtons(JButton bt){
        bt.setBackground(field.getSceneColor());
        bt.setForeground(Color.BLACK);
        bt.setBorder(new LineBorder(Color.BLACK, 2));
        bt.setFocusPainted(false);
        bt.setFont(new Font("BODONI MT BLACK", Font.BOLD, 40));
        bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bt.setBackground(field.getSceneColor().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {

                bt.setBackground(field.getSceneColor());
                bt.setForeground(Color.BLACK);
            }
        });
    }

    /**
     * The text is checked and the corresponding action called
     * @param button, Text of button
     * @throws IOException
     * @throws CryptoException
     */
    public void handleButtonAction(String button) throws IOException, CryptoException {
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

    /**
     * keyEvents that could cause issues to the program are handled
     * @param e, KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {

        //handle ESC key
        if((int)e.getKeyChar() == 27 && Data.valueMap.get("openStages") == 1) {
            Platform.runLater(() -> {
                ViewEscScreen viewEscScreen = new ViewEscScreen();
                try {
                    viewEscScreen.openEscScreen();
                } catch (Exception e1) {

                }
            });
        }

        //handle alt f4 key

    }
    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) { }

    public void startGame() { }
}