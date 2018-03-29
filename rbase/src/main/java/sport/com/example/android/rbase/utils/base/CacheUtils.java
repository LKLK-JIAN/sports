package sport.com.example.android.rbase.utils.base;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by android on 2018/3/28.
 */

public class CacheUtils {

    public static String getCacheSize(Context context) {
        //计算缓存大小
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();
        File ExternalDir = context.getExternalCacheDir();

        fileSize += getFileSize(filesDir);
        fileSize += getFileSize(cacheDir);
        fileSize += getFileSize(ExternalDir);
        if (fileSize>0) {
            cacheSize = formatFileSize(fileSize);
        }

        return cacheSize;
    }
    /**
     * 获取目录文件大小
     */
    public static long getFileSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else {
                dirSize += getFileSize(file);
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long files) {
        java.text.DecimalFormat df = new java.text.DecimalFormat();
        String fileSizeString;
        if (files < 1024) {
            fileSizeString = df.format((double) files) + "B";
        } else if (files < 1048576) {
            fileSizeString = df.format((double) files / 1024) + "KB";
        } else if (files < 1073741824) {
            fileSizeString = df.format((double) files / 1024) + "MB";
        } else {
            fileSizeString = df.format((double) files / 1024) + "GB";
        }
        return fileSizeString;
    }
    /**
     * 清除目录下的文件
     */
    public static void cleanFilea(Context context){
        deleteFilesByDirectory(context.getFilesDir());
    }
    /**
     * 清除本应用内部缓存
     */
    public static void cleandInternalCache(Context context){
        deleteFilesByDirectory(context.getCacheDir());
    }
    /**
     * 清除应用外部缓存
     */
    public static void cleanExternalCache(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }


    /**
     * 删除方法，只会删除目录下的文件，如果传进来的directory是文件而不是目录的话，将不会被删除
     * */
    public static void deleteFilesByDirectory(File directory){
        if(directory!=null&&directory.exists()&&directory.isDirectory()){
            for(File file:directory.listFiles()){
                if(file.isDirectory()){
                    deleteFilesByDirectory(file);
                }
                file.delete();
            }
            directory.delete();
        }
    }
}
