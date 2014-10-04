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


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import drawnzer.anurag.kollosal.utils.MusicService;
import drawnzer.anurag.kollosal.utils.MusicService.LocalBinder;


/**
 * 
 * @author Anurag....
 *
 */
public class MusicPlayer extends FragmentActivity implements View.OnClickListener{

	private final int PLAYING_COMPLETED = 0;
	private final int PAUSE = 1;
	private final int NEXT = 2;
	private final int PREVIOUS = 3;
	private final int SHUFFLE = 4;
	private final int LOOP = 5;
	private final int ERROR = 6;
	private final int SEEKBAR_MAX = 7;
	private Drawable oldBackground;
	private SharedPreferences prefs;
	private Intent intent;
	private int color;
	private SeekBar seekbar;
	private MusicService musicPlayback;
	
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			musicPlayback = null;
		}
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			
			//stopping other music players...
			Intent i = new Intent("com.android.music.musicservicecommand");
		    i.putExtra("command", "pause");
		    sendBroadcast(i);			
		    musicPlayback = ((LocalBinder)arg1).getService();
			musicPlayback.setHandler(handler);
		    musicPlayback.play();
		}
	};
	
	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
				case PLAYING_COMPLETED:
						seekbar.setProgress(0);
						break;
				case PAUSE:
						break;
				case NEXT:
						break;
				case PREVIOUS:
						break;	
				case SHUFFLE:
						break;
				case LOOP:
						break;
				case ERROR:
						break;
				case SEEKBAR_MAX:
						seekbar.setMax((int)msg.obj);
						break;
				default:
					seekbar.setProgress(msg.what);						
			}
		}		
	};
	
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
		
		
		Intent intent = new Intent(MusicPlayer.this,MusicService.class);
		intent.setAction("play");
		intent.setData(Uri.parse(path));
		//starting service...
		startService(intent);
		
		//binding the service to the current activity....
		bindService(intent, connection, Context.BIND_IMPORTANT);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		}
	}	
	
	public interface UpdateUI{
		public void updateSeekbar(SeekBar bar);
	}
}
