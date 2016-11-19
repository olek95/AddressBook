package addressbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddressBookFXMLController implements Initializable {
    private final ObservableList<String> data = FXCollections.observableArrayList("sasasas", "sasasas", "dasdsa");
    @FXML
    private Button searchButton, exitButton;
    @FXML
    private TextField nameTextField, lastNameTextField, emailTextField;
    @FXML
    private TableView<Person> informationTableView;
    @FXML
    private TableColumn<Person, String> nameColumn, lastNameColumn, emailColumn;
    @FXML
    private void searchAction(ActionEvent event) {
        ObservableList<Person> rows = FXCollections.observableArrayList(readFile());
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        informationTableView.setItems(rows);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitButton.setOnAction(event -> {
           System.exit(0); 
        });
    }    
    private ArrayList<Person> readFile(){
        ArrayList<Person> people = new ArrayList();
        try(BufferedReader file = new BufferedReader(new FileReader("Kontakty.txt"))){
            String line;
            String[] components;
            while((line = file.readLine()) != null){
                components = line.split("\\|");
                people.add(new Person(components[0], components[1], components[2]));
            }
            Collections.sort(people);
        }catch(IOException e){
            Logger.getLogger(AddressBookFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
        return people;
    }
}
