package com.webstartup20;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.webstartup20.io.CsvContactsParser;
import com.webstartup20.ui.Menu;
import com.webstartup20.ui.UserAction;
import com.webstartup20.utils.ContactsDownloaderFactory;
import com.webstartup20.utils.FileUtils;
import com.webstartup20.utils.LoggingUtil;

import java.io.*;

import java.net.URL;
import java.util.*;

import static com.webstartup20.utils.DataUtils.generateListForRecordToFile;
import static com.webstartup20.utils.DataUtils.getContactList;

/**
 * To change the method of authorization(command line or browser)
 * just change the constructor of the ContactsDownloader
 */
public class ContactsDownloader {
    private String userName;
    private String password;
    public static final String CSV = ".csv";
    public static final String URL_FOR_FEED = "https://www.google.com/m8/feeds/contacts/default/full";
    public static final String PERMISSIONS_PATH = "permissions/plus_sample/StoredCredential";
    public static final String APP_NAME = "webstartup20-contactsRetriever-1.0";
    private static ContactsDownloaderFactory contactsDownloaderFactory = new ContactsDownloaderFactory();
    private ContactFeed resultFeed;
    private List<Map<String, List<String>>> contacts;
    private ContactsService myService;

    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();

        while (true){
            UserAction userAction = menu.requestUserAction();
            if(userAction == null){
                continue;
            }
            ContactsDownloader contactsDownloader;
            switch (userAction) {
                case EXPORT:
                    contactsDownloader = contactsDownloaderFactory.createContactsDownloader();
                    if(contactsDownloader == null){
                        LoggingUtil.printMessage("Authorization error. Try again.");
                        continue;
                    }
                    contactsDownloader.exportContacts();
                    break;
                case IMPORT:
                    contactsDownloader = contactsDownloaderFactory.createContactsDownloader();

                    if(contactsDownloader == null){
                        LoggingUtil.printMessage("Authorization error. Try again.");
                        continue;
                    }
                    CsvContactsParser c = new CsvContactsParser(menu.getFilePath());
                    List<ContactEntry> contactEntries = c.getContacts();
                    contactsDownloader.importContacts(contactEntries);
                    LoggingUtil.printContactEntries(contactEntries);
                    break;
                case EXIT:
                    System.exit(0);
            }
        }
    }

    /**
     * Method for command line authorization
     */
    private void downloadContacts() {
        try {

            resultFeed = getContactFeed(myService);
            contacts = getContactList(resultFeed);
        } catch (AuthenticationException e) {
            System.err.println("Authentication error. Incorrect email or password. Also check your security settings at https://www.google.com/settings/security/lesssecureapps");
        } catch (ServiceException e) {
            System.err.println("Error while processing a GDataRequest");
        } catch (IOException e) {
            System.err.println("Error while reading data from server");
        }
    }

    /**
     * Method for browser authorization
     */
    /**
     * private void downloadContacts() {
     * try {
     * ContactsService myService = new ContactsService(APP_NAME);
     * myService.setOAuth2Credentials(AuthorizationHelper.authorize());
     * resultFeed = getContactFeed(myService);
     * contacts = getContactList(resultFeed);
     * } catch (ServiceException e) {
     * System.err.println("Error authorizing your account with Google");
     * } catch (IOException e) {
     * System.err.println("Error reading data from remote source");
     * } catch (Exception e) {
     * System.err.println("Some problem occurred. Check your connection and input parameters");
     * } finally {
     * FileUtils.deleteFile(PERMISSIONS_PATH);
     * }
     * }
     */
    private ContactFeed getContactFeed(ContactsService myService) throws ServiceException, IOException {
        URL feedUrl = new URL(URL_FOR_FEED);
        Query myQuery = new Query(feedUrl);
        myQuery.setMaxResults(Integer.MAX_VALUE);
        ContactFeed resultFeed = myService.getFeed(myQuery, ContactFeed.class);
        return resultFeed;
    }

    public void writeContactsToCSV() {

        if (contacts == null) {
            return;
        }

        List<String> data = generateListForRecordToFile(contacts);

        String title = FileUtils.getFileNameTimeStamp() + "_" + resultFeed.getTitle().getPlainText();
        FileUtils.writeToFile(data, title + CSV);
    }

    public List<Map<String, List<String>>> getContacts() {
        return contacts;
    }

    private void exportContacts() {
        downloadContacts();
        writeContactsToCSV();
        LoggingUtil.printContacts(getContacts());
    }

    private void importContacts(List<ContactEntry> contactEntries) {
        for (ContactEntry contactEntry : contactEntries) {
            try {
                URL feedUrl = new URL(URL_FOR_FEED);
                myService.insert(feedUrl, contactEntry);
            } catch (IOException e) {
                System.err.println("Failed to import contacts, please check your internet connection");
            } catch (ServiceException e) {
                System.err.println("Failed to import contacts, please check the input parameters");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }



    public boolean initContactsService(String userName, String password) {
        myService = new ContactsService(APP_NAME);
        try {
            myService.setUserCredentials(userName, password);
            return true;
        } catch (AuthenticationException e) {
            System.err.println("Authentication error. Incorrect email or password. Also check your security settings at https://www.google.com/settings/security/lesssecureapps");
        }
        return false;
    }
}
