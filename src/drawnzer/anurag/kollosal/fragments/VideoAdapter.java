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

import drawnzer.anurag.kollosal.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter{

	LayoutInflater inflater;
	Context ctx;
	public VideoAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	class Holder{
		ImageView thumb;
		TextView name;
	}
	
	@Override
	public View getView(int position, View convert, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		Holder hold = new Holder();
		if(convert == null){
			convert = inflater.inflate(R.layout.list_item, arg2 , false);
			hold.thumb = (ImageView) convert.findViewById(R.id.thumb);
			hold.name = (TextView) convert.findViewById(R.id.displayName);
			convert.setTag(hold);
		}else
			hold = (Holder) convert.getTag();	
		
		
		return convert;
	}

}
