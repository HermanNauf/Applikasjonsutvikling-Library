package admin;

import database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    private dbConnection conn;
    private ObservableList<AuthorData> data;
    private String sql = "SELECT * FROM author";
    private String deleteAuthor = "DELETE FROM author WHERE author_id = ?";

    @FXML
    private TextField id;
    @FXML
    private TextField authorName;

    @FXML
    private TableColumn<AuthorData, String> idColumn;
    @FXML
    private TableColumn<AuthorData, String> authorNameColumn;

    @FXML
    private TableView<AuthorData> authorTable;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.conn = new dbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            loadAuthorData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void loadAuthorData()throws SQLException{
        try {
            Connection con = dbConnection.dbConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet set = con.createStatement().executeQuery(sql);
            while (set.next()){
                this.data.add(new AuthorData(set.getString(1), set.getString(2)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.idColumn.setCellValueFactory(new PropertyValueFactory<AuthorData, String>("ID"));
        this.authorNameColumn.setCellValueFactory(new PropertyValueFactory<AuthorData, String>("authorName"));

        this.authorTable.setItems(null);
        this.authorTable.setItems(this.data);
    }

    @FXML
    public void addAuthor(ActionEvent event) throws SQLException {
        String insertAuthor = "INSERT INTO author(author_id,author_name) VALUE (?,?)";

        try {
            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(insertAuthor);
            statement.setString(1, this.id.getText());
            statement.setString(2, this.authorName.getText());
            statement.execute();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadAuthorData();
        clearField();


    }
    @FXML
    private void clearField(){
        this.id.setText("");
        this.authorName.setText("");
    }
    @FXML
    private void deleteAuthor() throws SQLException {
        AuthorData data = authorTable.getSelectionModel().getSelectedItem();

        Connection con = dbConnection.dbConnection();
        PreparedStatement statement = con.prepareStatement(deleteAuthor);
        statement.setString(1, data.getID());
        //statement.setString(2, data.getAuthorName());
        statement.execute();
        statement.close();
        loadAuthorData();

    }
}
