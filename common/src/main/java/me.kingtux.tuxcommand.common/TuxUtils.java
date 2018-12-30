package me.kingtux.tuxcommand.common;

import java.util.ArrayList;
import java.util.Arrays;
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
}
