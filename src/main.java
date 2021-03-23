import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        dbConnection dbConn = new dbConnection();
        dbConn.selectAllAuthors();
        //dbConn.closeConnection();
        dbConn.selectAllAuthorBooks();
    }
}
