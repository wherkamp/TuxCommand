package me.kingtux.tuxcommand.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TuxUtils {
    public static String[] removeFirst(String[] t) {
        List<String> s = new ArrayList<>(Arrays.asList(t));
        s.remove(0);
        return s.toArray(t);
    }

    public static String[] noNulls(String[] array) {
        return Arrays.stream(array)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
    }

    public static <T> boolean contains(T[] array, T value) {
        return new ArrayList<>(Arrays.asList(array)).contains(value);
    }

    public static Object[] arrayOf(Object... o) {
        return o;
    }

    public static boolean containsIgnoreCase(String[] alias, String string) {
        for (String s : alias) {
            if (s.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }
    public static <T> List<T> toList(T[] t){
        List<T> list = new ArrayList<>();
        Collections.addAll(list, t);
        return list;
    }
    public static void download(String s, File file) throws IOException {
        URL website = new URL(s);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
