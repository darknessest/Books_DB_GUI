package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static app.db_driver.AdminMode;
import static app.db_driver.connectToUser;


public class LoginWindowController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginStatusLabel = new Label();
        LoginStatusLabel.setVisible(true);
    }

    // LOGIN WINDOW
    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private CheckBox AdminCheckBox;
    @FXML
    private Label LoginStatusLabel;
    @FXML
    private Button StartButton;


    @FXML
    void setAdminCheckBox() {
        if (AdminCheckBox.isSelected()) {
            UsernameField.setDisable(false);
            PasswordField.setDisable(false);
        } else {
            UsernameField.setDisable(true);
            PasswordField.setDisable(true);
        }
    }

    // on star button press
    @FXML
    void Login() {
        if (AdminCheckBox.isSelected()) {
            if (!(UsernameField.getText().isEmpty() && PasswordField.getText().isEmpty()))
                if (!connectToUser(UsernameField.getText(), PasswordField.getText()))
                    LoginStatusLabel.setText("Wrong password or username");
                else {
                    LoginStatusLabel.setText("");
                    AdminMode = true;
                    System.out.println("Admin mode");
                }
            //successfully logged in
        }
        //open next window, close this one


        else {
            connectToUser("buyer", "");
            AdminMode = false;
        }
        LoginStatusLabel.setText("Loading database");
        ((Stage) StartButton.getScene().getWindow()).hide();

    }
}
