package com.webstartup20.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtils {

    public static final String PATTERN = "yyyy_MM_dd_hh_mm_ss";

    public static void deleteFile(String fileName) {
        try {

            File file = new File(fileName);

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void writeToFile(List<String> data, String filePath) {
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF8"))) {

            for (String elem : data) {
                out.write(elem + "\n");
            }
        } catch (IOException ex) {
            System.err.println("Error while writing data to file");
            ex.printStackTrace();
        }
    }

    public static String getFileNameTimeStamp() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        return simpleDateFormat.format(date);
    }
}

