package com.webstartup20.utils;


import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;

import java.util.List;
import java.util.Map;

public class LoggingUtil {
    public static void printContacts(List<Map<String, List<String>>> contacts) {
        if (contacts == null) {
            return;
        }
        int i = 0;
        for (Map<String, List<String>> map : contacts) {
            System.out.println("Record # "+(i+1));
            for (String key : map.keySet()) {
                System.out.println(key + " " + map.get(key));

            }
            i++;
            System.out.println("=============================");
        }
        System.out.println("Total records: " + i+"\n");
    }

    public static void printContactEntries(List<ContactEntry> contactEntries) {
        int i = 0;
        for (ContactEntry contactEntry : contactEntries) {
            System.out.println("Record # " + (i+1));
            if (contactEntry.getName() != null) {
                System.out.println("First Name: " + contactEntry.getName().getGivenName());
                System.out.println("Family Name: " + contactEntry.getName().getFamilyName());
            }
            System.out.print("Email: ");
            for (Email email : contactEntry.getEmailAddresses()) {
                System.out.print(email.getAddress() + " ");
            }
            System.out.println();
            i++;
        }
        System.out.println("Total records: "+i+"\n");
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
