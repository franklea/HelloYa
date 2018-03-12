package com.pwj.helloya;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by leon on 3/7/18.
 */

public class FragmentNews extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE_SETTING";
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
        // mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl("https://www.baidu.com");
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }
}
