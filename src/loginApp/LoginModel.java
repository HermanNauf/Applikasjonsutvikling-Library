package loginApp;

import database.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try{
            this.connection = dbConnection.dbConnection();
            } catch (SQLException e){
                    e.printStackTrace();
            }
        if (this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDbConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String password, String opt)throws Exception {
        PreparedStatement pr = null;
        ResultSet result = null;

        String query = "SELECT * FROM users WHERE username = ? and password = ? and division = ?";
        try {
            pr = this.connection.prepareStatement(query);
            pr.setString(1, user);
            pr.setString(2, password);
            pr.setString(3, opt);

            result = pr.executeQuery();

            boolean bool;

            if(result.next()){
                return true;
            }
            return false;
        }
        catch(SQLException e){
            return false;
        }
        finally{
            {
                pr.close();
                result.close();
            }
        }

    }

}
