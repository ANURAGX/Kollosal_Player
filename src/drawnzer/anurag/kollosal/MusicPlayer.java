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


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;


/**
 * 
 * @author Anurag....
 *
 */
public class MusicPlayer extends FragmentActivity implements View.OnClickListener{

	private final Handler handler = new Handler();
	private Drawable oldBackground;
	private SharedPreferences prefs;
	private Intent intent;
	private int color;
	private SeekBar seekbar;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		prefs = getSharedPreferences("APP_SETTINGS", 0);
		color = prefs.getInt("APP_COLOR", 0xFFC74B46);
		setContentView(R.layout.music_player);
		seekbar = (SeekBar)findViewById(R.id.seekbar);
		intent = getIntent();
		changeColor(color);
		updateUI();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.music_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	private void changeColor(int newColor) {
		LinearLayout listLayout = (LinearLayout)findViewById(R.id.main);
		listLayout.setBackgroundColor(newColor);
		
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
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);
			}
			oldBackground = ld;
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}
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
	
	private void updateUI(){
		String path = intent.getData().toString();
		MediaMetadataRetriever ret = new MediaMetadataRetriever();
		ret.setDataSource(path);
		ImageView img = (ImageView)findViewById(R.id.album_art);
		try{
			byte[] data = ret.getEmbeddedPicture();
			Bitmap map = BitmapFactory.decodeByteArray(data, 0, data.length);
			if(map != null)
				img.setImageBitmap(map);
		}catch(Exception e){
			
		}
		
		try{
			getActionBar().setTitle(ret.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
		}catch(Exception e){
			
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		}
	}	
}
