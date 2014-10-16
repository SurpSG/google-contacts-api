package com.webstartup20.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;


public class AuthorizationHelper {
    private static final File DATA_STORE_DIR = new File("permissions", "plus_sample");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_ACCOUNT = "webstartup20test";
    private static final String FEED_URL = "https://www.google.com/m8/feeds/contacts/default/full";
    private static final String JSON_FILE_NAME = "webstartup20.json";

    public static Credential authorize() throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        GoogleClientSecrets clientSecrets = extractGoogleClientSecrets();
        GoogleAuthorizationCodeFlow flow = setUpGoogleAuthorizationCodeFlow(httpTransport, dataStoreFactory, clientSecrets);
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(APPLICATION_ACCOUNT);
    }

    private static GoogleAuthorizationCodeFlow setUpGoogleAuthorizationCodeFlow(NetHttpTransport httpTransport, FileDataStoreFactory dataStoreFactory, GoogleClientSecrets clientSecrets) throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(FEED_URL)).setDataStoreFactory(
                dataStoreFactory).build();
    }

    private static GoogleClientSecrets extractGoogleClientSecrets() throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(JSON_FILE_NAME)));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
                            + "into plus-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        return clientSecrets;
    }
}
