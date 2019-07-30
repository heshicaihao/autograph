package com.heshicaihao.autograph.constants;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 项目名称：WMS-master
 * 类描述:
 * 创建人: heshicaihao
 * 创建时间: 2017/11/29 9:45
 */
public class FileConstants {

    //一级目录 文件夹名字
    public static final String APP_DIR = "Heshicaihao";
    //二级目录 项目 文件夹名字
    public static final String PROJECT_DIR = "StaffRecovery";

    //三级目录 捕获异常记录 文件夹名字
    public static final String EXCEPTION_DIR = "exception";
    //三级目录 配置 文件夹名字
    public static final String CONFIGURE_DIR = "configure";
    //分割斜线
    public static final String Slash = "/";
    // 后缀txt
    public static final String TXT = ".txt";
    // 后缀png
    public static final String PNG = ".png";
    // 后缀jpeg
    public static final String JPEG = ".jpeg";
    // 后缀json
    public static final String JSON = ".json";
    // 后缀zip
    public static final String ZIP = ".zip";

    //捕获异常记录 文件路径
    public static final String EXCEPTION_PATH = getThirdPath(EXCEPTION_DIR);
    //配置 文件架路径
    public static final String CONFIGURE_PATH = getThirdPath(CONFIGURE_DIR);

    // 配置文件名
    public static final String CONFIGURE_PASSWORD = "9527";
    // 配置文件名
    public static final String CONFIGURE_NAME = "configure";

    /**
     * 从sd卡读文件
     *
     * @param path 文件路径
     * @return
     */
    public static String readFile(String path) {
        FileInputStream inputStream = null;
        String Str = null;
        try {
            if (isSdCardExist()) {
                File fileDir = new File(path);
                inputStream = new FileInputStream(fileDir);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                Str = new String(b);
                return Str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return Str;
    }

    /**
     * @param folder_path 文件夹路径
     * @param file_name   文件名
     * @param file_er     文件后缀
     * @return
     */
    public static String getFilePath(String folder_path, String file_name, String file_er) {
        String picName = file_name + file_er;
        String path = folder_path + picName;
        return path;
    }

    /**
     * 获取三级目录 路径
     *
     * @return
     */
    public static String getThirdPath(String thirdFolderName) {

        String path = getSDPath() + Slash
                + APP_DIR + Slash
                + PROJECT_DIR + Slash
                + thirdFolderName + Slash;
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        return path;
    }

    /**
     * 获取手机SD卡路径的方法
     */
    public static String getSDPath() {
        File sdDir = null;
        if (isSdCardExist()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 判断SDCard是否存在
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
