package net.eugenpaul.adventofcode.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Some static helper functions to read content of a files.
 */
public final class FileReaderHelper {
    private FileReaderHelper() {

    }

    /**
     * Read the content of a resourceName in a string. New lines characters will be deleted.
     * 
     * @param resourceName - name of a resource
     * @return content of a file
     */
    public static String readStringFromFile(String resourceName) {
        ClassLoader classLoader = FileReaderHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());

        if (!file.canRead() || !file.isFile()) {
            return null;
        }

        StringBuilder responseData = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                responseData.append(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData.toString();
    }

    /**
     * Read the content of a resourceName in a List (one element pro line).
     * 
     * @param resourceName - name of a resource
     * @return content of a file as List
     */
    public static List<String> readListStringFromFile(String resourceName) {
        ClassLoader classLoader = FileReaderHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());

        if (!file.canRead() || !file.isFile()) {
            return Collections.emptyList();
        }

        List<String> responseData = new LinkedList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                responseData.add(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData;
    }
}
