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

import drawnzer.anurag.kollosal.LongClick;
import drawnzer.anurag.kollosal.MusicPlayer;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.models.MusicItem;
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

/**
 * 
 * @author Anurag....
 *
 */
public class MusicFragment extends Fragment{

	private GridView musicGrids;
	private static ArrayList<MusicItem> list;
	private static MusicAdapter adapter;
	private static LoadMusic loadMusic;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
				MusicItem item = list.get(position);
				Intent intent = new Intent(getActivity(), MusicPlayer.class);
				intent.setData(Uri.parse(item.getPath()));
				startActivity(intent);
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
		if(loadMusic == null){
			loadMusic = new LoadMusic();
			loadMusic.execute();
		}	
	}

	private class LoadMusic extends AsyncTask<Void, Void, Void>{
				
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
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
			Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					null, null, null, null);
			while(cursor.moveToNext()){
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
				MusicItem item = new MusicItem(name, path);
				list.add(item);
				publishProgress(new Void[]{});
			}			
			cursor.close();
			return null;
		}		
	}	
}
