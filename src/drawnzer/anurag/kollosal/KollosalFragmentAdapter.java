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

package drawnzer.anurag.kollosal;

import drawnzer.anurag.kollosal.fragments.CloudPlayerFragment;
import drawnzer.anurag.kollosal.fragments.KollosalCameraFragment;
import drawnzer.anurag.kollosal.fragments.NetworkStreamFragments;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class KollosalFragmentAdapter extends FragmentPagerAdapter{

	private final String[] TITLES = { "VIDEOS", "MUSIC", "NETWORK STREAM", "CLOUD PLAYER", ""
			+ "SHARE  CAMERA", "YOU TUBE"};
	
	public KollosalFragmentAdapter(FragmentManager fm) {
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
