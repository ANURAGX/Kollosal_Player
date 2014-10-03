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
import drawnzer.anurag.kollosal.models.MusicItem;
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
public class MusicFragment extends Fragment{

	private GridView musicGrids;
	private ArrayList<MusicItem> list;
	private MusicAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.music_tab, container , false);
		musicGrids = (GridView)view.findViewById(R.id.music_grids);
		musicGrids.setSelector(R.drawable.button_click);
		list = new ArrayList<MusicItem>();
		adapter = new MusicAdapter(getActivity(), list);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		musicGrids.setAdapter(adapter);
		new LoadMusic().execute();
	}

	private class LoadMusic extends AsyncTask<Void, Void, Void>{
				
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

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
