package me.wangcai.myblog.manager;

import me.wangcai.myblog.utils.DataBaseUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserManager {

    private final String TABLE_NAME = "users";

    private static UserManager userManager;



    private DataBaseUtil dataBaseUtil;

    public static UserManager getUserManager(){
        if(userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }

    public UserManager(){
        try {
            dataBaseUtil = new DataBaseUtil(TABLE_NAME);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isRegisted(String name) throws SQLException {
        boolean isreg = false;
        Statement stat = dataBaseUtil.getConnection().createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM `blog`.`users` WHERE `name` LIKE '" + name + "'");
        if(rs.next()){
            isreg= true;
        }
        rs.close();
        stat.close();
        return isreg;
    }

    public boolean login(String name,String password) throws SQLException {
        if(!isRegisted(name)){
            return false;
        }
        Statement stat = dataBaseUtil.getConnection().createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM `blog`.`users` WHERE `name` LIKE '" + name +"'");
        rs.next();
        String pass = rs.getString("password");
        rs.close();
        stat.close();
        if(!pass.equals(password)){
            return false;
        }
        return true;
    }

    public boolean register(String name,String password) throws SQLException {
        if(isRegisted(name)){
            return false;
        }
        PreparedStatement stat = dataBaseUtil.getConnection().prepareStatement("INSERT INTO `blog`.`users` (`name`, `password`, `isadmin`) VALUES (?, ?, ?)");
        stat.setString(1,name);
        stat.setString(2,password);
        stat.setInt(3,0);
        stat.execute();
        stat.close();
        return true;
    }
}
