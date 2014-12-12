package fr.coincoin.test;

import java.io.InputStream;
import java.util.Scanner;

public class FileUtils {
    public static String toString(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
