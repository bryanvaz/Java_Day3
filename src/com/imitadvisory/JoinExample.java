package com.imitadvisory;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;

import javax.sql.rowset.JoinRowSet;
import java.sql.*;

public class JoinExample {
    public static void testJoin(){
        sopl("\nTesting Table Join:");


        Statement stmt = null;

        sopl("\nNormal Table Join (Searching for Coffees by Acme, Inc.):");
        String query =
                "SELECT COFFEES.COF_NAME " +
                        "FROM COFFEES, SUPPLIERS " +
                        "WHERE SUPPLIERS.SUP_NAME = 'Acme, Inc.' " +
                        "and " +
                        "SUPPLIERS.SUP_ID = COFFEES.SUP_ID";

        try {
            Connection con = getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            sopl("coffeename");
            sopl("-----------------------------------");
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                System.out.println(coffeeName + "\t\t");
            }

        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e){ System.out.println(e);}
            }
        }
    }

    public static void joinSetTest(String supplierName){
        sopl("\nTesting JoinSet:");


        Statement stmt = null;


        try {

            JoinRowSet jrs = new JoinRowSetImpl();

            CachedRowSetImpl coffees = new CachedRowSetImpl();
            coffees.setCommand("SELECT * FROM COFFEES");
            coffees.setUsername("root");
            coffees.setPassword("password");
            coffees.setUrl("jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false");
            coffees.execute();

            CachedRowSetImpl suppliers = new CachedRowSetImpl();
            suppliers.setCommand("SELECT * FROM SUPPLIERS");
            suppliers.setUsername("root");
            suppliers.setPassword("password");
            suppliers.setUrl("jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false");
            suppliers.execute();

            // Set Match Column
            jrs.addRowSet(coffees, "SUP_ID");

            // Set Match Column
            jrs.addRowSet(suppliers, 1);

            System.out.println("Coffees bought from " + supplierName + ": ");

            while (jrs.next()) {
                if (jrs.getString("SUP_NAME").equals(supplierName)) {
                    String coffeeName = jrs.getString(1);
                    System.out.println("     " + coffeeName);
                }
            }

        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e){ System.out.println(e);}
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false","root","password");
    }


    private static void sopl(Object obj){
        System.out.println(obj);
    }
}
