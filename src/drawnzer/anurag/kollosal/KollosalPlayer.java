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
 *                             anurag.dev1512@gmail.com
 *
 */

package drawnzer.anurag.kollosal;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;

/**
 * 
 * @author ANURAG....
 *
 */
public class KollosalPlayer extends FragmentActivity{
	
	private final Handler handler = new Handler();
	Drawable oldBackground;
	ViewPager pager;
	PagerSlidingTabStrip pagerSlideTab;
	private int currentColor = 0xFFC74B46;
	
	private boolean isDrawerOpen; 
	private DrawerLayout slidingDrawer;
	private ActionBarDrawerToggle toggle;
	private ListView lsMenu;
	private ListView lsTheme;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.combined_ui);	
		isDrawerOpen = false;
		slidingDrawer = (DrawerLayout)findViewById(R.id.slideDrawer);
		lsMenu = (ListView)findViewById(R.id.list_slidermenu);
		lsTheme = (ListView)findViewById(R.id.list_slidermenu_theme);
		lsTheme.setSelector(R.drawable.button_click);
		lsMenu.setSelector(R.drawable.button_click);
		
		lsMenu.setAdapter(new ArrayAdapter<String>(KollosalPlayer.this, android.R.layout.simple_list_item_1,
				getResources().getStringArray(R.array.lsMenu)));
		lsTheme.setAdapter(new ThemeAdapter(KollosalPlayer.this));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);	
		
		toggle = new ActionBarDrawerToggle(KollosalPlayer.this, slidingDrawer,
				R.drawable.ic_launcher_icon, R.string.settings, R.string.app_name){
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
				int[] colors={
						R.color.grey,R.color.green,
						R.color.red,R.color.orange,
						R.color.blue,R.color.violet
					};
				changeColor(getResources().getColor(colors[arg2]));
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
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void changeColor(int newColor) {
		pagerSlideTab.setIndicatorColor(newColor);
		pager.setBackgroundColor(newColor);
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.lists_layout);
		listLayout.setBackgroundColor(newColor);
		ColorDrawable color = new ColorDrawable(newColor);
		lsMenu.setDivider(color);
		lsTheme.setDivider(color);
		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}
		currentColor = newColor;

	}
	
	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(isDrawerOpen){
			slidingDrawer.closeDrawers();
			if(lsTheme.getVisibility() == View.VISIBLE)
				lsTheme.setVisibility(View.GONE);
		}	
		else{
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}	
	
	
}
