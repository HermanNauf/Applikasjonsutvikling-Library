package loginApp;

import admin.adminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.studentController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    public loginController() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.loginModel.isDbConnected()){
            this.dbstatus.setText("Connected...");
        }
        else {
            this.dbstatus.setText("Not connected...");
            }
        this.combobox.setItems(FXCollections.observableArrayList(option.values()));
    }
    @FXML
    public void Login(ActionEvent event){
        try {

            if(this.loginModel.isLogin(this.username.getText(), this.password.getText(), ((option)this.combobox.getValue()).toString())){
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                switch(((option)this.combobox.getValue()).toString()){
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                }
            }
            else {
                this.loginStatus.setText("Wrong credentials");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void studentLogin(){
        try {
            Stage studentStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/students/student.fxml").openStream());

            studentController studentController = (studentController)loader.getController();

            Scene scene = new Scene(root);
            studentStage.setScene(scene);
            studentStage.setTitle("Student");
            studentStage.setResizable(false);
            studentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void adminLogin(){
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane)adminLoader.load(getClass().getResource("/admin/admin.fxml").openStream());

            adminController admincontroller = (adminController)adminLoader.getController();

            Scene scene = new Scene(adminRoot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
