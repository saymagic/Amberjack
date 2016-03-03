package com.dockerandroid.data.file;

import android.os.Environment;

import com.dockerandroid.exception.ADException;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by hzcaoyanming on 2016/2/17.
 */
public class FileManager {

    private static final String BASE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + File.separator + "dockerandroid";

    private static final String CRASH_PATH = "crash";

    private static final String CRASH_LOG_NAME = "crash.log";

    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    private static synchronized String ensurePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                Logger.w("create path " + path + "error");
            }
        }
        return path;
    }

    public static String getCrashPath() throws ADException {
        if (hasSdcard()) {
            return ensurePath(BASE_PATH + File.separator + CRASH_PATH);
        }
        throw ADException.createException(ADException.NO_SDCARD);
    }
}
