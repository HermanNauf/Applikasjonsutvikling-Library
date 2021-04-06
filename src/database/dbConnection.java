package database;

import java.sql.*;

public class dbConnection {

    public static final String URL_STRING = "jdbc:mysql://158.38.101.75:3306/library";
    public static final String USER_NAME = "newuser";
    public static final String PASSWORD = "password";

    public static Connection con;
    public static ResultSetMetaData rsmd;
    public static int rowCount;

    Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    static {
        try {
            con = DriverManager.getConnection(URL_STRING, USER_NAME, PASSWORD);
            System.out.println("Database connection established...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public dbConnection() throws SQLException {
    }

    public static Connection dbConnection() throws SQLException {
        return con;
    }



    public void selectAllAuthors(){
        try{
            ResultSet result = statement.executeQuery("select * from author");
            while(result.next())
                System.out.println(result.getString(1)+"  "+result.getString(2));
            rsmd = result.getMetaData();
            queryInformation(result);
        }catch(Exception e){ System.out.println(e);}
    }
    public void selectAllAuthorBooks(){
        try{
            ResultSet result = statement.executeQuery("select * from authorsBooks");
            while(result.next())
            System.out.println(result.getString(1) + "  " + result.getString(2));
            rsmd = result.getMetaData();
            queryInformation(result);
        }catch(Exception e){ System.out.println(e);}
    }
    public void closeConnection() throws SQLException {
        con.close();
        System.out.println("Database connection closed...");
    }
    public void queryInformation(ResultSet res) throws SQLException {
        res.last();
        System.out.println("This table contains "+ res.getRow() + " rows\n");
        res.beforeFirst();
    }


    }
