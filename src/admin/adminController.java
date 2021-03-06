package admin;

import database.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    /**
     * Adds author to database. Returns status as a string.
     *
     * @param id of author
     * @param name of author
     * @return status of action as string
     */
    public String addAuthor(String id, String name) {
        String status = "Error: Author not added.";
        String query = "INSERT INTO author(author_id,author_name) VALUE (?,?)";

        try {
            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.execute();
            statement.close();
            status = "Author added: " + name + ", with ID: " + id;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    @FXML
    public String insertBook(ActionEvent event) throws SQLException {
        String insertBook = "INSERT INTO book(book_id, book_name, publisher_name, published_date) VALUE (?,?,?,?)";

        String responseMsg = "Insert failed";

        try {
            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(insertBook);
            statement.setString(1, this.bookID.getText());
            statement.setString(2, this.bookName.getText());
            statement.setString(3, this.publisherName.getText());
            statement.setString(4, this.publishedDate.getText());
            statement.execute();
            statement.close();

            responseMsg = "Insert Completed!";

        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadAuthorData();
        clearField();

        return responseMsg;
    }

    public String insertBook(String book_id, String book_name, String publisher_name, String published_date){
        String responseMsg = "Insert failed";
        String insertBook = "INSERT INTO book(book_id, book_name, publisher_name, published_date) VALUE (?,?,?,?)";

        try {

            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(insertBook);
            statement.setString(1, book_id);
            statement.setString(2, book_name);
            statement.setString(3, publisher_name);
            statement.setString(4, published_date);
            statement.execute();
            statement.close();

            responseMsg = "Insert Completed!";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseMsg;
    }
    public String deleteLoanRecord(String borrower_id, String book_id){
        String responseMsg = "Delete failed";
        String deleteLoan = "DELETE FROM borrowerBooks WHERE borrower_id = ? AND book_id = ?";

        try {

            Connection con = dbConnection.dbConnection();
            PreparedStatement statement = con.prepareStatement(deleteLoan);
            statement.setString(1, borrower_id);
            statement.setString(2, book_id);
            statement.execute();
            statement.close();

            responseMsg = "Delete Completed!";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseMsg;
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

    /**
     * Updates an existing user with new username and password.
     *
     * @param oldName old username
     * @param oldPass old password
     * @param newName new username
     * @param newPass new password
     * @return status message
     */
    public String updateUsernameAndPassword(String oldName, String oldPass, String newName, String newPass) {
        String status = "Error! User not updated";
        String query = "UPDATE users SET username = ?, password = ? WHERE username = ? and password = ?";

        if (newName.length() < 1 || newPass.length() < 1) {
            return status;
        }

        try {
            Connection connection = dbConnection.dbConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, newName);
            statement.setString(2, newPass);
            statement.setString(3, oldName);
            statement.setString(4, oldPass);

            statement.executeUpdate();

            status = "Update completed!";

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
    public String question4(){
        String status = "";
        String query = "SELECT b.borrower_name FROM ((borrower b NATURAL JOIN borrowerBooks l) NATURAL JOIN book k) WHERE b.borrower_id = l.borrower_id  AND book_name = 'Harry Potter and the Goblet of Fire'";
        try {
            Connection connection = dbConnection.dbConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery(query);
            status = "Query executed!\n";
            status += "Query Result: \n";

            while(result.next()){
                status += "" + result.getString(1) + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public String question5(){
        String status = "";
        String query = "SELECT b.book_name, bor.borrower_name, lib.library_name FROM book b, borrower bor, borrowerBooks bbook, library lib WHERE library_name = 'Trondheim Bibliotek' AND lib.library_id = bbook.library_id AND bbook.dueDate = 'today' AND bbook.borrower_id = bor.borrower_id AND bbook.book_id = b.book_id";
        try {
            Connection connection = dbConnection.dbConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery(query);
            status = "Query executed!\n";
            status += "Query Result: \n";

            while(result.next()){
                status += "" + result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }
    public String question6(){
        String status = "";
        String query = "SELECT library_name, count(*) from library INNER JOIN borrowerBooks  on library.library_id = borrowerBooks.library_id GROUP BY library_name";
        try {
            Connection connection = dbConnection.dbConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery(query);
            status = "Query executed!\n";
            status += "Query Result: \n";

            while(result.next()){
                status += "" + result.getString(1) + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

}
