package com.example.s2demo1.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

import com.example.s2demo1.R;
import com.example.s2demo1.fragments.CountriesFlagFragment;
import com.example.s2demo1.fragments.CountriesListFragment;

public class MainActivity extends ActionBarActivity implements TabListener
{
	Fragment[] fragments = new Fragment[] {new CountriesListFragment(), new CountriesFlagFragment()};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionbar = getSupportActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionbar.addTab(actionbar.newTab().setText(getString(R.string.title_fragment_list)).setTabListener(this));
		actionbar.addTab(actionbar.newTab().setText(getString(R.string.title_fragment_flags)).setTabListener(this));
		
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().add(R.id.mainContent, fragments[0]).add(R.id.mainContent, fragments[1]).commit();
		
		
		
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment toHide = null;
		Fragment toShow = null;
		switch(tab.getPosition())
		{
			case 0:
				toHide = fragments[1];
				toShow = fragments[0];
				break;
			case 1:
				toHide = fragments[0];
				toShow = fragments[1];
				break;
		}
		
		ft.hide(toHide).show(toShow);
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
