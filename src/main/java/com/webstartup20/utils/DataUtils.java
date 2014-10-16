package com.webstartup20.utils;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.webstartup20.utils.ContactEntryUtils.*;
import static com.webstartup20.utils.ContactEntryUtils.getEtag;
import static com.webstartup20.utils.ContactEntryUtils.getPhotoLink;
import static com.webstartup20.utils.NameUtils.*;
import static com.webstartup20.utils.NameUtils.getFamilyName;
import static com.webstartup20.utils.NameUtils.getNameSuffix;


public class DataUtils {
    private static final String DELIM = ",";

    private static final String[] HEADER = {
            "Full Name",
            "Name prefix",
            "Given name",
            "Additional name",
            "Family name",
            "Name suffix",
            "Emails",
            "IM addresses",
            "Groups",
            "Extended Properties",
            "Photo Link",
            "Etag"
    };

    public static void putNameDataToMap(Map<String, List<String>> data, List<String> fullName,
                                        List<String> namePrefix,
                                        List<String> givenName,
                                        List<String> additionalName,
                                        List<String> familyName,
                                        List<String> nameSuffix) {
        data.put(HEADER[0], fullName);
        data.put(HEADER[1], namePrefix);
        data.put(HEADER[2], givenName);
        data.put(HEADER[3], additionalName);
        data.put(HEADER[4], familyName);
        data.put(HEADER[5], nameSuffix);
    }

    public static void putNameDataToMap(Map<String, List<String>> data) {
        List<String> emptyList = new ArrayList<>();
        putNameDataToMap(data, emptyList, emptyList, emptyList, emptyList, emptyList, emptyList);
    }

    public static List<String> generateListForRecordToFile(List<Map<String, List<String>>> contacts) {
        List<String> data = new ArrayList<>();
        data.add(getHeader());
        data.addAll(convertToStringList(contacts));
        return data;
    }

    public static List<String> convertToStringList(List<Map<String, List<String>>> contacts) {
        List<String> data = new ArrayList<>();

        for (Map<String, List<String>> map : contacts) {
            appendToList(data, mapToArray(map));
        }
        return data;
    }


    public static void appendToList(List<String> list, String[][] dat) {
        for (int i = 0; i < dat.length; i++) {
            String row = "";
            for (int j = 0; j < dat[i].length; j++) {
                if (dat[i][j] != null) {
                    row += dat[i][j] + DELIM;
                } else {
                    row += " " + DELIM;
                }
            }
            list.add(row);
        }
//        list.add("");
    }

    public static String[][] mapToArray(Map<String, List<String>> dat) {
        int arrayWidth = calculateMaxListSize(dat);
//        System.out.println(arrayWidth);
        String[][] data = new String[HEADER.length][arrayWidth];
        int i = 0;
        for (List<String> values : dat.values()) {
            int j = 0;
            for (String string : values) {
                data[i][j] = string;
                j++;
            }
            i++;
        }

        return rotateMatrix(data);
    }

    public static int calculateMaxListSize(Map<String, List<String>> dat) {
        int maxLength = 0;

        for (List<String> list : dat.values()) {
            if (list.size() > maxLength) {
                maxLength = list.size();
            }
        }

        return maxLength;
    }


    public static String[][] rotateMatrix(String[][] arr) {
        String[][] res = new String[arr[0].length][arr.length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                res[j][i] = arr[i][j];
            }
        }

        return res;
    }

    public static String getHeader() {
        String header = "";
        for (String string : HEADER) {
            header += String.format("%s%s", string, DELIM);
        }
        return header;
    }

    public static List<Map<String, List<String>>> getContactList(ContactFeed resultFeed) {
        List<Map<String, List<String>>> contacts = new ArrayList<>();
        for (ContactEntry entry : resultFeed.getEntries()) {
            Map<String, List<String>> contact = new LinkedHashMap<>();

            Name name = entry.getName();
            if (name != null) {
                putNameDataToMap(contact, getFullName(name), getNamePrefix(name), getGivenName(name),
                        getAdditionalName(name), getFamilyName(name), getNameSuffix(name));
            } else {
                putNameDataToMap(contact);
            }

            contact.put(HEADER[6], getEmails(entry));
            contact.put(HEADER[7], getImAddresses(entry));
            contact.put(HEADER[8], getGroups(entry));
            contact.put(HEADER[9], getExtendedProperties(entry));
            contact.put(HEADER[10], getPhotoLink(entry));
            contact.put(HEADER[11], getEtag(entry));

            contacts.add(contact);
        }
        return contacts;
    }


}
