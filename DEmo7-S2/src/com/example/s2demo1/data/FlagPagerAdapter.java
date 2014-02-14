package com.example.s2demo1.data;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.s2demo1.R;
import com.example.s2demo1.fragments.FlagFragment;

public class FlagPagerAdapter extends FragmentPagerAdapter {
	private int[] arrayFlags = new int[]
		{
			R.drawable.brasil,
			R.drawable.mexico,
			R.drawable.colombia,
			R.drawable.argentina,
			R.drawable.peru,
			R.drawable.venezuela,
			R.drawable.chile,
			R.drawable.ecuador,
			R.drawable.guatemala,
			R.drawable.cuba
		};
	public FlagPagerAdapter(FragmentManager fm) {
		super(fm);
		 
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment fragment = new FlagFragment();
		Bundle args = new Bundle();
		args.putInt(FlagFragment.RESOURCE, arrayFlags[position]);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return arrayFlags.length;
	}

}
