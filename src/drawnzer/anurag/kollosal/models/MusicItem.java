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

package drawnzer.anurag.kollosal.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

/**
 * 
 * @author Anurag....
 *
 */
public class MusicItem {

	private String dislayName;
	private String musicPath;
	private MediaMetadataRetriever retreive;
	private Bitmap art;
	private String artist;
	private String album;
	public MusicItem(String name , String path) {
		// TODO Auto-generated constructor stub
		this.dislayName = name;
		this.musicPath = path;
		this.retreive = new MediaMetadataRetriever();
		this.retreive.setDataSource(musicPath);
		
		//getting the actual song name....
		try{
			this.dislayName = retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		}catch(Exception e){
			this.dislayName = name;
		}
		
		//extracting the album art....
		try{
			byte[] data = this.retreive.getEmbeddedPicture();
			this.art = BitmapFactory.decodeByteArray(data, 0, data.length);
		}catch(Exception e){
			this.art = null;
		}
		
		//extracting artist name....
		try{
			artist = this.retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			if(artist.length() == 0)
				artist = null;
		}catch(Exception e){
			artist = null;
		}
		
		//extracting album name....
		try{
			album = this.retreive.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
			if(album.length() == 0)
				album = null;
		}catch(Exception e){
			album = null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDisplayName(){
		return this.dislayName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPath(){
		return this.musicPath;
	}
	
	/**
	 * 
	 * @return
	 */
	public Bitmap getArt(){
		return this.art;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getArtistName(){
		return this.artist;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAlbumName(){
		return this.album;
	}
}
