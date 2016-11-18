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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AddressBookFXMLController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private void searchAction(ActionEvent event) {
        readFile();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }    
    private void readFile(){
        ArrayList<Person> people = new ArrayList();
        try(BufferedReader file = new BufferedReader(new FileReader("Kontakty.txt"))){
            String line;
            String[] components;
            while((line = file.readLine()) != null){
                components = line.split("\\|");
                people.add(new Person(components[0], components[1], components[2]));
            }
            Collections.sort(people);
            for(Person p : people) System.out.println(p);
        }catch(IOException e){
            Logger.getLogger(AddressBookFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
