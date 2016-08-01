package cn.ut.library.bean;

import android.os.Build;

/**
 * Created by chenhang on 2016/6/3.
 */
public class DeviceInfo {

    public DeviceInfo() {

    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("DeviceInfo:  ");
        builder.append("Mode:").append(Build.MODEL).append("; ");
        builder.append("Device:").append(Build.DEVICE).append("; ");
        builder.append("Product:").append(Build.PRODUCT).append("; ");
        builder.append("Manufacturer:").append(Build.MANUFACTURER).append("; ");
        builder.append("Release:").append(Build.VERSION.RELEASE).append("; ");
        builder.append("SDK:").append(Build.VERSION.SDK_INT);
        return builder.toString();
    }


}
