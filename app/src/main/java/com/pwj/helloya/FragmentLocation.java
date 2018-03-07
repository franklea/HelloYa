package com.pwj.helloya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by leon on 3/7/18.
 */

public class FragmentLocation extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE_LOCATION";
    private int mPage;

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

        Button buttonFind = (Button) view.findViewById(R.id.find_location);
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewLocation.setText("为您查找 " + String.valueOf(seekBar.getProgress()) + " km 范围内的客户");
            }
        });

        return view;
    }

}
