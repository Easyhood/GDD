package com.rgk.qiguan.gddemo.utils;

import android.os.Environment;

import java.io.File;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/11/1 15:00
 */

public class FileUtil {

    public static File[] getFileList(){
        String path = "/Pictures/";
        File file = new File(Environment.getExternalStorageDirectory().getPath(), path);
        File[] tempList = file.listFiles();
        return  tempList;
    }
}
