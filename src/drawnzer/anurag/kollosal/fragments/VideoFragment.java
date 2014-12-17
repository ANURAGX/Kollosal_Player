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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;

import drawnzer.anurag.kollosal.LongClick;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.VideoPlayer;
import drawnzer.anurag.kollosal.adapters.FolderAdapter;
import drawnzer.anurag.kollosal.adapters.VideoAdapter;
import drawnzer.anurag.kollosal.models.VideoItem;

/**
 * 
 * @author Anurag....
 *
 */
public class VideoFragment extends Fragment implements PanelSlideListener{

	private static VideoAdapter videoAdapter;
	
	private static FolderAdapter folderAdapter;
	
	private ArrayList<VideoItem> list;
	private static ListView grid;
	private LoadVideo loadVideo;
	private static SlidingUpPanelLayout slider;
	
	private HashMap<String, VideoItem> addedItems;
	private LinearLayout mini_controls;	
	
	private static boolean folder_expanded;
	
	private static VideoItem selected_folder;
	
	private static int selected_video_position;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.timeline_view, container , false);
		if(list == null){
			addedItems = new HashMap<String , VideoItem>();
			list = new ArrayList<VideoItem>();
		}	
		if(videoAdapter == null)
			videoAdapter = new VideoAdapter(getActivity(), list , false);
		
		if(folderAdapter == null)
			folderAdapter = new FolderAdapter(getActivity(), list);
		
		folder_expanded = false;
		
		return view;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		grid = (ListView)v.findViewById(R.id.video_grid);
		grid.setSelector(R.drawable.button_click);
		grid.setAdapter(folderAdapter);
		
		//long click action is taken here.....
		grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LongClick.class);
				intent.setAction("VIDEO");
				intent.putExtra("PATH", list.get(position).getVideoPath());
				startActivity(intent);
				return true;
			}
		});
		
		slider = (SlidingUpPanelLayout)v.findViewById(R.id.sliding_layout_video);
		mini_controls = (LinearLayout)v.findViewById(R.id.player_mini_controls);
		slider.setPanelSlideListener(this);
		//launching the video player....
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(getActivity(), VideoPlayer.class);
				intent.setData(Uri.parse(list.get(position).getVideoPath()));
				startActivity(intent);*/
				
				if(folder_expanded){
					//the folder is open,now choosing a video file to play....
					Intent intent = new Intent(getActivity(), VideoPlayer.class);
					intent.setData(Uri.parse(selected_folder.getChildVideos().get(position).getVideoPath()));
					startActivity(intent);
					selected_video_position = position;
				}else{
					
					//opening a folder....
					folder_expanded = true;
					selected_folder = list.get(position);
					grid.setAdapter(new VideoAdapter(getActivity(), selected_folder.getChildVideos(), true));
				}	
			}
		});
		
		if(loadVideo == null){
			loadVideo = new LoadVideo();
			loadVideo.execute();
		}
	}	
	
	/**
	 * 
	 * @author Anurag....
	 *
	 */
	private class LoadVideo extends AsyncTask<Void, Void, Void>{
		
		public LoadVideo() {
			// TODO Auto-generated constructor stub
		}

		

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			folderAdapter.notifyDataSetChanged();
		}



		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			Cursor cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				//addedItems.put(key, value)
				File file = new File(path);
				String parent = file.getParent();
				VideoItem itm = addedItems.get(parent);
				if(itm == null){
					VideoItem item = new VideoItem(file , true);
					list.add(item);
					addedItems.put(file.getParent(), item);
			
					//adding the current video also....
					addedItems.get(parent).addVideo(new VideoItem(file , false));
				}else{
					addedItems.get(parent).addVideo(new VideoItem(file , false));
				}	
				
				publishProgress();
			}
			cursor.close();
			return null;
		}		
	}

	
	/**
	 * changes the background color of umano slider menu....
	 * @param color
	 */
	public static void notifyColorChange(int color){
		slider.setBackgroundColor(color);
	}

	/**
	 * 
	 * @return true if umano menu is opened....
	 */
	public static boolean isSliderOpened(){
		return slider.isPanelExpanded();
	}
	
	/**
	 * collapses the umano slider menu.... 
	 */
	public static void notifyPanelClose(){
		try{
			slider.collapsePanel();
		}catch(Exception e){
			
		}			
	}
	
	/**
	 * 
	 * @return true if a folder is opened for viewing videos....
	 */
	public static boolean isFolderExpanded(){
		return folder_expanded;
	}
	
	/**
	 * collapses the expanded folder and displays initial folder list....
	 */
	public static void collapseFolder(){
		folder_expanded = false;
		grid.setAdapter(folderAdapter);
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
	 * this function is called when next button is clicked from video player.... 
	 * 
	 * @return the next video track to be played from the sequence of opened folder....
	 */
	public static VideoItem getNextVideo(){
		VideoItem item = null;
		
		if(++selected_video_position < selected_folder.getTotalChildVideos())
			item = selected_folder.getChildVideos().get(selected_video_position);
		else
			--selected_video_position;
		return item;
	}
	
	/**
	 * this function is called when previous button is clicked from video player....
	 * 
	 * @return the back video to be played from the opened folder....
	 */
	public static VideoItem getPreviousVideo(){
		VideoItem item = null;
		
		if(selected_video_position != 0)
			if(--selected_video_position >= 0)
				item = selected_folder.getChildVideos().get(selected_video_position);
		return item;
	}
	
}
