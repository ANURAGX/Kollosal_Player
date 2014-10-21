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

package drawnzer.anurag.kollosal.fragments;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import drawnzer.anurag.kollosal.LongClick;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.models.MusicItem;

/**
 * 
 * @author Anurag....
 *
 */
@SuppressLint("HandlerLeak")
public class MusicFragment extends Fragment implements PanelSlideListener{

	private GridView musicGrids;
	private static ArrayList<MusicItem> list;
	private static MusicAdapter adapter;
	private static LoadMusic loadMusic;
	private static SlidingUpPanelLayout panel;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(!initSlider_player){
				initSlider_Player();
			}
			adapter.notifyDataSetChanged();
		}	
	};
	
	private boolean initSlider_player;
	
	private ImageView album_art;
	
	private ImageView slider_alb_art;
	
	private TextView song_name;
	
	private TextView alb_name;
	//
	private LinearLayout mini_controls;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initSlider_player = false;
		View view = inflater.inflate(R.layout.music_tab, container , false);
		if(list == null)
			list = new ArrayList<MusicItem>();
		
		if(adapter == null)
			adapter = new MusicAdapter(getActivity(), list);
		
		return view;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(v, savedInstanceState);
		musicGrids = (GridView)v.findViewById(R.id.music_grids);
		musicGrids.setSelector(R.drawable.button_click);
		musicGrids.setAdapter(adapter);
		musicGrids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				// TODO Auto-generated method stub
				/*MusicItem item = list.get(position);
				Intent intent = new Intent(getActivity(), MusicPlayer.class);
				intent.setData(Uri.parse(item.getPath()));
				startActivity(intent);*/
				initSlider_Player(list.get(position));
				panel.expandPanel();
			}
		});
		
		musicGrids.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LongClick.class);
				intent.setAction("MUSIC"); 
				intent.putExtra("PATH", list.get(position).getPath());
				startActivity(intent);
				return true;
			}
		});
		
		mini_controls = (LinearLayout)v.findViewById(R.id.player_mini_controls);
		album_art = (ImageView)v.findViewById(R.id.album_art);
		slider_alb_art = (ImageView)v.findViewById(R.id.slider_alb_art);
		song_name = (TextView)v.findViewById(R.id.name);
		alb_name = (TextView)v.findViewById(R.id.alb_name);
		
		//setting color for sliding panel layout....
		panel = (SlidingUpPanelLayout)v.findViewById(R.id.sliding_layout);
		int color = getActivity().getSharedPreferences("APP_SETTINGS", 0).getInt("APP_COLOR",0xFFC74B46);
		panel.setBackgroundColor(color);
		panel.setPanelSlideListener(this);
		
		if(loadMusic == null){
			loadMusic = new LoadMusic();
			loadMusic.start();
		}	
	}

	private class LoadMusic extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
				MusicItem item = new MusicItem(name, path);
				list.add(item);
				handler.sendEmptyMessage(0);
			}			
			cursor.close();
		}		
	}	
	
	/**
	 * 
	 * @param color
	 */
	public static void notifyColorChange(int color){
		panel.setBackgroundColor(color);
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isSliderOpened(){
		return panel.isPanelExpanded();
	}
	
	/**
	 * 
	 */
	public static void notifyPanelClose(){
		try{
			panel.collapsePanel();
		}catch(Exception e){
			
		}		
	}
	
	@Override
	public void onPanelSlide(View panel, float slideOffset) {
		// TODO Auto-generated method stub
		mini_controls.setVisibility(View.GONE);
	}

	@Override
	public void onPanelCollapsed(View panel) {
		// TODO Auto-generated method stub
		mini_controls.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPanelExpanded(View panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelAnchored(View panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelHidden(View panel) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * function the create player in slider ....
	 */
	private void initSlider_Player() {
		// TODO Auto-generated method stub
		initSlider_player = true;
		song_name.setText(list.get(0).getDisplayName());
		alb_name.setText(list.get(0).getAlbumName());
		try{
			MediaMetadataRetriever ret = new MediaMetadataRetriever();
			ret.setDataSource(list.get(0).getPath());
			byte[] bits = ret.getEmbeddedPicture();
			Bitmap map = BitmapFactory.decodeByteArray(bits, 0, bits.length);
			album_art.setImageBitmap(map);
			slider_alb_art.setImageBitmap(map);
		}catch(Exception e){
			initSlider_player = false;
		}
	}
	
	/**
	 * 
	 * @param musicItem
	 */
	protected void initSlider_Player(MusicItem musicItem) {
		// TODO Auto-generated method stub
		song_name.setText(musicItem.getDisplayName());
		alb_name.setText(musicItem.getAlbumName());
		try{
			MediaMetadataRetriever ret = new MediaMetadataRetriever();
			ret.setDataSource(musicItem.getPath());
			byte[] bits = ret.getEmbeddedPicture();
			Bitmap map = BitmapFactory.decodeByteArray(bits, 0, bits.length);
			album_art.setImageBitmap(map);
			slider_alb_art.setImageBitmap(map);
		}catch(Exception e){
			initSlider_player = false;
		}
	}
	
}
