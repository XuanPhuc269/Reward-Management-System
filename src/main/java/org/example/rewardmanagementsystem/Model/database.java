package org.example.rewardmanagementsystem.Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    public static Connection connectDb(){

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/qlhsdb", "root", "Hoanganh@21");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
