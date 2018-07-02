package com.pwj.helloya;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leon on 3/7/18.
 */

public class FragmentHome extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE_HOME";

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
        // mPage = ARG_PAGE;
    }

    private ListView singleListView;
    private ListView multiListView;
    private List<Item> singleItemList;
    private List<Item> multiItemList;
    private Item selectedItem;
    private int selectedPosition = -1;
    private Button request;
    private static HashMap<Integer, Boolean> multiSelected;
    private static String[] productType = {"钢","铁","钢铁","铝","轮船","锚链","矿山机械"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // init view

        request = (Button) view.findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition == -1){
                    Toast.makeText(getContext(), "请选择客户位置", Toast.LENGTH_LONG).show();
                    return;
                }

                int multisize = 0;
                String choice = "";
                for (int i = 0; i < 7; i ++ ){
                    if (multiSelected.get(i)){
                        choice = choice + productType[i]+ ",";
                        multisize = multisize + 1;
                    }
                }
                if (multisize == 0){
                    Toast.makeText(getContext(), "请至少选择一种客户类型", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v(LOGTAG, choice);
                if (selectedPosition == 0){
                    Intent intent = new Intent(getActivity(), RequestActivity.class);
                    intent.putExtra("choice", choice);
                    startActivity(intent);
                }else if (selectedPosition == 1){
                    Intent cityListIntent = new Intent(getActivity(), CityListActivity.class);
                    cityListIntent.putExtra("choice", choice);
                    startActivity(cityListIntent);
                }


            }
        });
        singleListView = (ListView) view.findViewById(R.id.singleListView);
        multiListView = (ListView) view.findViewById(R.id.multi_listView);



        //initData
        initData();

        return view;
    }

    public String LOGTAG="\n+++++++++++++++++++++++++Jun Debug +++++++++++++++++++++++++++\n";


    private void initData(){
        //singleItemList
        singleItemList = new ArrayList<Item>();
        Item item1 = new Item(R.mipmap.nearby, R.string.fujin);
        Item item2 = new Item(R.mipmap.city, R.string.chengshi);
        singleItemList.add(item1);
        singleItemList.add(item2);

        final MyAdapter myAdapter = new MyAdapter(getContext(), singleItemList);
        singleListView.setAdapter(myAdapter);
        singleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                selectedItem = singleItemList.get(position);
                Log.d(LOGTAG, position + " selected ! ");
                myAdapter.notifyDataSetChanged();
            }
        });


        //multiItemList
        multiItemList = new ArrayList<Item>();
        Item item3 = new Item(R.mipmap.gang, R.string.gang);
        Item item4 = new Item(R.mipmap.tie, R.string.tie);
        Item item5 = new Item(R.mipmap.gangtie, R.string.gangtie);
        Item item6 = new Item(R.mipmap.lv, R.string.lv);
        Item item7 = new Item(R.mipmap.lunchuan, R.string.lunchuan);
        Item item8 = new Item(R.mipmap.maolian, R.string.maolian);
        Item item9 = new Item(R.mipmap.kuagnshanjixie, R.string.kuangshanjixie);
        multiItemList.add(item3);
        multiItemList.add(item4);
        multiItemList.add(item5);
        multiItemList.add(item6);
        multiItemList.add(item7);
        multiItemList.add(item8);
        multiItemList.add(item9);

        final MyMultiAdapter myMultiAdapter = new MyMultiAdapter(getContext(), multiItemList);
        multiListView.setAdapter(myMultiAdapter);
       multiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.v(LOGTAG, position + " selected !");
               if (myMultiAdapter.getMultiSelected().get(position)){
                   myMultiAdapter.getMultiSelected().put(position,false);
               } else{
                   myMultiAdapter.getMultiSelected().put(position,true);
               }
               myMultiAdapter.notifyDataSetChanged();
           }
       });

    }

    public class MyMultiAdapter extends BaseAdapter{

        private Context context;
        private List<Item> multiItems;
        private LayoutInflater layoutInflater;

        public MyMultiAdapter(Context context, List<Item> multiItems) {
            this.context = context;
            this.multiItems = multiItems;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            multiSelected = new HashMap<Integer, Boolean>();
            initSelectedData();
        }

        private void initSelectedData(){
            for (int i = 0; i < multiItems.size(); i++){
                getMultiSelected().put(i, false);
            }
        }

        public HashMap<Integer, Boolean> getMultiSelected(){
            return multiSelected;
        }


        @Override
        public int getCount() {
            return multiItems.size();
        }

        @Override
        public Object getItem(int position) {
            return multiItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO, return id in sql request;
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MultiViewHolder multiViewHolder = null;

            if (convertView == null){
                multiViewHolder = new MultiViewHolder();
                convertView = layoutInflater.inflate(R.layout.multi_item, null);
                multiViewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.multi_checkbox);
                multiViewHolder.imageView = (ImageView) convertView.findViewById(R.id.multi_imageView);
                multiViewHolder.editText = (EditText) convertView.findViewById(R.id.multi_itemtext);
                convertView.setTag(multiViewHolder);
            } else {
                multiViewHolder = (MultiViewHolder) convertView.getTag();
            }

            multiViewHolder.imageView.setImageResource(multiItems.get(position).getImgId());
            multiViewHolder.editText.setText(multiItems.get(position).getTextId());
            multiViewHolder.checkBox.setChecked(getMultiSelected().get(position));

            return convertView;
        }
    }

    class MultiViewHolder{
        CheckBox checkBox;
        ImageView imageView;
        EditText editText;
    }


    public class MyAdapter extends BaseAdapter{
        private Context context;
        private List<Item> items;
        private LayoutInflater layoutInflater;

        MyAdapter(Context context, List<Item> items) {
            this.context = context;
            this.items = items;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount(){
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.single_item, null);
                viewHolder = new ViewHolder();
                viewHolder.select = (RadioButton) convertView.findViewById(R.id.single_radio);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.singleImage);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.singleText);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imageView.setImageResource(items.get(position).getImgId());
            viewHolder.textView.setText(items.get(position).getTextId());

            if (selectedPosition == position){
                viewHolder.select.setChecked(true);
            }else{
                viewHolder.select.setChecked(false);
            }
            return convertView;
        }

    }

    class ViewHolder{
        RadioButton select;
        ImageView imageView;
        TextView textView;
    }
}
