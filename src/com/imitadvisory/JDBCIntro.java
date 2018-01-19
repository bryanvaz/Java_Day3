package com.imitadvisory;
import java.sql.*;

public class JDBCIntro {

    /**
     * First Connection to JDBC
     */
    public static void testConnect(){
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

    /**
     * Works only in Java 7 or lower
     * The ODBC bridge has been removed in v8
     */
    public static void odbcBridge(){
        try {
            //Load the JdbcOdbc Driver
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            //Specify the Database URL where the DNS will be and the user and password
            Connection con = DriverManager.getConnection("jdbc:odbc:sonoo", "root", "password");

            //Initialize the statement to be used, specify if rows are scrollable
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //ResultSet will hold the data retrieved
            ResultSet rs = stmt.executeQuery("SELECT * FROM emp");

            //Display the results
            while(rs.next()){
                System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getInt("age"));
            }
        }
        catch(Exception e){ System.out.println(e);}

    }

}
