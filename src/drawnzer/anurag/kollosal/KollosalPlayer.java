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

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import com.astuetz.PagerSlidingTabStrip;

/**
 * 
 * @author ANURAG....
 *
 */


public class KollosalPlayer extends FragmentActivity{
	
	private ViewPager pager;
	private PagerSlidingTabStrip pagerSlideTab;
	private int currentColor;
	private SharedPreferences prefs;
	private boolean isDrawerOpen; 
	private DrawerLayout slidingDrawer;
	private ActionBarDrawerToggle toggle;
	private ListView lsMenu;
	private ListView lsTheme;
	
	
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
				changeColor(getResources().getColor(Constant.COLORS[arg2]));
				SharedPreferences.Editor edit = prefs.edit();
				edit.putInt("APP_COLOR", getResources().getColor(Constant.COLORS[arg2]));
				edit.putInt("SEMI_APP_COLOR", getResources().getColor(Constant.SEMI_COLORS[arg2]));
				edit.putInt("CONTROLLER_COLOR", getResources().getColor(Constant.CONTROLLER_COLORS[arg2]));
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
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void changeColor(int newColor) {
		pagerSlideTab.setIndicatorColor(getResources().getColor(R.color.semi_white));
		RelativeLayout ml = (RelativeLayout)findViewById(R.id.main_ui);
		ml.setBackgroundColor(newColor);
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.lists_layout);
		listLayout.setBackgroundColor(newColor);
		ColorDrawable color = new ColorDrawable(newColor);
		lsMenu.setDivider(color);
		lsTheme.setDivider(color);
		Drawable colorDrawable = new ColorDrawable(newColor);
		getActionBar().setBackgroundDrawable(colorDrawable);
		// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
	}	

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
