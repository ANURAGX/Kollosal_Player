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

import drawnzer.anurag.kollosal.fragments.VideoFragment;
import drawnzer.anurag.kollosal.models.VideoItem;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;


/**
 * THIS CLASS PLAYS THE VIDEO FROM THE PROVIDED PATH .....
 * @author Anurag....
 *
 */
public class VideoPlayer extends Activity{

	private GestureDetector detector;
	private MediaController mController;
	private VideoView videoView;
	private String videoPath;
	private int NAV_BAR_OPTIONS;
	private int color;
	private OrientationEventListener orientationlistener;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		SharedPreferences prefs = getSharedPreferences("APP_SETTINGS", 0);
		color = prefs.getInt("CONTROLLER_COLOR", 0x66C74B46);
		videoPath = getIntent().getData().toString();
		NAV_BAR_OPTIONS = prepareNavBarOptions(); 
		setContentView(R.layout.video_view);
		detector = new GestureDetector(new SwipeGestureDetector());
		mController = new MediaController(VideoPlayer.this , color);
		videoView = (VideoView) findViewById(R.id.videoView);
		//videoView.setHardwareDecoder(true);
		//videoView.setBufferSize(1024*1024*2);
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
		
		/**
		 * This listener is responsible for changing the orientation from reverse_landscape to landscape
		 * and vice versa....
		 */
		orientationlistener = new OrientationEventListener(this , SensorManager.SENSOR_DELAY_NORMAL) {
			@Override
			public void onOrientationChanged(int orientation) {
				// TODO Auto-generated method stub
				if(orientation>180)
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				else
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
					
				
			}
		};
		orientationlistener.enable();
		
		/**
		 * This is handle the next button press request and load the next video
		 * from the current directory if available....
		 */
		mController.setOnNextVideoSelectListener(new MediaController.OnNextVideoSelectListener() {
			@Override
			public void onNextVideoSelect() {
				// TODO Auto-generated method stub
				VideoItem item = VideoFragment.getNextVideo();
				if(item != null)
					videoView.setVideoPath(item.getVideoPath());
			}
		});
		
		/**
		 * This is handle the previous button press request and load the next video
		 * from the current directory if available....
		 */
		mController.setOnPreviousVideoSelectListener(new MediaController.OnPreviousVideoSelectListener(){
			@Override
			public void onPreviousVideoSelect() {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * This is to handle the click on Qr Code Button....
		 */
		mController.setOnQrCodeClickListener(new MediaController.OnQrCodeClickListener() {
			@Override
			public void onCLick() {
				// TODO Auto-generated method stub
				
			}
		});
				
	}	
	
	/**
	 * This function calculate
	 * @return
	 */
	@SuppressLint("InlinedApi")
	private int prepareNavBarOptions(){
		if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);		
		return super.onTouchEvent(event);
	}	
	
	
	/**
	 * 
	 * @author Anurag....
	 *
	 */
	class SwipeGestureDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY){
	        switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
	        case 1:
	        	//swiped to top....
	        	return true;
	        case 2:
	            //swiped to left....
	        	return true;
	        case 3:
	        	//swiped to down....
	        	return true;
	        case 4:
	        	//swiped to right....
	        	//Toast.makeText(VideoPlayer.this, ""+(int)(e2.getX()-e1.getX()), Toast.LENGTH_SHORT).show();
	        	mController.hideExceptSeekBar();
	        	long swipe = (long)(e2.getX()-e1.getX());
	        	swipe = swipe*10 + (long)videoView.getCurrentPosition();
	        	videoView.seekTo(swipe); 
	        	return true;
	        }
	        //unrecognized swipe....
	        return false;
	    }
	    
	    //function to calculate the exact quadrant and angle....
	    private int getSlope(float x1, float y1, float x2, float y2) {
	        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
	        if (angle > 45 && angle <= 135)
	        // top
	        return 1;
	        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
	        // left
	        return 2;
	        if (angle < -45 && angle >= -135)
	        // down
	        return 3;
	        if (angle >= -45 && angle <= 45)
	        // right
	        return 4;
	        return 0;
	    }
	}
}
