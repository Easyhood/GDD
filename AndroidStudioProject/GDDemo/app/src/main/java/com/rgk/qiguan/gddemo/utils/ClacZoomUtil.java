package com.rgk.qiguan.gddemo.utils;

import android.util.DisplayMetrics;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

/**
 * Description:根据两点坐标确定缩放级别
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/11/1 17:17
 */

public class ClacZoomUtil {
    public static float getZoom(LatLng maxLatLng,LatLng minLatlng,DisplayMetrics metrics){
        float zoom = 0;
        int widthPixels = metrics.widthPixels;
        float widPix = (float) widthPixels;
        float distance = AMapUtils.calculateLineDistance(maxLatLng,minLatlng);
        //20, 50, 100, 200, 500, 1000, 2000,  5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000,  2000000
        if (distance < 20 * widPix){
            zoom = 11;
            return zoom;
        } else if (distance < 50 * widPix){
            zoom = 10;
            return  zoom;
        } else if (distance < 100 * widPix){
            zoom = 9;
            return  zoom;
        }else if (distance < 200 * widPix){
            zoom = 8;
            return  zoom;
        }else if (distance < 500 * widPix){
            zoom = 7;
            return  zoom;
        }else if (distance < 1000 * widPix){
            zoom = 6;
            return  zoom;
        }else if (distance < 2000 * widPix){
            zoom = 5;
            return  zoom;
        }else if (distance < 5000 * widPix){
            zoom = 4;
            return  zoom;
        }else if (distance < 10000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 20000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 25000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 50000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 100000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 200000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 500000 * widPix){
            zoom = 3;
            return  zoom;
        }else if (distance < 1000000 * widPix){
            zoom = 3;
            return  zoom;
        }
            zoom = 3;
            return  zoom;


    }
}
