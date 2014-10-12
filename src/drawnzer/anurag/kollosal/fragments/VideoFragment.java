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
import android.widget.GridView;
import drawnzer.anurag.kollosal.LongClick;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.VideoPlayer;
import drawnzer.anurag.kollosal.models.VideoItem;

/**
 * 
 * @author Anurag....
 *
 */
public class VideoFragment extends Fragment{

	private static VideoAdapter adapter;
	private static ArrayList<VideoItem> list;
	private GridView grid;
	private static LoadVideo loadVideo;
	private static HashMap<String, Integer> addedItems;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.timeline_view, container , false);
		if(list == null){
			addedItems = new HashMap<String , Integer>();
			list = new ArrayList<VideoItem>();
		}	
		if(adapter == null)
			adapter = new VideoAdapter(getActivity(), list);
		return view;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		grid = (GridView)v.findViewById(R.id.video_grid);
		grid.setSelector(R.drawable.button_click);
		grid.setAdapter(adapter);
		
		//long click action is taken here.....
		grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), LongClick.class);
				intent.setAction("VIDEO");
				intent.putExtra("PATH", list.get(position).getVideoPath());
				startActivity(intent);
				return false;
			}
		});
		
		//launching the video player....
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), VideoPlayer.class);
				intent.setData(Uri.parse(list.get(position).getVideoPath()));
				startActivity(intent);
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
			adapter.notifyDataSetChanged();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Cursor cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				//addedItems.put(key, value)
				VideoItem item = new VideoItem(path , getActivity());
				list.add(item);
				publishProgress(new Void[]{});
			}
			cursor.close();
			return null;
		}
	}	
}
