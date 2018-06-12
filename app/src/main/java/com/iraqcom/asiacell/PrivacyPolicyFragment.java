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
public class PrivacyPolicyFragment extends Fragment {


    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        WebView webView_ccs = (WebView) rootView.findViewById(R.id.webview);
        webView_ccs.loadUrl("http://iraqcom.com/asiacell-plus-privacy-policy/");
        webView_ccs.getSettings().setJavaScriptEnabled(true);

        return rootView;
    }

}
