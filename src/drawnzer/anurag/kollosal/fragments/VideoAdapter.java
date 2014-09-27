package drawnzer.anurag.kollosal.fragments;

import drawnzer.anurag.kollosal.R;
import drawnzer.anurag.kollosal.utils.LoadVideoFromSDUtils;
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
		return LoadVideoFromSDUtils.VIDEO_LIST.size();
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
		
		hold.thumb.setImageBitmap(LoadVideoFromSDUtils.VIDEO_LIST.get(position).getThumbnail());
		hold.name.setText(LoadVideoFromSDUtils.VIDEO_LIST.get(position).getDisplayName());
		return convert;
	}

}
