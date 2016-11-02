package com.rgk.qiguan.gddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.houde.amapclusterlib.ClusterOverlay;
import com.houde.amapclusterlib.IClusterItem;
import com.houde.amapclusterlib.IconRes;
import com.rgk.qiguan.gddemo.R;
import com.rgk.qiguan.gddemo.utils.ClacZoomUtil;
import com.rgk.qiguan.gddemo.utils.CoordinateUtil;
import com.rgk.qiguan.gddemo.utils.FileUtil;
import com.rgk.qiguan.gddemo.utils.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements
        AMap.OnMarkerClickListener,
        AMap.OnMapLoadedListener,
        AMap.OnCameraChangeListener {


    Context mContext;
    MapView mapView;
    AMap aMap;
    private ClusterOverlay clusterOverlay;
    private LatLng latLng;
    private double latMax = 29;
    private double latMin = 29;
    private double lngMax = 112;
    private double lngMin = 112;
    private DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mContext = getApplicationContext();
        initMap(savedInstanceState);
        addMarkersToMap();
    }

    private void initMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        aMap.setOnMapLoadedListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnCameraChangeListener(this);
    }

    private final String TAG = "ClusterMarkerActivity";

    public void addMarkersToMap() {
        List<IClusterItem> clusterItems = new ArrayList<IClusterItem>();
        File[] fileList = FileUtil.getFileList();

        for (int i = 0; i < fileList.length; i++){
            if (fileList[i].isFile()){
                double lat = 0 ;
                double lng = 0 ;

                try {
                    lat = ImageInfo.getImgLatitude(fileList[i]);
                    lng = ImageInfo.getImgLongitude(fileList[i]);
                    if (lat > latMax){
                        latMax = lat;
                    }
                    if (lat < latMin){
                        latMin = lat;
                    }
                    if (lng > lngMax){
                        lngMax = lng;
                    }
                    if (lng < lngMin){
                        lngMin = lng;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (lat != 0 && lng != 0){
                    LatLng beforeLatLng = new LatLng(lat, lng);
                    latLng = CoordinateUtil.transformFromWGSToGCJ(beforeLatLng);
                    clusterItems.add(new ImgData(latLng,fileList[i]));
                }

            }

        }

            Log.e(TAG,"list" + clusterItems);
            clusterOverlay = new ClusterOverlay(mContext, aMap, dp2px(65), clusterItems, null);

    }

    public int dp2px(float dpValue){
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    static class ImgData implements IClusterItem {
        LatLng latLng;
        File imgStr;

        public ImgData(LatLng latLng, File imgStr) {
            this.latLng = latLng;
            this.imgStr = imgStr;
        }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public IconRes getIconRes() {
        return new IconRes(imgStr);
    }

    @Override
    public String toString() {
        return "ImgData{" +
                "latLng=" + latLng +
                ", imgStr='" + imgStr + '\'' +
                '}';
    }

}

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        clusterOverlay.onCameraChangeFinish(cameraPosition);
    }

    @Override
    public void onMapLoaded() {
        double latMid = (latMax + latMin)/2;
        double lngMid = (lngMax + lngMin)/2;
        LatLng beforeMidLatLng = new LatLng(latMid,lngMid);
        LatLng midLatLng = CoordinateUtil.transformFromWGSToGCJ(beforeMidLatLng);
        LatLng maxLatLng = new LatLng(latMax,lngMax);
        LatLng minLatLng = new LatLng(latMin,lngMin);
        float zoom = ClacZoomUtil.getZoom(maxLatLng, minLatLng,metrics);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(midLatLng,zoom));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
