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

package drawnzer.anurag.kollosal.utils;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import drawnzer.anurag.kollosal.models.VideoItem;

public class LoadVideoFromSDUtils {
	
	public static HashMap<String, VideoItem> VIDEO_LIST;
	
	public static void prepare(Context ctx){
		VIDEO_LIST = new HashMap<String , VideoItem>();
		Cursor cursor = ctx.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				null,
				null,
				null,
				null);
		while(cursor.moveToNext()){
			String PATH = cursor.getColumnName(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			
			
		}
		cursor.close();
	}
}
