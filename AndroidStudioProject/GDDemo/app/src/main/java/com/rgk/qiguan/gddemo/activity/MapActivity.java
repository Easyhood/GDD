package com.rgk.qiguan.gddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
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
import com.rgk.qiguan.gddemo.utils.CoordinateUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        String filePath = "/Pictures/img2414.JPG";
        File file = new File(Environment.getExternalStorageDirectory().getPath(),filePath);
        double lat = 0 ;
        double lng = 0 ;
            try {
                   lat = ImageInfo.getImgLatitude(file);
                   lng = ImageInfo.getImgLongitude(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        LatLng beforeLatLng = new LatLng(lat, lng);
        latLng = CoordinateUtil.transformFromWGSToGCJ(beforeLatLng);
        clusterItems.add(new ImgData(latLng,file/*Images.imageUrls[i]*/));
            Log.e(TAG,"list" + clusterItems);
            clusterOverlay = new ClusterOverlay(mContext, aMap, dp2px(80), clusterItems, null);

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
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
