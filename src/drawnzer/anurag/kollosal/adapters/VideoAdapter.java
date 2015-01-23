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


import java.util.ArrayList;
import java.util.HashMap;

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
import drawnzer.anurag.kollosal.Utils;
import drawnzer.anurag.kollosal.fragments.VideoFragment;
import drawnzer.anurag.kollosal.models.VideoItem;

public class VideoAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context ctx;
	
	//storing the video thumbnail in hashmap and using key to 
	//retrieve them....
	private HashMap<String , Bitmap> thumbs;
	
	//list of videos....
	private ArrayList<VideoItem> list;
	
	//true then loads video thumbnail....
	private boolean thumbLoading;
	
	private int thumb_width;
	private int thumb_height;
	/**
	 * 
	 * @param context
	 * @param object list of videos or parent folder for videos....
	 * @param loadThumb true then loads the thumb for video....
	 */
	public VideoAdapter(Context context , ArrayList<VideoItem> object ,boolean loadThumb) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.list = object;
		thumbLoading = loadThumb;
		thumbs = new HashMap<String , Bitmap>();
		this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		thumb_width = Utils.convert_dp_to_px(ctx, 100);
		thumb_height = Utils.convert_dp_to_px(ctx, 70);
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
		ImageView newVid;
	}
	
	@Override
	public View getView(int position, View convert2, ViewGroup arg2) {
		// TODO Auto-generated method stub
		VideoItem item = list.get(position);
		Holder hold = new Holder();
		View convert = null;
		convert = inflater.inflate(R.layout.video_grid_item, arg2 , false);
			hold.thumb = (ImageView) convert.findViewById(R.id.grid_icon);
			hold.name = (TextView) convert.findViewById(R.id.grid_artist_name);
			hold.newVid = (ImageView) convert.findViewById(R.id.new_video);
			convert.setTag(hold);
		
		hold.name.setText(item.getDisplayName());
		
		if(item.isVideoNew())
			hold.newVid.setVisibility(View.VISIBLE);
		
		if(thumbLoading && VideoFragment.isFolderExpanded()){
			Bitmap map = thumbs.get(item.getVideoPath());
			if(map == null){
				hold.thumb.setTag(item.getVideoPath());
				new LoadThumb(item, hold.thumb).execute();
			}	
			else
				hold.thumb.setImageBitmap(map);
		}	
		return convert;
	}
	
	/**
	 * 
	 * @author anurag
	 *
	 */
	private class LoadThumb extends AsyncTask<Void, Void, Void>{

		VideoItem itm;
		ImageView image;
		Bitmap map;
		
		public LoadThumb(VideoItem item , ImageView img) {
			// TODO Auto-generated constructor stub
			itm = item;
			image = img;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(map != null)
				if(image.getTag().equals(itm.getVideoPath()))
					image.setImageBitmap(map);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			try{
				/*map = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(ctx,
			               itm.getVideoPath(), MediaStore.Video.Thumbnails.MICRO_KIND), thumb_width, thumb_height);
				thumbs.put(itm.getVideoPath(), map);*/
			}catch(OutOfMemoryError e){
				/*
				 * bitmap consumes a large memory in heap,in case we get out of memory error
				 * deallocate the hashmap and reallocate hashmap again....
				 */
				thumbs = null;
				thumbs = new HashMap<String ,Bitmap>();
			}
			return null;
		}		
	}
}
