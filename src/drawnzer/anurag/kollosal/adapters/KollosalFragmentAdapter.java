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

import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import drawnzer.anurag.kollosal.fragments.NetworkStreamFragments;
import drawnzer.anurag.kollosal.fragments.VideoFragment;

/**
 * 
 * @author Anurag....
 *
 */
public class KollosalFragmentAdapter extends FragmentStatePagerAdapter{

	private final static String[] TITLES = { "VIDEOS", "NETWORK STREAM"};
	private static HashMap<String, Fragment> fragments;
	
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
		if(fragments == null)
			fragments = new HashMap<String , Fragment>();
		switch(position){
			case 0:	
					{
						Fragment frgmt = fragments.get(""+position);	
						if(frgmt == null){
							VideoFragment vidFrgmt = new VideoFragment();
							fragments.put(""+position, vidFrgmt);
							return vidFrgmt;
						}
						return frgmt;
					}				
					
			case 1:
					{
						Fragment frgmt = fragments.get(""+position);	
						if(frgmt == null){
							NetworkStreamFragments netFrgmt = new NetworkStreamFragments();
							fragments.put(""+position, netFrgmt);
							return netFrgmt;
						}
						return frgmt;
					}
		}
		return null;		
	}
}
