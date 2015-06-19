package com.pointrestapp.pointrest.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pointrestapp.pointrest.Constants;
import com.pointrestapp.pointrest.R;
import com.pointrestapp.pointrest.adapters.TabAdapter;

public class FragmentTitleScreen extends Fragment
	implements TabAdapter.TabSelectedListener {

	
	private static final String CATEGORY_ID = "category_id";
	private ViewPager mViewPager;
	private TabAdapter mTabsAdapter;
	private int mCategoryId = Constants.TabType.TUTTO;
	
	public static FragmentTitleScreen getInstance() {
		return new FragmentTitleScreen();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vView = inflater.inflate(R.layout.fragment_title_screen, container, false);
        mViewPager = (ViewPager) vView.findViewById(R.id.pager);
        Activity vActivity = getActivity();
        ActionBar bar = vActivity.getActionBar();
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        if (savedInstanceState != null) {
        	mCategoryId = savedInstanceState.getInt(CATEGORY_ID);
        }
        
        mTabsAdapter = new TabAdapter(vActivity, getFragmentManager());
        mViewPager.setAdapter(mTabsAdapter);
        mViewPager.setOnPageChangeListener(mTabsAdapter);
		return vView;
	}
	
	@Override
	public void onResume() {
        int tab = mTabsAdapter.getTabPositionFromCategoryId(mCategoryId);
        mViewPager.setCurrentItem(tab, true);
		super.onResume();
	}

	public void OnBackPressed() {
		onTabSelected(mCategoryId);
	}

	@Override
	public void onTabSelected(int categoryId) {
		mCategoryId = categoryId;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(CATEGORY_ID, mCategoryId);
		super.onSaveInstanceState(outState);
	}
}
