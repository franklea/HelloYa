package com.pwj.helloya;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class RequestActivity extends AppCompatActivity {
    private MapView mMapView = null;
    private String numberOfCustomer = "???";
    private TextView textViewCustomer;
    public LocationClient mLocationClient = null;
    private DataBaseHelper dataBaseHelper;
    Cursor querryResults;
    private double longitude;
    private double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        final TextView textViewSeekBar = (TextView) findViewById(R.id.textView2);
        textViewSeekBar.setText("距您20公里之内，共有" + numberOfCustomer.toString()+"个潜在客户");

        textViewCustomer = findViewById(R.id.textView_customer);
        textViewCustomer.setText("test,test,test");

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBar.setText("距您" + String.valueOf(seekBar.getProgress()) + "公里之内， 共有"+ numberOfCustomer + "个潜在客户");
                // TODO show the number of customers.
                textViewCustomer.setText("查找" + String.valueOf(seekBar.getProgress()) + "公里范围内的客户\n");

                querryResults = querryDB(seekBar.getProgress());

                while (!querryResults.isAfterLast()){
                    textViewCustomer.append(querryResults.getString(6)+ " ");
                    textViewCustomer.append(querryResults.getString(7) + "\n");
                    querryResults.moveToNext();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mLocationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(true);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                longitude = bdLocation.getLongitude();
                latitude = bdLocation.getLatitude();
                Log.i("++++Jun++++++", "Longitude: " + longitude + " Latitude: " + latitude + " ----");
            }
        });




        mMapView = (MapView) findViewById(R.id.bmapView);
        BaiduMap baiduMap = mMapView.getMap();



    }

    public Cursor querryDB(int distance){
        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase;

        try{
            sqLiteDatabase = dataBaseHelper.openDatabase();
            String sql = "select * from pwj_user where round";
            querryResults = sqLiteDatabase.rawQuery(sql, null);
            querryResults.moveToFirst();
        } catch (SQLException e){
            throw e;
        }
        return querryResults;
    }

    public void addMarker(BaiduMap map, double x, double y){
        LatLng point = new LatLng(x, y);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.pos_blue);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmapDescriptor).draggable(false).perspective(true);
        map.addOverlay(option);
    }

    @Override
    public void onResume(){
        super.onResume();
        mLocationClient.restart();
        mMapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
    }



}
