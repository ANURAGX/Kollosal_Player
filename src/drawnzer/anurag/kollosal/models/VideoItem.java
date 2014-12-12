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
import java.util.ArrayList;

public class VideoItem {
	
	private String videoPath;
	private String folderDisplayName;

	private ArrayList<VideoItem> child_Videos;
	
	/**
	 * 
	 * @param path path for video.....
	 * @param ctx
	 * @param load_thumb if true loads the thumb....
	 */
	public VideoItem(File path , boolean isFolder) {
		// TODO Auto-generated constructor stub
		this.child_Videos = new ArrayList<VideoItem>();
		this.videoPath = path.getAbsolutePath();
		if(isFolder)
			this.folderDisplayName = path.getParentFile().getName();
		else
			this.folderDisplayName = path.getName();
		
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
	public String getVideoPath(){
		return this.videoPath;
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addVideo(VideoItem item) {
		this.child_Videos.add(item);
	}
	
	/**
	 * 
	 * @return child videos within parent folder....
	 */
	public ArrayList<VideoItem> getChildVideos(){
		return child_Videos;
	}
	
}
