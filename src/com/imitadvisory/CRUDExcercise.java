package com.imitadvisory;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class CRUDExcercise {
    public static void start() {
        Scanner sc = new Scanner(System.in);
        boolean contLoop = true;
        while (contLoop) {
            sopl("\n Coffee Stock Management System: (Enter selection) \n");
            sopl("1. Create New Coffee");
            sopl("2. Search for Coffee by Name");
            sopl("3. Search for Coffee by Price");
            sopl("4. Search and Delete for Coffee by Name");
            sopl("5. Exit\n");

            String selection = sc.nextLine();
            try{
                int sel = Integer.parseInt(selection);

                switch (sel){
                    case 1:
                        sopl("1. Create New Coffee");
                        createCoffee();
                        break;
                    case 2:
                        sopl("2. Search for Coffee by Name");
                        searchByName();
                        break;
                    case 3:
                        sopl("3. Search for Coffee by Price");
                        searchByPrice();
                        break;
                    case 4:
                        sopl("4. Search and Delete for Coffee by Name");
                        deleteByName();
                        break;
                    case 5:
                        sopl("Exiting...");
                        contLoop = false;
                        break;
                    default: sopl("Incorrect Selection. Please Try again.");
                        break;

                }
            }catch (Exception e){
                sopl("Incorrect Selection. Please Try again.");
            }

        }
    }

    private static void createCoffee(){
        Scanner sc = new Scanner(System.in);

        //Enter Your Code Here

    }

    private static void searchByName(){
        Scanner sc = new Scanner(System.in);

        //Enter Your Code Here

    }

    private static void searchByPrice(){
        Scanner sc = new Scanner(System.in);

        //Enter Your Code Here

    }

    private static void deleteByName(){
        Scanner sc = new Scanner(System.in);

        //Enter Your Code Here
    }

    private static void sopl(Object obj){
        System.out.println(obj);
    }
}
