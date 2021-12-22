package net.adventofcode.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Some static helper functions to read content of a files.
 */
public class FileReaderHelper {
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
}
