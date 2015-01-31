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

package drawnzer.anurag.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * this class is quite opposite from its class name.....
 * this class contains all the video paths,if any how if provided video
 * path is not found from db then its new video.....
 * 
 * @author anurag
 *
 */
public class NewAddedVideos extends SQLiteOpenHelper{

	private static String DB = "NEW_VIDEOS.DB";
	
	private String TABLE_NAME = "NEW_VIDEOS_TABLE";
	
	public NewAddedVideos(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DB, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase DB) {
		// TODO Auto-generated method stub
		DB.execSQL("CREATE TABLE " + TABLE_NAME +""
				+ "(VID_PATH TEXT PRIMARY KEY);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param path of new added video and inserting in into the db.....
	 */
	public void addNewVideo(String path){
		ContentValues values = new ContentValues();
		values.put("VID_PATH", path);
		this.getWritableDatabase().insert(TABLE_NAME, null, values);
	}	

	
	/**
	 * 
	 * @param videoPath is checked whether it is new video or not.....
	 * @return 0 if new video....
	 */
	public int isVideoNew(String videoPath){
		Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + ""
				+ " WHERE VID_PATH = ?", new String[]{videoPath});
		int count = cursor.getCount();
		cursor.close();
		return count;
	}	
}
