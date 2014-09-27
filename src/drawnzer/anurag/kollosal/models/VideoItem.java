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

import java.io.File;

import android.graphics.Bitmap;

public class VideoItem {

	
	private String videoPath;
	private String folderDisplayName;
	private Bitmap thumb;
	public VideoItem(String path , Bitmap bitmap) {
		// TODO Auto-generated constructor stub
		this.videoPath = path;
		this.folderDisplayName = new File(new File(this.videoPath).getParent()).getName();
		this.thumb = bitmap;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDisplayName(){
		return this.folderDisplayName;
	}
	
	/**
	 * 
	 * @return
	 */
	public Bitmap getThumbnail(){
		return this.thumb;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getVideoPath(){
		return this.videoPath;
	}
	
}
