import java.sql.*;
public class dbConnection {
    public static Connection con;

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

            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from author");
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2));
        }catch(Exception e){ System.out.println(e);}
    }
    public void selectAllAuthorBooks(){
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from authorsBooks");
            while(rs.next())
                System.out.println(rs.getString(1)+"  "+rs.getString(2));
        }catch(Exception e){ System.out.println(e);}
    }
    public void closeConnection() throws SQLException {
        con.close();
    }

    }
