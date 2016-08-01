package cn.ut.library.utils;

import java.io.File;

/**
 * Created by chenhang on 2016/6/16.
 */
public class FileUtils {

    private FileUtils() {
    }


    /**
     * 创建文件的目录
     *
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    public static String getFolderName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int position = filePath.lastIndexOf(File.separator);
        return (position == -1) ? "" : filePath.substring(0, position);
    }

}
