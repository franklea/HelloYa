package com.pwj.helloya;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by leon on 3/7/18.
 */

public class FragmentLocation extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE_LOCATION";
    private int mPage;
    private MapView mMapView = null;

    public static FragmentHome newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentHome pageFragment = new FragmentHome();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        final TextView textViewLocation = (TextView) view.findViewById(R.id.textView_location);
        textViewLocation.setText("location");

        final TextView textViewSeekBar = (TextView) view.findViewById(R.id.textView2);
        textViewSeekBar.setText(getResources().getString(R.string.distance) + ": 20 km");

        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBar.setText(getResources().getString(R.string.distance) + ": " + String.valueOf(seekBar.getProgress()) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMapView = (MapView) view.findViewById(R.id.bmapView);
        // 开启定位图层
        final BaiduMap mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory.fromResource(R.mipmap.pos)));

        Button buttonFind = (Button) view.findViewById(R.id.find_location);
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLocation.setText("为您查找 " + String.valueOf(seekBar.getProgress()) + " km 范围内的客户");
                addMarker(mBaiduMap, 39.963175, 116.400244);
            }
        });

        return view;
    }


    public void addMarker(BaiduMap map, double x, double y) {
        LatLng point = new LatLng(x, y);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.pos_blue);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).draggable(false).perspective(true);
        map.addOverlay(option);
    }


    @Override
    public void onResume(){
        super.onResume();
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
