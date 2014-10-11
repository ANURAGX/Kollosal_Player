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

import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Anurag....
 *
 */
public class LongClick extends Activity implements View.OnClickListener{

	private boolean visible;
	private int color;
	private String ACTION;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = getSharedPreferences("APP_SETTINGS", 0);
		color = prefs.getInt("SEMI_APP_COLOR", 0x66C74B46);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.long_click_new_ui);
		visible = true;
		intent = getIntent();
		initUI();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.play_all:
					break;
					
			case R.id.delete:
					break;
					
			case R.id.share:
					break;
					
			case R.id.more:
					{
						visible = !visible;
						TextView txt = (TextView)findViewById(R.id.more_button);
						if(!visible){
							
							LinearLayout l2 = (LinearLayout)findViewById(R.id.row2);
							l2.setVisibility(View.GONE);
							LinearLayout l3 = (LinearLayout)findViewById(R.id.row3);
							l3.setVisibility(View.GONE);
							txt.setText(getString(R.string.more));
						}else if(visible){
							
							LinearLayout l2 = (LinearLayout)findViewById(R.id.row2);
							l2.setVisibility(View.VISIBLE);
							LinearLayout l3 = (LinearLayout)findViewById(R.id.row3);
							l3.setVisibility(View.VISIBLE);
							txt.setText(getString(R.string.less));
						}
					}
					break;
					
			
					
			case R.id.reload_thumb:
					break;
				
				
		}		
	}	
	
	/**
	 * 
	 */
	private void initUI(){
		LinearLayout lin = (LinearLayout)findViewById(R.id.main_long);
		lin.setBackgroundColor(color);
		ACTION = intent.getAction();
		if(ACTION.equals("MUSIC")){
			ImageView img = (ImageView)findViewById(R.id.video_play_btn);
			img.setVisibility(View.GONE);
			buildAlbumArt();
		}else if(ACTION.equals("VIDEO"))
			buildVideoPreview();
	}
	
	/**
	 * 
	 */
	private void buildAlbumArt(){
		String path = intent.getStringExtra("PATH");
		try{
			MediaMetadataRetriever ret = new MediaMetadataRetriever();
			ret.setDataSource(path);
			byte[] data = ret.getEmbeddedPicture();
			Bitmap map = BitmapFactory.decodeByteArray(data, 0, data.length);
			if(map !=null){
				ImageView img = (ImageView)findViewById(R.id.grid_icon);
				img.setImageBitmap(map);
			}
		}catch(Exception e){
			
		}
	}
	
	private void buildVideoPreview(){
		String path = intent.getStringExtra("PATH");
		try{
			Bitmap map;// = ret.getFrameAtTime(10000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
			map = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(LongClick.this,
	               path, MediaStore.Video.Thumbnails.MINI_KIND), 500, 500);
			if(map !=null){
				ImageView img = (ImageView)findViewById(R.id.grid_icon);
				img.setImageBitmap(map);
			}
		}catch(Exception e){
			
		}
	}
}
