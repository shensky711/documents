package cn.ut.library.utils;

import android.support.annotation.Nullable;

/**
 * Created by chenhang on 2016/6/14.
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return (str == null || str.length() == 0);
    }

}
