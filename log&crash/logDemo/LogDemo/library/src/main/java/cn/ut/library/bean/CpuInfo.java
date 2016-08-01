package cn.ut.library.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chenhang on 2016/6/20.
 */
public class CpuInfo {

    private String mProcessor;

    public static CpuInfo getCpuInfo() {
        String cmd = "/proc/cpuinfo";

        String processor = "";
        try {
            String temp;
            String[] arrayOfString;
            FileReader fr = new FileReader(cmd);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            temp = localBufferedReader.readLine();
            arrayOfString = temp.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                processor = processor + arrayOfString[i] + " ";
            }
            localBufferedReader.close();
        } catch (IOException ignored) {
        }

        return new CpuInfo(processor);
    }

    public CpuInfo(String mProcessor) {
        this.mProcessor = mProcessor;
    }

    public String getProcessor() {
        return mProcessor;
    }

    @Override
    public String toString() {
        return "CpuInfo{" +
                "mProcessor='" + mProcessor + '\'' +
                '}';
    }
}
