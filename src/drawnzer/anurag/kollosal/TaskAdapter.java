package drawnzer.anurag.kollosal;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TaskAdapter extends FragmentPagerAdapter{

	private final String[] TITLES = { "VIDEOS", "MUSICS", "NETWORK STREAM", "CLOUD PLAYER", ""
			+ "CAMERA", "YOU TUBE",};
	
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
		return SuperAwesomeCardFragment.newInstance(position);
	}
}
