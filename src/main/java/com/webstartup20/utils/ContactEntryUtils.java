package com.webstartup20.utils;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Im;

import java.util.ArrayList;
import java.util.List;

public class ContactEntryUtils {

    public static List<String> getEtag(ContactEntry entry) {
        List<String> result = new ArrayList<String>();
        result.add(entry.getEtag());
        return result;
    }

    public static List<String> getPhotoLink(ContactEntry entry) {
        List<String> result = new ArrayList<>();
        result.add(entry.getContactPhotoLink().getHref());
        return result;
    }

    public static List<String> getExtendedProperties(ContactEntry entry) {
        List<String> result = new ArrayList<>();
        String buff = "";
        for (ExtendedProperty property : entry.getExtendedProperties()) {
            if (property.getValue() != null) {
                buff = String.format("%s = %s", property.getName(), property.getValue());
            } else if (property.getXmlBlob() != null) {
                buff = String.format("%s = %s (xmlBlob)", property.getName(), property.getXmlBlob().getBlob());
            }
            result.add(buff);
        }
        return result;
    }

    public static List<String> getGroups(ContactEntry entry) {
        List<String> result = new ArrayList<>();
        for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
            result.add(group.getHref());
        }

        return result;
    }

    public static List<String> getImAddresses(ContactEntry entry) {
        List<String> result = new ArrayList<>();
        String buff = "";
        for (Im im : entry.getImAddresses()) {

            buff = im.getAddress();

            if (im.getPrimary()) {
                buff += String.format(" (%s)", "primary");
            }
            result.add(buff);
        }

        return result;
    }

    public static List<String> getEmails(ContactEntry entry) {
        List<String> result = new ArrayList<String>();
        for (Email email : entry.getEmailAddresses()) {
            result.add(email.getAddress());
        }
        return result;
    }

}
