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


package drawnzer.anurag.kollosal.adapters;

import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.provider.MediaStore;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.fragments.VideoFragment;
import drawnzer.anurag.kollosal.models.VideoItem;

public class FolderAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context ctx;
	private ArrayList<VideoItem> list;
	
		
	/**
	 * 
	 * @param context
	 * @param object list of videos or parent folder for videos....
	 * @param loadThumb true then loads the thumb for video....
	 */
	public FolderAdapter(Context context , ArrayList<VideoItem> object) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.list = object;
		this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	class Holder{
		ImageView thumb;
		TextView name;
		TextView vidCount;
	}
	
	@Override
	public View getView(int position, View convert, ViewGroup arg2) {
		// TODO Auto-generated method stub
		VideoItem item = list.get(position);
		Holder hold = new Holder();
		if(convert == null){
			convert = inflater.inflate(R.layout.folder_item, arg2 , false);
			hold.thumb = (ImageView) convert.findViewById(R.id.grid_icon);
			hold.name = (TextView) convert.findViewById(R.id.grid_artist_name);
			hold.vidCount = (TextView) convert.findViewById(R.id.total_video);
			convert.setTag(hold);
		}else
			hold = (Holder) convert.getTag();	
		
		hold.name.setText(item.getDisplayName());
		hold.vidCount.setText(item.getTotalChildVideos() + " Videos");
		return convert;
	}
	
	
}
