package Game;

import Game.BlackJack;
import Game.Card;
import Game.CardImages;
import Game.Player;
import Model.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Field extends Canvas {
    private int players;
    private CardImages cardImages = new CardImages();
    private Frame frame;
    private BlackJack blackjack;

    private int value = 0;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int) size.getWidth();
    private int height = (int) size.getHeight();
    private float originalWidth = 1920;
    private float originalHeight = 1080;

    private Thread thread;

    private BufferedImage background;

    public Field(Frame frame, int scene){
        this.frame = frame;
        try {
            if(scene==1){
                background = ImageIO.read(new File("casino.png"));
            }
            else if(scene==2){
                background = ImageIO.read(new File("tirol.png"));
            }
            else if(scene==3){
                background = ImageIO.read(new File("beach.png"));
            }
            else if(scene==4){
                background = ImageIO.read(new File("space.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.blackjack=new BlackJack(Data.valueMap.get("spieler")+Data.valueMap.get("bot"), frame);

    }

    public void start(){


        thread = new Thread(()-> {
            int x=0;
            boolean closer = false;
            while(true) {
                x++;
                if(Data.getCloseFrame() || closer) {
                    frame.dispose();
                    Data.setCloseFrame(false);
                    break;
                }

                BufferStrategy bs = this.getBufferStrategy();
                if (bs == null) {
                    createBufferStrategy(3);
                    continue;
                }

                Graphics g = bs.getDrawGraphics();
                draw(g);


                g.dispose();
                bs.show();

                try {
                    closer = Data.getCloseFrame();
                    do {
                        Thread.sleep(10);
                    } while(!frame.isDrawReady());
                    closer = Data.getCloseFrame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(x<10)
                    uiUpdate();

                closer = Data.getCloseFrame();
            }

        });
        frame.setVisible(true);
        thread.start();
        blackjack.start();
    }

    void bf(){
        BufferStrategy bs = this.getBufferStrategy();;
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
    }

    public void draw(Graphics g){
        g.drawImage(background, 0, 0, scaleX(originalWidth), scaleY(originalHeight), null);
        for(int i=0;i<blackjack.getPlayers().length;i++){
            int count=0;
            Player p = blackjack.getPlayers()[i];
            for (int j=0;j<p.getHand().size();j++){
                drawCard(getXPos(i)+count, getYPos(i), p.getHand().get(j), g);
                count+=40;
            }
        }
        int count=0;
        int x = (int) originalWidth;
        for (int j=0;j<blackjack.getDealer().getHand().size();j++){
            drawCard(x/2+count, 100, blackjack.getDealer().getHand().get(j), g);
            count+=40;
        }
    }

    public void uiUpdate(){
        frame.setValueToTextfields(blackjack.getTurnPlayer());
    }

    public void drawCard(int x, int y, Card card, Graphics g){
        BufferedImage img = cardToImage(card);
        g.drawImage(img, scaleX(x), scaleY(y), scaleX(103), scaleY(158), null);
    }

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
        else{
            typeString="S";
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

    public int getXPos(int player){
        int n=0;
        int x = (int) (originalWidth - 200);
        n = x / blackjack.getPlayers().length;
        return 100+player*n+n/2;
    }

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

    public int scaleX(double originalX){
        return (int) (originalX / originalWidth * this.width);
    }

    public int scaleY(double originalY){
        return (int) (originalY / originalHeight * this.height);
    }

    public int getCardPosition() {

        return 0;
    }

    public BlackJack getBlackjack() {
        return blackjack;
    }
}
