/**
 * Copyright(c) 2014 DRAWNZER.ORG PROJECTS -> ANURAG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *                             
 *                             anuraxsharma1512@gmail.com
 *
 */

package drawnzer.anurag.kollosal;


import android.app.ActionBar;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import drawnzer.anurag.kollosal.adapters.DrawerMenuAdapter;
import drawnzer.anurag.kollosal.adapters.KollosalFragmentAdapter;
import drawnzer.anurag.kollosal.adapters.ThemeAdapter;
import drawnzer.anurag.kollosal.fragments.VideoFragment;


/**
 * 
 * @author ANURAG....
 *
 */


public class KollosalPlayer extends FragmentActivity{
	
	//view pager for fragments....
	private ViewPager pager;
	
	//pages slide tab strip for view pager....
	private PagerSlidingTabStrip pagerSlideTab;
	
	//current color used in theme....
	private int currentColor;
	
	//preferences of the app....
	private SharedPreferences prefs;
	
	//if true drawer is open....
	private boolean isDrawerOpen;
	
	//slide drawer menu....
	private DrawerLayout slidingDrawer;
	
	//action bar toggle....
	private ActionBarDrawerToggle toggle;
	
	//main list view in slide drawer menu....
	private ListView lsMenu;
	
	//theme listview in slide drawer menu
	private ListView lsTheme;
	
	
	
	//action bar of main activity....
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("APP_SETTINGS", 0);
		currentColor = prefs.getInt("APP_COLOR",0xFFC74B46);
						
		setContentView(R.layout.combined_ui);	
		isDrawerOpen = false;
		slidingDrawer = (DrawerLayout)findViewById(R.id.slideDrawer);
		lsMenu = (ListView)findViewById(R.id.list_slidermenu);
		lsTheme = (ListView)findViewById(R.id.list_slidermenu_theme);
		lsTheme.setSelector(R.drawable.button_click);
		lsMenu.setSelector(R.drawable.button_click);
		
		lsMenu.setAdapter(new DrawerMenuAdapter(KollosalPlayer.this));
		lsTheme.setAdapter(new ThemeAdapter(KollosalPlayer.this));
		
		actionBar = getActionBar();
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);	
		
		actionBar.setIcon(R.drawable.red_kollosal);
		
		toggle = new ActionBarDrawerToggle(KollosalPlayer.this, slidingDrawer,
				R.drawable.red_kollosal, R.string.settings, R.string.app_name){
			public void onDrawerClosed(View view) {
                getActionBar().setTitle(getString(R.string.app_name));
                isDrawerOpen = false;
                if(lsTheme.getVisibility() == View.VISIBLE)
    				lsTheme.setVisibility(View.GONE);
            } 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getString(R.string.settings));
                isDrawerOpen = true;
            }
		};
		
		lsMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				// TODO Auto-generated method stub
				if(position == 5){//handling click on theme option...
					if(lsTheme.getVisibility() == View.GONE)
						lsTheme.setVisibility(View.VISIBLE);
					else 
						lsTheme.setVisibility(View.GONE);
				}	
			}
		});
		
		lsTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				
				changeColor(getResources().getColor(Constant.COLORS[arg2]));
				
				//applying color change to system views....
				init_system_ui();							
				
				SharedPreferences.Editor edit = prefs.edit();
				edit.putInt("APP_COLOR", getResources().getColor(Constant.COLORS[arg2]));
				edit.putInt("SEMI_APP_COLOR", getResources().getColor(Constant.SEMI_COLORS[arg2]));
				edit.putInt("CONTROLLER_COLOR", getResources().getColor(Constant.CONTROLLER_COLORS[arg2]));
				edit.putInt("APP_ICON", arg2);
				edit.commit();
			}
		});
		
		slidingDrawer.setDrawerListener(toggle);
		
		pager = (ViewPager)findViewById(R.id.pager);
		pagerSlideTab = (PagerSlidingTabStrip)findViewById(R.id.tabs);
		
		pager.setAdapter(new KollosalFragmentAdapter(getSupportFragmentManager()));
		
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pagerSlideTab.setViewPager(pager);
		changeColor(currentColor);		
		
	}	
	
	
	@Override
	public void onResume(){
		super.onResume();
		init_system_ui();
	}
	
	private void init_system_ui() {
		// TODO Auto-generated method stub
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
			return;
		
		SystemBarTintManager tinter = new SystemBarTintManager(KollosalPlayer.this);
		SystemBarTintManager.SystemBarConfig conf = tinter.getConfig();
		boolean isNavBar = conf.hasNavigtionBar();
		if(isNavBar){
			tinter.setNavigationBarTintEnabled(true);
			tinter.setNavigationBarTintColor(currentColor);
		}
		tinter.setStatusBarTintEnabled(true);
		tinter.setStatusBarTintColor(currentColor);
		LinearLayout ls = (LinearLayout) findViewById(R.id.lists_layout);
		LinearLayout frame = (LinearLayout) findViewById(R.id.frame_container);
		frame.setPadding(0, getStatusBarHeight(), 0, isNavBar ? getNavigationBarHeight() : 0);
		ls.setPadding(0, getStatusBarHeight(), 0, isNavBar ? getNavigationBarHeight() : 0);
	}

	/**
	 * 
	 * @return height of status bar along with height of action bar....
	 */
	private int getStatusBarHeight(){
		int res = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if(resId > 0)
			res = getResources().getDimensionPixelSize(resId);
		TypedValue val = new TypedValue();
		getTheme().resolveAttribute(android.R.attr.actionBarSize, val, true); 
		int action_size = TypedValue.complexToDimensionPixelSize(val.data, getResources().getDisplayMetrics());
		res += action_size;
		return res;
	}
	
	/**
	 * 
	 * @return the height of navigation bar....
	 */
	private int getNavigationBarHeight(){
		int res = 0;
		int resId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
		if(resId > 0)
			res = getResources().getDimensionPixelSize(resId);
		return res;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void changeColor(int newColor) {
		//MusicFragment.getSlideLayout().setBackgroundColor(newColor);
		//pagerSlideTab.setIndicatorColor(getResources().getColor(R.color.semi_white));
		RelativeLayout ml = (RelativeLayout)findViewById(R.id.main_ui);
		ml.setBackgroundColor(newColor);
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.lists_layout);
		listLayout.setBackgroundColor(newColor);
		ColorDrawable color = new ColorDrawable(newColor);
		lsMenu.setDivider(color);
		lsTheme.setDivider(color);
		Drawable colorDrawable = new ColorDrawable(newColor);
		actionBar.setBackgroundDrawable(colorDrawable);
		// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		currentColor = newColor;
	}	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(isDrawerOpen){
			slidingDrawer.closeDrawers();
			if(lsTheme.getVisibility() == View.VISIBLE)
				lsTheme.setVisibility(View.GONE);
		}else if(VideoFragment.isSliderOpened() && pager.getCurrentItem() == 0){
			VideoFragment.notifyPanelClose();
		}else if(VideoFragment.isSliderOpened() && pager.getCurrentItem() != 0){
			pager.setCurrentItem(0);
		}else if(VideoFragment.isFolderExpanded() && pager.getCurrentItem() == 0){
			//collapsing the opened folder....
			VideoFragment.collapseFolder();
		}
		else{
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}		
}
