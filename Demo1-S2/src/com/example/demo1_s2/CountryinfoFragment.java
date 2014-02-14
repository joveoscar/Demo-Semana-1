package com.example.demo1_s2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CountryinfoFragment extends Fragment {
	private WebView webView;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		String country = ((CountryDetailActivity)getActivity()).getCountry();
		webView.loadUrl("http://es.m.wikipedia.org/wiki/" + country);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url) {
				view.loadUrl(url);
				return false;
			}
		});
		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_country_info,container,false);
		webView = (WebView) view.findViewById(R.id.webView);
		return view;
	}

	
	
}
