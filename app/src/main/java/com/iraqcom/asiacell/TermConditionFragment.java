package com.iraqcom.asiacell;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TermConditionFragment extends Fragment {


    public TermConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_term_condition, container, false);

        WebView webView = (WebView)rootView. findViewById(R.id.webview);
        webView.loadUrl("http://iraqcom.com/asiacell-plus-terms-conditions/");
        webView.getSettings().setJavaScriptEnabled(true);





        return rootView;
    }

}
