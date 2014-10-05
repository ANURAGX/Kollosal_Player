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

import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.models.VideoItem;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * 
 * @author Anurag....
 *
 */
public class VideoFragment extends Fragment{

	private VideoAdapter adapter;
	private ArrayList<VideoItem> list;
	private Context ctx;
	private GridView grid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.ctx = getActivity();
		this.list = new ArrayList<VideoItem>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.timeline_view, container , false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		grid = (GridView)view.findViewById(R.id.video_grid);
		adapter = new VideoAdapter(ctx, list);
		grid.setAdapter(adapter);
		new LoadVideo().execute();
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
			Cursor cursor = ctx.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				VideoItem item = new VideoItem(path , ctx);
				list.add(item);
				publishProgress(new Void[]{});
			}
			cursor.close();
			return null;
		}
	}	
}
