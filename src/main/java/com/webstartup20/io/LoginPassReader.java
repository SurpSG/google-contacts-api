package com.webstartup20.io;

import java.io.Console;
import java.util.Arrays;

public class LoginPassReader {

    public static String readPassword(){

  Console console = System.console();
        char[] chars = console.readPassword("Password: ");
        String result = "";
        for (Character charr:chars){
            result += charr;
        }
        return result;
    }

    public static String readLogin()

    {
        Console console = System.console();
        return console.readLine("Username: ");
    }

}
