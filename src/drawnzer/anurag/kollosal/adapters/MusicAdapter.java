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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.models.MusicItem;

/**
 * 
 * @author Anurag....
 *
 */
public class MusicAdapter extends BaseAdapter{

	private LayoutInflater inf;
	private Context ctx;
	private ArrayList<MusicItem> list;
	private Drawable noArt;
	private MusicItem item;
	public MusicAdapter(Context context , ArrayList<MusicItem> objects) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = objects;
		noArt = ctx.getResources().getDrawable(R.drawable.no_album);			
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
	
	class hold{
		ImageView img;
		TextView artist;
		TextView album;
	}

	@Override
	public View getView(int position, View convert, ViewGroup arg2) {
		// TODO Auto-generated method stub
		hold hld = new hold();
		if(convert == null){
			convert = inf.inflate(R.layout.grid_item, arg2 , false);
			hld.img = (ImageView)convert.findViewById(R.id.grid_icon);
			hld.album = (TextView)convert.findViewById(R.id.grid_album_name);
			hld.artist = (TextView)convert.findViewById(R.id.grid_artist_name);
			convert.setTag(hld);
		}else
			hld = (hold) convert.getTag();
		item = list.get(position);
		Bitmap map = item.getArt();
		if(map != null)
			hld.img.setImageBitmap(map);
		else
			hld.img.setImageDrawable(noArt);
		hld.album.setText(item.getAlbumName());
		hld.artist.setText(item.getArtistName());
		return convert;
	}
}
