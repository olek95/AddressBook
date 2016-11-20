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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Klasa <code>AddressBookFXMLController</code> reprezentuje sterowanie programem 
 * będącym książką zawierającą adresy mailowe. Ksiązka ta zapisana jest w pliku tekstowym
 * w formacie imię|nazwisko|mail, gdzie imię i nazwisko składa się tylko z liter, 
 * a mail jest postaci: tekst@tekst.tekst, gdzie tekst (litery + liczby) po znaku @ może występować 
 * dowolną liczbę razy, lecz musi być oddzielony kropką. Przykładowy prawidłowy rekord: 
 * Aleksander|Sklorz|olek1995@poczta.onet.pl
 * Klasa ta wyświetla wczytane, posortowane dane w tabeli. Posiada pola tekstowe
 * zezwalające na wyszukanie konkretnych rekordów. Pozostawienie pustego pola 
 * wyświetla dowolną wartość w danej kolumnie. Klasa zabezpieczona przed 
 * niepoprawnym formatem zawartości pliku lub wprowadzonych danych w polach tekstowych w programie. 
 * @author AleksanderSklorz
 */
public class AddressBookFXMLController implements Initializable {
    private ArrayList<Person> people;
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
        if(people == null){
            try{
                readFile();
            }catch(IllegalArgumentException e){
                Alert incorrectFormatAlert = new Alert(AlertType.ERROR);
                incorrectFormatAlert.setHeaderText(e.getMessage());
                incorrectFormatAlert.showAndWait();
            }
        }
        String name = nameTextField.getText().trim(), lastName = lastNameTextField.getText().trim(),
                email = emailTextField.getText().trim();
        if((name.equals("") || isCorrectName(name)) && (lastName.equals("") || isCorrectName(lastName)) && (email.equals("") || isCorrectEmail(email))){
            ObservableList<Person> rows = FXCollections.observableArrayList();
            for(Person p : people)
                if(!name.equals("") && p.getName().equals(name) || name.equals(""))
                    if(!lastName.equals("") && p.getLastName().equals(lastName) || lastName.equals(""))
                        if(!email.equals("") && p.getEmail().equals(email) || email.equals(""))
                            rows.add(p);
            nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
            informationTableView.setItems(rows);
        }else{
            Alert incorrectFormatAlert = new Alert(AlertType.ERROR);
            incorrectFormatAlert.setHeaderText("Złe dane. Imię i nazwisko powinno składać się z liter, "
                    + "a email mieć postać: tekst@tekst.test,\n gdzie tekst to liczby, małe litery, duże litery. "
                    + "Tekst po @ może wystąpić w dowolnej liczbie,\n lecz ma być oddzielony kropką.");
            incorrectFormatAlert.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exitButton.setOnAction(event -> {
           System.exit(0); 
        });
    }    
    private void readFile() throws IllegalArgumentException{
        people = new ArrayList();
        try(BufferedReader file = new BufferedReader(new FileReader("Kontakty.txt"))){
            String line;
            String[] components;
            while((line = file.readLine()) != null){
                components = line.split("\\|");
                if(isCorrectFileFormat(components))
                    people.add(new Person(components[0], components[1], components[2]));
                else{
                    people = null;
                    throw new IllegalArgumentException("Niepoprawny format zawartości pliku."
                            + " Każda linia powinna zawierać 3 wartości oddzielone |.\n Imię i nazwisko "
                            + "powinno składać się z liter, a email mieć postać: tekst@tekst.tekst,\n gdzie tekst to liczby, małe litery, duże litery."
                            + " Tekst po @ może wystąpić w dowolnej liczbie,\n lecz ma być oddzielony kropką.");
                }
            }
            Collections.sort(people);
        }catch(IOException e){
            Logger.getLogger(AddressBookFXMLController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private boolean isCorrectFileFormat(String[] components){
        return components.length == 3 && isCorrectName(components[0]) && 
                isCorrectName(components[1]) && isCorrectEmail(components[2]);
    }
    private boolean isCorrectName(String name){
        return name.matches("[a-zA-z]+");
    }
    private boolean isCorrectEmail(String email){
        return email.matches("[a-zA-z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+");
    }
}
