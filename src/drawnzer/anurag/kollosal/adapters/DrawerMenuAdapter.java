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
 *                             anuraxsharma1512@gmail.com
 *
 */

package drawnzer.anurag.kollosal.adapters;

import drawnzer.anurag.kollosal.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerMenuAdapter extends BaseAdapter{

	private Context ctx;
	private LayoutInflater inf;
	private String[] menuItems;
	private int[] icons = {
		R.drawable.preferences,
		R.drawable.check_for_update,
		R.drawable.help,
		R.drawable.bug,
		R.drawable.about,
		R.drawable.theme
	};
	public DrawerMenuAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.ctx = context;
		this.inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.menuItems = ctx.getResources().getStringArray(R.array.lsMenu);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuItems.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return menuItems[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	class hold{
		ImageView img;
		TextView txt;
	}
	
	@Override
	public View getView(int arg0, View convert, ViewGroup arg2) {
		// TODO Auto-generated method stub
		hold hld = new hold();
		if(convert == null){
			convert = inf.inflate(R.layout.list_item, arg2 , false);
			hld.img = (ImageView)convert.findViewById(R.id.thumb);
			hld.txt = (TextView)convert.findViewById(R.id.displayName);
			convert.setTag(hld);
		}else
			hld = (hold) convert.getTag();
		hld.img.setImageDrawable(ctx.getResources().getDrawable(icons[arg0]));
		hld.txt.setText(menuItems[arg0]);
		return convert;
	}
}
