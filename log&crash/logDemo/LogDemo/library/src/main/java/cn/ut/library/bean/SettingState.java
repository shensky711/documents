package cn.ut.library.bean;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by chenhang on 2016/6/3.
 */
public class SettingState {

    private final boolean mWifiConnected;
    private final boolean mMobileConnected;
    private final String mLanguage;

    public static SettingState capture(Context context) {
        return new SettingState(context);
    }

    private SettingState(Context context) {
        mWifiConnected = isWifiConnected(context);
        mMobileConnected = isMobileConnected(context);
        mLanguage = context.getResources().getConfiguration().locale.getLanguage();
    }

    public boolean isWifiConnected() {
        return mWifiConnected;
    }

    public boolean isMobileConnected() {
        return mMobileConnected;
    }

    public String getLanguage() {
        return mLanguage;
    }

    @Override
    public String toString() {
        return "SettingState{" +
                "mWifiConnected=" + mWifiConnected +
                ", mMobileConnected=" + mMobileConnected +
                ", mLanguage='" + mLanguage + '\'' +
                '}';
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }
}
