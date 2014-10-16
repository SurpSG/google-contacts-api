package com.webstartup20.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Menu {
    private static final int[] ACTIONS = {1,2,3};
    public UserAction requestUserAction() {
        System.out.println("Enter the action here : ");
        System.out.println("1 - export all contacts to csv ");
        System.out.println("2 - import contacts from csv ");
        System.out.println("3 - exit ");
        Integer action = 0;
        try{
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();
            try{
                action = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input parameters");
            }
             UserAction userAction = UserAction.getActionFromIndex(action);
            if (userAction!=null) {
                return userAction;
            }
        }
        catch(IOException e)
        {
            System.out.println("Invalid input parameters");
        }
        return null;
    }

    public String getFilePath() {
        String path = "";
        System.out.println("Please enter the file path");
        try{
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            path = bufferRead.readLine();
        }
        catch(IOException e)
        {
            System.out.println("Invalid input parameters... the application now will be terminated");
        }
        return path;
    }
}
