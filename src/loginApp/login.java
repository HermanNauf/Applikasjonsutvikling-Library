package loginApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class login extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("https://www.carechartsuk.co.uk/wp-content/uploads/2014/10/book-reviews-web.png"));
        primaryStage.setTitle("Gruppe 20 - Mandatory assignment - LibraryApp");
        primaryStage.show();
        primaryStage.setResizable(false);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
