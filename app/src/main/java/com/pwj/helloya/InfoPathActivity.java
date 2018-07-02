package com.pwj.helloya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class InfoPathActivity extends AppCompatActivity {

    TextView textView;
    BaiduMap baiduMapPath;
    MapView pathMapView;
    String showInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_path);

        textView = (TextView)findViewById(R.id.info_path_textView);
        pathMapView = (MapView) findViewById(R.id.path_bmapView);
        baiduMapPath = pathMapView.getMap();

        Customer customer = (Customer) getIntent().getSerializableExtra("customer_info");
        showInfo = "公司名称： " + customer.getCompanyname() + "\n" +
                "联系方式： " + customer.getLiaisons() + "\n" +
                "网址： " + customer.getWww() + "\n" +
                "地址： " + customer.getAddress() + "\n" +
                "公司介绍： " + customer.getDescription() + "\n" +
                "主要使用抛丸机产品为： " + customer.getMain_products_using_pwj() + "\n" +
                "有关销售人员： " + customer.getRelated_sailer() + "\n" +
                "已有抛丸机销售： " + customer.getUsed_pwjs() + "\n";

        textView.setText(showInfo);
    }
}
