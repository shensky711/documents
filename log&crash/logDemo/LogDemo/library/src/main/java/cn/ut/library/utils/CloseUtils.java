package cn.ut.library.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by chenhang on 2016/6/1.
 */
public class CloseUtils {

    private CloseUtils() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
