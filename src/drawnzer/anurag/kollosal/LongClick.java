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

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.LinearLayout;

/**
 * 
 * @author Anurag....
 *
 */
public class LongClick extends Activity implements View.OnClickListener{

	private boolean visible;
	private int color;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = getSharedPreferences("APP_SETTINGS", 0);
		color = prefs.getInt("SEMI_APP_COLOR", 0x66C74B46);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.long_click);
		visible = false;
		
		LinearLayout lin = (LinearLayout)findViewById(R.id.main_long);
		lin.setBackgroundColor(color);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
			case R.id.play_all:
					break;
					
			case R.id.delete:
					break;
					
			case R.id.share:
					break;
					
			case R.id.more:
					{
						visible = !visible;
						if(!visible){
							LinearLayout l1 = (LinearLayout)findViewById(R.id.row1);
							l1.setVisibility(View.GONE);
							LinearLayout l2 = (LinearLayout)findViewById(R.id.row2);
							l2.setVisibility(View.GONE);
						}else if(visible){
							LinearLayout l1 = (LinearLayout)findViewById(R.id.row1);
							l1.setVisibility(View.VISIBLE);
							LinearLayout l2 = (LinearLayout)findViewById(R.id.row2);
							l2.setVisibility(View.VISIBLE);
						}
					}
					break;
					
			case R.id.properties:
					break;
					
			case R.id.reload_thumb:
					break;
				
				
		}		
	}	
}
