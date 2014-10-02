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
 */

package drawnzer.anurag.kollosal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThemeAdapter extends BaseAdapter{

	LayoutInflater inf;
	Context ctx;
	String[] list;
	int[] colors={
		R.color.grey,R.color.green,
		R.color.red,R.color.orange,
		R.color.blue,R.color.violet
	};
	public ThemeAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = ctx.getResources().getStringArray(R.array.theme_colors);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class hold{
		ImageView img;
		TextView txt;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		hold hld = new hold();
		if(convertView == null){
			convertView = inf.inflate(R.layout.list_item, parent , false);
			hld.img = (ImageView)convertView.findViewById(R.id.thumb);
			hld.txt = (TextView)convertView.findViewById(R.id.displayName);
			convertView.setTag(hld);
		}else
			hld = (hold) convertView.getTag();
		hld.txt.setText(list[position]);
		hld.img.setBackgroundColor(ctx.getResources().getColor(colors[position]));
		return convertView;
	}
	
	public int getColor(int position){
		return ctx.getResources().getColor(colors[position]);
	}
}
