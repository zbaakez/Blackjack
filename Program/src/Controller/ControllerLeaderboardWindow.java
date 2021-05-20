package Controller;

import Model.CryptoException;
import Model.CryptoUtils;
import Model.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Controller class for Leaderboard
 * opens when LeaderboardWindow opens
 * @author Busted
 */
public class ControllerLeaderboardWindow {

    @FXML private TableView<LeaderboardParticipant> tableViewLeaderboard;
    @FXML private TableColumn<LeaderboardParticipant, String> tableColumnName;
    @FXML private TableColumn<LeaderboardParticipant, Integer> tableColumnSpiele;
    @FXML private TableColumn<LeaderboardParticipant, Integer> tableColumnSiege;
    @FXML private TableColumn<LeaderboardParticipant, Integer> tableColumnGeld;

    /**
     * Method calls handleLeaderboard() Method on startup
     * @throws FileNotFoundException
     */
    public void initialize() throws FileNotFoundException {
       handleLeaderboard();
    }

    @FXML private Button btnCloseLeaderboard;
    /**
     * Method handles btnCloseLeaderboard
     * Leaderboardwindow closes when button is clicked
     */
    public void btnCloseLeaderboardClicked(){
        Stage stage = (Stage) btnCloseLeaderboard.getScene().getWindow();
        stage.close(); //Stage szene gets closed after button is clicked
        Data.valueMap.put("openStages", 1);
        File decryptedFile = new File("resources/datadecrypted.csv");
        if(decryptedFile.exists())
            decryptedFile.delete();
    }

    /**
     * Method fills leaderboard with Data
     * @throws FileNotFoundException
     */
    public void handleLeaderboard() throws FileNotFoundException {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnSpiele.setCellValueFactory(new PropertyValueFactory<>("spiele"));
        tableColumnSiege.setCellValueFactory(new PropertyValueFactory<>("siege"));
        tableColumnGeld.setCellValueFactory(new PropertyValueFactory<>("geld"));

        //encrypted File needs to be decrypted to get Data which can be saved to leaderboard
        String key = "iMtheEncrypter!1"; // password for encrypting and decrypting
        File encryptedFile = new File("resources/dataencrypted.csv");
        File decryptedFile = new File("resources/datadecrypted.csv");

        if(encryptedFile.exists()) { //if no encryptedFile exists there is no data, the tableview will be empty
            try {
                CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
            } catch (CryptoException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

            LeaderboardParticipant leaderboardParticipant;
            Scanner scanner = new Scanner(decryptedFile);
            String line;
            //add Data to Leaderboard
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] s = line.split("§");
                leaderboardParticipant = new LeaderboardParticipant(s[0], Integer.parseInt(s[3]), Integer.parseInt(s[4]), Integer.parseInt(s[5]));
                tableViewLeaderboard.getItems().add(leaderboardParticipant);
            }
            scanner.close();
            //decryptedFile.delete();
            //standardsort nach tablecolumn, tablecolumnname
            tableViewLeaderboard.getSortOrder().add(tableColumnGeld);
            tableViewLeaderboard.getSortOrder().add(tableColumnName);
            tableColumnName.setSortable(false);
            //remove Selection Model on click on cell
            tableViewLeaderboard.focusedProperty().addListener((a, b, c) -> {
                if (!tableViewLeaderboard.isPressed()) {
                    tableViewLeaderboard.getSelectionModel().clearSelection();
                }
            });
        }

        //TODO: (BUG) lists sorts ascending on first click on row

    }

}
