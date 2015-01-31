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

package drawnzer.anurag.kollosal.models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;


/**
 * 
 * @author Anurag....
 *
 */
public class TimeLine {

	private static String DB_NAME = "TIMELINE_DB.db";
	private String TABLE_NAME = "TIME_LINE_TABLE";
	private TimeLineDB db;
	private Bitmap preview;
	private Context context;
	
	public TimeLine(Context ctx) {
		// TODO Auto-generated constructor stub
		this.db = new TimeLineDB(ctx);
		this.context = ctx;
	}
	
	/**
	 * THIS FUNCTION RETURNS THE PATH OF THE TIMELINE VIDEO....
	 * @return
	 */
	public String getTimeLinePath(){
		Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME, null);
		if(cursor != null){
			String path = cursor.getString(cursor.getColumnIndex("ITEM"));
			return path;
		}
		return null;
	}
	
	/**
	 * DELETED THE SELECTED TIME LINE VIDE.....
	 * @param path
	 */
	public void deleteItem(){
		this.db.deleteItem();
	}
	
	/**
	 * PUTS A VIDEO IN TIMELINE DB...
	 * @param path
	 */
	public void addItem(String path){
		this.db.addItem(path);
	}
	
	/**
	 * CHECKS WHETHER TIME LINE VIDEO IS SELECTED OR NOT
	 * @return -1 FOR NO VIDEO....
	 */
	public int isVideoAdded(){
		return this.db.getCount();
	}
	
	/**
	 * GENERATES AND RETURNS THE PREVIEW OF THE TIME LINE VIDEO....
	 * @param path
	 * @return
	 */
	public Bitmap getVideoPreview(String path){
		//preview = ThumbnailUtils.createVideoThumbnail(context, path, MediaStore.Video.Thumbnails.MICRO_KIND);
		if(preview !=null)
			return preview;
		return null;
	}
	
	/**
	 * THIS IS DB HELPER CLASS....
	 * @author Anurag....
	 *
	 */
	private class TimeLineDB extends SQLiteOpenHelper{

		public TimeLineDB(Context ctx) {
			// TODO Auto-generated constructor stub
			super(ctx, DB_NAME, null, 1);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE "+TABLE_NAME +
					"(ITEM TEXT PRIMARY KEY);");
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+";");
			onCreate(db);
		}
		
		/**
		 * FUNCTIN TO ADD A TIME LINE VIDEO TO DB....
		 * @param path
		 */
		private void addItem(String path){
			ContentValues values = new ContentValues();
			values.put("ITEM", path);
			SQLiteDatabase db = this.getWritableDatabase();
			db.insert(TABLE_NAME, null, values);
		}
		
		/**
		 * FUNCTION TO DELETE TIME LINE VIDEO FROM RECORD....
		 * @param path
		 */
		private void deleteItem(){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_NAME, null, null);
		}
		
		/**
		 * THIS FUNCTION IS USED TO DETERMINE WHETHER TIME LINE VIDEO IS SELECTED
		 * OR NOT....
		 * @return
		 */
		private int getCount(){
			Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME,null);
			if(cursor !=null){
				int count = cursor.getCount();
				cursor.close();
				return count;
			}
			return -1;
		}
	}
	
}
