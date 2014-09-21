package drawnzer.anurag.kollosal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class NetworkStreamFragments extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout layout = new FrameLayout(getActivity());
		layout.setLayoutParams(params);
	
		int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		
		EditText enterURL = new EditText(getActivity());
		params.setMargins(margin, margin, margin, margin);
		enterURL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		enterURL.setGravity(Gravity.TOP);
		enterURL.setHint("Enter URL");
		layout.addView(enterURL);
		return layout;
	}
}
