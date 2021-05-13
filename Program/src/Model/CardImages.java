package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardImages extends Component {
    MediaTracker tracker;
    Toolkit toolkit;
    List<Image> cards = new ArrayList<>();
    List<String> cardNames = new ArrayList<>();


    public String getFileName(String name){
        String fileName = name +".png";
        return fileName;
    }






}
