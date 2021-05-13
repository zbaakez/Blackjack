package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Blackjack {

    public static void main(String[] args) throws Exception {

        Frame frame = new Frame();

       /* BlackjackGame mygame = new BlackjackGame();

        mygame.initializeGame();
        do {
            mygame.shuffle();
            mygame.getBets();
            mygame.dealCards();
            mygame.printStatus();
            mygame.checkBlackjack();
            mygame.hitOrStand();
            mygame.dealerPlays();
            mygame.settleBets();
            mygame.printMoney();
            mygame.clearHands();
        } while (mygame.playAgain());

        try
        {
            FileOutputStream outObjectFile = new FileOutputStream("objOut", false);

            ObjectOutputStream outObjectStream = new ObjectOutputStream(outObjectFile);

            outObjectStream.writeObject(mygame);

            outObjectStream.flush();
            outObjectStream.close();
        }
        catch(FileNotFoundException fnfException)
        {
            System.out.println("No file");
        }
        catch(IOException ioException)
        {
            System.out.println("bad IO");
        }

        try
        {
            FileInputStream inObjectFile = new FileInputStream("objOut");

            ObjectInputStream inObjectStream = new ObjectInputStream(inObjectFile);

            Card myNewCard = (Card)inObjectStream.readObject();

            System.out.println(myNewCard);

        }
        catch(FileNotFoundException fnfException)
        {
            System.out.println("No File");
        }
        catch(IOException ioException)
        {
            System.out.println("IO no good");
        }

        catch(ClassNotFoundException cnfException)
        {
            System.out.println("This is not a Card.");
        }*/

    }

}

