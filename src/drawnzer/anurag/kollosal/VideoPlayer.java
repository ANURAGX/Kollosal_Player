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

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * THIS CLASS PLAYS THE VIDEO FROM THE PROVIDED PATH .....
 * @author Anurag....
 *
 */
public class VideoPlayer extends Activity{

	private MediaController mController;
	private VideoView videoView;
	String videoPath;
	private static int NAV_BAR_OPTIONS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		
		videoPath = getIntent().getData().toString();
		NAV_BAR_OPTIONS = prepareNavBarOptions(); 
		setContentView(R.layout.video_view);
		
		mController = new MediaController(VideoPlayer.this);
		videoView = (VideoView) findViewById(R.id.videoView);
		videoView.setVideoPath(videoPath);
		videoView.requestFocus();
		videoView.setMediaController(mController);
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.setPlaybackSpeed(1.0f);
			}
		});
		
		/*
		 * This listener tells when the media controller is visible...
		 * along with that I m showing the navigation bar and status bar...  
		 */
		
		mController.setOnShownListener(new MediaController.OnShownListener() {
			@Override
			public void onShown() {
				// TODO Auto-generated method stub
				
			}
		}); 
		
		/*
		 *  This listener tells when the media controller becomes invisible....
		 *  along with that I m making the navigation bar and staus bar invisible....
		 */
		mController.setOnHiddenListener(new MediaController.OnHiddenListener() {
			@Override
			public void onHidden() {
				// TODO Auto-generated method stub
				getWindow().getDecorView().setSystemUiVisibility(NAV_BAR_OPTIONS);
			}
		});
		getWindow().getDecorView().setSystemUiVisibility(NAV_BAR_OPTIONS);
	}	
	
	
	/**
	 * This function calculat
	 * @return
	 */
	@SuppressLint("InlinedApi")
	private int prepareNavBarOptions(){
		if(Build.VERSION.SDK_INT >18){
			int ret = View.SYSTEM_UI_FLAG_FULLSCREEN |
					  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
					  View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
					  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
					  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
					  View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
			return ret;
		}else if(Build.VERSION.SDK_INT >15){
			int ret = View.SYSTEM_UI_FLAG_FULLSCREEN |
					  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
					  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
					  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
					  View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
			return ret;
		}else{
			int ret = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;					  
			return ret;
		}
	}
}
