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


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
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
	private String videoPath;
	private int NAV_BAR_OPTIONS;
	private int color;
	private OrientationEventListener orientationlistener;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = getSharedPreferences("APP_SETTINGS", 0);
		color = prefs.getInt("CONTROLLER_COLOR", 0x66C74B46);
		videoPath = getIntent().getData().toString();
		NAV_BAR_OPTIONS = prepareNavBarOptions(); 
		setContentView(R.layout.video_view);
		detector = new GestureDetector(new SwipeGestureDetector());
		
				
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
