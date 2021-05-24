package Game;

import Game.BlackJack;
import Game.Card;
import Game.CardImages;
import Game.Player;
import Model.Data;
import javafx.stage.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * creates Field
 */
public class Field extends Canvas {
    private int players;
    private CardImages cardImages = new CardImages();
    private Frame frame;
    private BlackJack blackjack;
    private BufferedImage cardSkin;
    private int scene;

    private Color sceneColor;

    private int value = 0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int) size.getWidth();
    private int height = (int) size.getHeight();
    private float originalWidth = 1920;
    private float originalHeight = 1080;

    private Thread thread;

    private BufferedImage background;

    /**
     * sets the frame of the field and its background
     * @param frame, frame of the field
     * @param scene, selected scene
     */
    public Field(Frame frame, int scene){
        this.frame = frame;
        try {
            if(scene==1){
                background = ImageIO.read(new File("casino.png"));
                cardSkin = ImageIO.read(new File("0casino.png"));
                sceneColor = new Color(0, 120, 25);
            }
            else if(scene==2){
                background = ImageIO.read(new File("tirol.png"));
                cardSkin = ImageIO.read(new File("0tirol.png"));
                sceneColor = new Color(136, 0, 27);
            }
            else if(scene==3){
                background = ImageIO.read(new File("beach.png"));
                cardSkin = ImageIO.read(new File("0beach.png"));
                sceneColor = new Color(0, 168, 243);
            }
            else if(scene==4){
                background = ImageIO.read(new File("space.png"));
                cardSkin = ImageIO.read(new File("0space.png"));
                sceneColor = new Color(100, 90, 190);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.scene = scene;
        this.blackjack=new BlackJack(Data.valueMap.get("spieler"), Data.valueMap.get("bot"), frame);
    }

    /**
     * Thread that adds the components of the field and keeps drawing them
     */
    public void start(){

        thread = new Thread(()-> {
            boolean closer = false;
            while(true) {
                if(Data.getCloseFrame() || closer) {
                    frame.dispose();
                    Data.setCloseFrame(false);
                    break;
                }
                try {
                    BufferStrategy bs = this.getBufferStrategy();
                    if (bs == null) {
                        createBufferStrategy(3);
                        continue;
                    }

                    Graphics g = bs.getDrawGraphics();
                    draw(g);


                    g.dispose();
                    bs.show();
                    uiUpdate();
                }catch (IllegalStateException e){

                }
                try {
                    do {
                        Thread.sleep(10);
                    } while(!frame.isDrawReady());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                closer = Data.getCloseFrame();
            }

        });
        frame.setVisible(true);
        thread.start();
        blackjack.start();
    }

    /**
     * sets the positions of the cards
     * @param g, current graphics object
     */
    public void draw(Graphics g)
    {
        g.drawImage(background, 0, 0, scaleX(originalWidth), scaleY(originalHeight), null);
        for(int i=0;i<blackjack.getPlayers().length;i++){
            int count=0;
            Player p = blackjack.getPlayers()[i];
            for (int j=0;j<p.getHand().size();j++){
                drawCard(getXPos(i) + count, getYPos(i), p.getHand().get(j), g);
                count+=40;
            }
        }
        int count=0;
        int x = (int) originalWidth;
        for (int j=0;j<blackjack.getDealer().getHand().size();j++){
            drawCard((x - scaleX(103)) / 2 + count, 100, blackjack.getDealer().getHand().get(j), g);
            count+=40;
        }

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        g.drawImage(cardSkin,scaleX((dimension.width - scaleX(103)) / 2), scaleY((dimension.height / 2 - scaleY(158))), scaleX(103), scaleY(158), null);
    }

    /**
     * updates the ui
     */
    public void uiUpdate(){
        frame.setValueToTextfields(blackjack.getTurnPlayer());
    }

    /**
     * draws the cards at the field
     * @param x, x-coordinate for the card
     * @param y, y-coordinate for the card
     * @param card, which card
     * @param g, current graphics object
     */
    public void drawCard(int x, int y, Card card, Graphics g){
        BufferedImage img = cardToImage(card);
        g.drawImage(img, scaleX(x), scaleY(y), scaleX(103), scaleY(158), null);
    }

    /**
     *
     * @param card
     * @return
     */
    public BufferedImage cardToImage(Card card){
        int type = Card.cardTypeToInt(card.getType());
        int value = Card.cardValueToInt(card.getValue());
        String typeString;
        String filename;
        if(type==0){
            typeString="H";
        }
        else if(type==1){
            typeString="C";
        }
        else if(type==2){
            typeString="D";
        }
        else if(type==3){
            typeString="S";
        }
        else{
            if(this.scene == 1)
                typeString="casino";
            else if(this.scene == 2)
                typeString="tirol";
            else if(this.scene == 3)
                typeString="beach";
            else
                typeString="space";
        }
        value++;

        filename=value+typeString+".png";
        try {
            BufferedImage img = ImageIO.read(new File(filename));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * gets the x coordinate of the given player
     * @param player, given player
     * @return, x coordinate
     */
    public int getXPos(int player){
        int n = ((int) (originalWidth - 200)) / blackjack.getPlayers().length;
        return 50 + player * n + n / 2;
    }

    /**
     * gets the y coordinate of the given player
     * @param player, given player
     * @return, y coordinate
     */
    public int getYPos(int player){
        int count=0;
        if(player==0 || player==4){
            count-=50;
        }
        else if(player==2){
            count+=50;
        }
        return 800+count;
    }

    /**
     * scales the x coordinates for monitor size
     * @param originalX
     * @return
     */
    public int scaleX(double originalX){
        return (int) (originalX / originalWidth * this.width);
    }

    /**
     * scales the y coordinates for monitor size
     * @param originalY
     * @return
     */
    public int scaleY(double originalY){
        return (int) (originalY / originalHeight * this.height);
    }

    /**
     * returns current blackjack object
     * @return
     */
    public BlackJack getBlackjack() {
        return blackjack;
    }

    public Color getSceneColor() { return sceneColor; }
}
