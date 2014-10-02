package drawnzer.anurag.kollosal;


import drawnzer.anurag.kollosal.fragments.CloudPlayerFragment;
import drawnzer.anurag.kollosal.fragments.KollosalCameraFragment;
import drawnzer.anurag.kollosal.fragments.NetworkStreamFragments;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TaskAdapter extends FragmentPagerAdapter{

	private final String[] TITLES = { "VIDEOS", "MUSIC", "NETWORK STREAM", "CLOUD PLAYER", ""
			+ "SHARE  CAMERA", "YOU TUBE",};
	
	public TaskAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		
		switch(position){
			case 2:
				return new NetworkStreamFragments();
				
			case 3:	
				return new CloudPlayerFragment();
				
			case 4:
				return new KollosalCameraFragment();
				
			default :
				return SuperAwesomeCardFragment.newInstance(position);
		}
				
	}
}
