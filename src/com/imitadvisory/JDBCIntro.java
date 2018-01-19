package com.imitadvisory;
import java.sql.*;

public class JDBCIntro {

    /**
     * First Connection to JDBC
     */
    public static void TestConnect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            // Create the connection
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false","root","password");
            //here sonoo is database name, root is username and password
            //SSL must be false because we're testing

            Statement stmt=con.createStatement();

            System.out.println("Current Tables in Sonoo Database");
            ResultSet rs=stmt.executeQuery("show tables");
            while(rs.next())
                System.out.println(rs.getString(1));
            System.out.println("Query End.\n");

            System.out.println("Current Employees in emp Table");
            rs=stmt.executeQuery("select * from emp");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

            System.out.println("Query End.\n");

            //Close the connection
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

}
