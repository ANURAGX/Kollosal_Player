/**
 * Copyright(c) 2015 DRAWNZER.ORG PROJECTS -> ANURAG
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
 *                            anuraxsharma1512@gmail.com
 *
 */

package drawnzer.anurag.kollosal;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
	
	/**
	 * 
	 * @param ctx
	 * @param dp to convert to px....
	 * @return the converted dp into px....
	 */
	public static int convert_dp_to_px(Context ctx , int dp){
		Resources res = ctx.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, res.getDisplayMetrics());
		return (int)px; 
	}

}
