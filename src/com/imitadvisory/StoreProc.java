package com.imitadvisory;

import java.sql.*;

public class StoreProc {

    public static void runStoredProcedures(String coffeeNameArg, float maximumPercentageArg, float newPriceArg) {
        CallableStatement cs = null;

        try {
            Connection con = getConnection();

            System.out.println("\nCalling the procedure GET_SUPPLIER_OF_COFFEE");
            cs = con.prepareCall("{call GET_SUPPLIER_OF_COFFEE(?, ?)}");
            cs.setString(1, coffeeNameArg);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.executeQuery();

            String supplierName = cs.getString(2);

            if (supplierName != null) {
                System.out.println("\nSupplier of the coffee " + coffeeNameArg + ": " + supplierName);
            } else {
                System.out.println("\nUnable to find the coffee " + coffeeNameArg);
            }

            System.out.println("\nCalling the procedure SHOW_SUPPLIERS");
            cs = con.prepareCall("{call SHOW_SUPPLIERS}");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String supplier = rs.getString("SUP_NAME");
                String coffee = rs.getString("COF_NAME");
                System.out.println(supplier + ": " + coffee);
            }

            System.out.println("\nContents of COFFEES table before calling RAISE_PRICE:");
            CRUDOperations.readTable();

            System.out.println("\nCalling the procedure RAISE_PRICE");
            cs = con.prepareCall("{call RAISE_PRICE(?,?,?)}");
            cs.setString(1, coffeeNameArg);
            cs.setFloat(2, maximumPercentageArg);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.setFloat(3, newPriceArg);

            cs.execute();

            System.out.println("\nValue of newPrice after calling RAISE_PRICE: " + cs.getFloat(3));

            System.out.println("\nContents of COFFEES table after calling RAISE_PRICE:");
            CRUDOperations.readTable();



        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch(Exception e){ System.out.println(e);}
            }
        }
    }

    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo?autoReconnect=true&useSSL=false","root","password");
    }
}
