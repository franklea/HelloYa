package com.pwj.helloya;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class CityListActivity extends AppCompatActivity {

    private ArrayList<String> city_names;
    private DataBaseHelper dataBaseHelper;
    ArrayList<ProvinceCityCustomer> provinceCityCustomerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        provinceCityCustomerList = new ArrayList<>();

        Intent intent = getIntent();
        String choice = intent.getStringExtra("choice");
        String[] choices = choice.split(",");

        //get all city names in db
        city_names = new ArrayList<String>();
        String sql_city = "select distinct province from pwj_user;";
        Cursor city_result = querryDB(sql_city);
        while (!city_result.isAfterLast()) {


            city_names.add(city_result.getString(0));
            city_result.moveToNext();
        }

        //get the cunstomer number of each province

        String main_products_using_pwj = "";
        for (int i = 0; i < choices.length; i++){
            if (i == 0){
                main_products_using_pwj += " and main_products_using_pwj=\"" + choices[i] + "\"";
            }else{
                main_products_using_pwj += " or main_products_using_pwj=\"" + choices[i] + "\"";
            }

        }

        for (int i =0; i < city_names.size(); i++){
            String city = city_names.get(i);

            ProvinceCityCustomer provinceCityCustomer = new ProvinceCityCustomer();
            provinceCityCustomer.setProvince(city);

            String sql = "select * from pwj_user where province=\"" + city + "\" " +
                    main_products_using_pwj +";";

            Cursor tmpResult = querryDB(sql);

            ArrayList<Customer> tmpList = new ArrayList<>();
            while (!tmpResult.isAfterLast()) {

                Customer customer = new Customer(tmpResult.getInt(0), tmpResult.getString(1), tmpResult.getString(2),
                        tmpResult.getString(3), tmpResult.getString(4), tmpResult.getString(5), tmpResult.getDouble(6),
                        tmpResult.getDouble(7), tmpResult.getString(8), tmpResult.getString(9), tmpResult.getString(10),
                        tmpResult.getString(11), tmpResult.getString(12), tmpResult.getString(13), tmpResult.getString(14),
                        tmpResult.getString(15), tmpResult.getString(16), tmpResult.getString(17));

                tmpList.add(customer);
                tmpResult.moveToNext();
            }
            provinceCityCustomer.setListCustomers(tmpList);



            String sql_count = "select count(*) from pwj_user where province=\"" + city + "\" " +
                    main_products_using_pwj +";";

            Cursor tmp_count_result = querryDB(sql_count);
            provinceCityCustomer.setCount(tmp_count_result.getInt(0));

            provinceCityCustomerList.add(provinceCityCustomer);

        }

        final ListView listView = (ListView) findViewById(R.id.customer_city_listview);
        final CityListActivity.MyCityAdapter myCityAdapter = new MyCityAdapter(this, provinceCityCustomerList);
        listView.setAdapter(myCityAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityListActivity.this, ResultDetailList.class);

                intent.putExtra("resultlist", (Serializable) provinceCityCustomerList.get(position).listCustomers);
                startActivity(intent);
            }
        });
        /*
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResultDetailList.this, InfoPathActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer_info", resultCustomerList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        */


    }

    public class MyCityAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<ProvinceCityCustomer> provinceCityCustomerArrayList;
        private LayoutInflater layoutInflater;

        MyCityAdapter(Context context, ArrayList<ProvinceCityCustomer> provinceCityCustomerArrayList) {
            this.context = context;
            this.provinceCityCustomerArrayList = provinceCityCustomerArrayList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public int getCount(){
            return provinceCityCustomerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return provinceCityCustomerArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            CityListActivity.ResultViewHolder resultViewHolder = null;

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.result_company_name, null);
                resultViewHolder= new CityListActivity.ResultViewHolder();
                resultViewHolder.textView = (TextView) convertView.findViewById(R.id.detail_result_textview);
                resultViewHolder.imageView = (ImageView) convertView.findViewById(R.id.detail_result_image);

                convertView.setTag(resultViewHolder);
            }else {
                resultViewHolder = (CityListActivity.ResultViewHolder) convertView.getTag();
            }

            Log.v("++++debug+++", "debug");
            resultViewHolder.imageView.setImageResource(R.mipmap.more);
            String txtShow = provinceCityCustomerArrayList.get(position).getProvince() + "\n"+ "客户数量："+ provinceCityCustomerArrayList.get(position).getCount() + "个";
            resultViewHolder.textView.setText(txtShow);
            return convertView;
        }

    }

    class ResultViewHolder{
        TextView textView;
        ImageView imageView;
    }


    public Cursor querryDB(String sql){
        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase;
        Cursor querryResults;

        try{
            sqLiteDatabase = dataBaseHelper.openDatabase();
            querryResults = sqLiteDatabase.rawQuery(sql, null);
            querryResults.moveToFirst();

        } catch (SQLException e){
            throw e;
        }
        return querryResults;
    }
}
