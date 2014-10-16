package com.webstartup20.utils;

import com.google.gdata.data.extensions.Name;

import java.util.ArrayList;
import java.util.List;

public class NameUtils {

    public static List<String> getFullName(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasFullName()) {
            String fullNameToDisplay = name.getFullName().getValue();
            if (name.getFullName().hasYomi()) {
                fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
            }
            result.add(fullNameToDisplay);
        }
        return result;
    }

    public static List<String> getNamePrefix(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasNamePrefix()) {
            result.add(name.getNamePrefix().getValue());
        }
        return result;
    }

    public static List<String> getGivenName(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasGivenName()) {
            String givenNameToDisplay = name.getGivenName().getValue();
            if (name.getGivenName().hasYomi()) {
                givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
            }
            result.add(givenNameToDisplay);
        }
        return result;
    }

    public static List<String> getAdditionalName(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasAdditionalName()) {
            String additionalNameToDisplay = name.getAdditionalName().getValue();
            if (name.getAdditionalName().hasYomi()) {
                additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
            }
            result.add(additionalNameToDisplay);
        }
        return result;
    }

    public static List<String> getFamilyName(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasFamilyName()) {
            String familyNameToDisplay = name.getFamilyName().getValue();
            if (name.getFamilyName().hasYomi()) {
                familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
            }
            result.add(familyNameToDisplay);
        }
        return result;
    }

    public static List<String> getNameSuffix(Name name){
        List<String> result = new ArrayList<>();
        if (name.hasNameSuffix()) {
            result.add(name.getNameSuffix().getValue());
        }
        return result;
    }
}
