package com.imitadvisory;
import java.sql.*;

public class CRUDOperations {

    private static String dbName = "sonoo";

    public static void readTable() {
        sopl("\nViewing Table by Column Name:");


        Statement stmt = null;

        String query =
                "select COF_NAME, SUP_ID, PRICE, " +
                        "SALES, TOTAL " +
                        "from " + dbName + ".COFFEES";

        try {
            Connection con = CRUDOperations.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            sopl("coffeename" + "\t|\t" + "supplierID" +
                    "\t|\t" + "price" + "\t|\t" + "sales" +
                    "\t|\t" + "total");
            sopl("-----------------------------------");
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(coffeeName + "\t\t" + supplierID +
                        "\t\t" + price + "\t\t" + sales +
                        "\t\t" + total);
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

    public static void viewTableByIndex() {
        sopl("\nViewing Table by Index:");

        Statement stmt = null;
        String query =
                "select COF_NAME, SUP_ID, PRICE, " +
                        "SALES, TOTAL from COFFEES";

        try {
            Connection con = CRUDOperations.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            sopl("-----------------------------------");
            while (rs.next()) {
                String coffeeName = rs.getString(1);
                int supplierID = rs.getInt(2);
                float price = rs.getFloat(3);
                int sales = rs.getInt(4);
                int total = rs.getInt(5);
                System.out.println(coffeeName + "\t\t" + supplierID +
                        "\t\t" + price + "\t\t" + sales +
                        "\t\t" + total);
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

    public static void updatePrices(float percentage) {
        sopl("\nAdjusting prices by " + percentage * 100 + "%");

        Statement stmt = null;
        try {
            Connection con = CRUDOperations.getConnection();

            stmt = con.createStatement();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(
                    "SELECT * FROM " + dbName + ".COFFEES");

            while (uprs.next()) {
                float f = uprs.getFloat("PRICE");
                uprs.updateFloat( "PRICE", f * percentage);
                uprs.updateRow();
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

    public static void readPrices() {
        sopl("\nViewing Prices:");


        Statement stmt = null;

        String query =
                "select COF_NAME, PRICE " +
                        "from " + dbName + ".COFFEES";

        try {
            Connection con = CRUDOperations.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            sopl("coffeename" + "\t|\t" + "price");
            sopl("-----------------------------------");
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                float price = rs.getFloat("PRICE");
                System.out.println(coffeeName + "\t|\t" + price );
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

    public static void batchCreate(){
        sopl("\nBatch Adding:");

        Statement stmt = null;
        try {
            Connection con = CRUDOperations.getConnection();
            con.setAutoCommit(false);
            stmt = con.createStatement();

            stmt.addBatch(
                    "INSERT INTO COFFEES " +
                            "VALUES('Amaretto', 49, 9.99, 0, 0)");

            stmt.addBatch(
                    "INSERT INTO COFFEES " +
                            "VALUES('Hazelnut', 49, 9.99, 0, 0)");

            stmt.addBatch(
                    "INSERT INTO COFFEES " +
                            "VALUES('Amaretto_decaf', 49, " +
                            "10.99, 0, 0)");

            stmt.addBatch(
                    "INSERT INTO COFFEES " +
                            "VALUES('Hazelnut_decaf', 49, " +
                            "10.99, 0, 0)");

            int [] updateCounts = stmt.executeBatch();
            con.commit();

            sopl("Update Stats:");
            for(int i = 0; i<updateCounts.length;i++)
                sopl("Update for statement "+ (i + 1) +" - Records Affected: "+ updateCounts[i]);

        } catch(BatchUpdateException b) {System.out.println(b);
        } catch(SQLException ex) {System.out.println(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e){ System.out.println(e);}
            }
        }
    }

    public static void deleteDecaf() {
        sopl("\nDeleting Decaf Items:");


        Statement stmt = null;

        String query =
                "select COF_NAME " +
                        "from " + dbName + ".COFFEES";

        try {
            Connection con = CRUDOperations.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                if(coffeeName.toLowerCase().contains("decaf")) {
                    System.out.println("Deleting : " + coffeeName);
                    rs.deleteRow();
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

    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false","root","password");
    }

    private static void sopl(Object obj){
        System.out.println(obj);
    }
}
