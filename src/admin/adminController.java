package admin;

import HTTPserver.HttpHandler;
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
import sun.plugin.dom.html.HTMLBodyElement;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    private dbConnection conn;
    private ObservableList<AuthorData> data;
    private ObservableList<BookData> bookData;
    private String sql = "SELECT * FROM author";
    private String bookSQL = "SELECT * FROM book";
    private String deleteAuthor = "DELETE FROM author WHERE author_id = ?";
    //Socket s = new Socket(InetAddress.getByName("localhost"), 8080);

    @FXML
    private TextField id;
    @FXML
    private TextField authorName;

    @FXML
    private TextField bookID;
    @FXML
    private TextField bookName;
    @FXML
    private TextField publisherName;
    @FXML
    private TextField publishedDate;



    @FXML
    private TableColumn<AuthorData, String> idColumn;
    @FXML
    private TableColumn<AuthorData, String> authorNameColumn;


    @FXML
    private TableColumn<BookData, String> bookIdColumn;
    @FXML
    private TableColumn<BookData, String> bookNameColumn;
    @FXML
    private TableColumn<BookData, String> publisherNameColumn;
    @FXML
    private TableColumn<BookData, String> publishedDateColumn;

    @FXML
    private TableView<AuthorData> authorTable;

    @FXML
    private TableView<BookData> bookTable;

    public adminController() throws IOException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.conn = new dbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            loadAuthorData();
            loadBookData();
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

            //HttpHandler handler = new HttpHandler(s);
            //handler.run();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadAuthorData();
        clearField();


    }

    @FXML
    public void insertBook(ActionEvent event) throws SQLException {
        String insertBook = "INSERT INTO book(book_id, book_name, publisher_name, published_date) VALUE (?,?,?,?)";

        try {
            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(insertBook);
            statement.setString(1, this.bookID.getText());
            statement.setString(2, this.bookName.getText());
            statement.setString(3, this.publisherName.getText());
            statement.setString(4, this.publishedDate.getText());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadAuthorData();
        clearField();


    }
    @FXML
    private void loadBookData()throws SQLException{
        try {
            Connection con = dbConnection.dbConnection();
            this.bookData = FXCollections.observableArrayList();

            ResultSet set = con.createStatement().executeQuery(bookSQL);
            while (set.next()){
                this.bookData.add(new BookData(set.getString(1), set.getString(2),set.getString(3),set.getString(4)));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        this.bookIdColumn.setCellValueFactory(new PropertyValueFactory<BookData, String>("bookID"));
        this.bookNameColumn.setCellValueFactory(new PropertyValueFactory<BookData, String>("bookName"));
        this.publisherNameColumn.setCellValueFactory(new PropertyValueFactory<BookData, String>("publisherName"));
        this.publishedDateColumn.setCellValueFactory(new PropertyValueFactory<BookData, String>("publishedDate"));

        this.bookTable.setItems(null);
        this.bookTable.setItems(this.bookData);
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
