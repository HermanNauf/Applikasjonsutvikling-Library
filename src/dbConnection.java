import java.sql.*;
public class dbConnection {
    public static Connection con;
    public static ResultSetMetaData rsmd;
    public static int rowCount;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://158.38.101.75:3306/library","newuser","password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAllAuthors(){
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");

            Statement statement=con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery("select * from author");
            while(result.next())
                System.out.println(result.getString(1)+"  "+result.getString(2));
            rsmd = result.getMetaData();
            result.last();
            System.out.println("This table contains "+ result.getRow() + " rows\n");
            result.beforeFirst();
        }catch(Exception e){ System.out.println(e);}
    }
    public void selectAllAuthorBooks(){
        try{
            Statement statement=con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery("select * from authorsBooks");
            while(result.next())
                System.out.println(result.getString(1) + "  " + result.getString(2));
            rsmd = result.getMetaData();
            result.last();
            System.out.println("This table contains "+ result.getRow() + " rows\n");
            result.beforeFirst();
        }catch(Exception e){ System.out.println(e);}
    }
    public void closeConnection() throws SQLException {
        con.close();
    }

    }
