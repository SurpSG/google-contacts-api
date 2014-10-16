package com.webstartup20.utils;


import com.webstartup20.ContactsDownloader;
import com.webstartup20.io.LoginPassReader;

public class ContactsDownloaderFactory {
    public ContactsDownloader createContactsDownloader() {
        ContactsDownloader contactsDownloader = new ContactsDownloader();
        String login = LoginPassReader.readLogin();
        String password = LoginPassReader.readPassword();

        if(contactsDownloader.initContactsService(login, password)){
            return contactsDownloader;
        }

        return null;
    }
}
