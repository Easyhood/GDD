package com.rgk.qiguan.gddemo.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/10/31 11:04
 */

/**
 * 读取照片信息
 */
public class ImageInfo {
    private static double imgLatitude;
    private static double imgLongitude;
    private static String format;

    /**
     * 获取纬度
     */
    public static double getImgLatitude(File file) throws Exception {
        printImageGPSInfo(file);
        return imgLatitude;
    }

    /**
     * 获取经度
     */
    public static double getImgLongitude(File file)throws Exception{
        printImageGPSInfo(file);
        return imgLongitude;
    }

    /**
     *读取照片里面的信息
     */
    public static void printImageGPSInfo(File file) throws ImageProcessingException,Exception{
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()){
            for (Tag tag : directory.getTags()){
                String tagName = tag.getTagName();//标签名
                String desc = tag.getDescription();//标签信息
                if (tagName.equals("GPS Latitude")){
                    imgLatitude = StrToDou(desc);
                    System.err.print("纬度："+ imgLatitude);
                }else if (tagName.equals("GPS Longitude")){
                    imgLongitude = StrToDou(desc);
                    System.err.print("经度："+ imgLongitude);
                }
            }
        }
    }

    /**
     * 将经纬度度分秒格式String转换为度double格式
     */
    private static double StrToDou(String desc){
        Double du = Double.parseDouble(desc.substring(0,desc.indexOf("°")).trim());
        Double fen = Double.parseDouble(desc.substring(desc.indexOf("°")+1,desc.indexOf("'")).trim());
        Double miao = Double.parseDouble(desc.substring(desc.indexOf("'")+1,desc.indexOf("\"")).trim());
        Double duDou = du + fen / 60 + miao / 60 / 60 ;
        DecimalFormat df = new DecimalFormat("#####0.000");
        format = df.format(duDou);
        return Double.valueOf(format);
    }

}
