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



import java.io.File;
import java.util.ArrayList;


public class VideoItem {
	
	//path for video.....
	//comes in use for getting child video path....
	private String videoPath;
	
	//folder name to display....
	private String folderDisplayName;

	//child list of videos inside the folder.....
	private ArrayList<VideoItem> child_Videos;
	
	//number of child videos inside folder....
	private int number_of_child_video;
	
	//true then folder contains new video.....
	private boolean folder_has_new_video;
	
	//true then child video is new video and is not watched....
	private boolean video_is_new;
	
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
		number_of_child_video = 0;
		
		folder_has_new_video = false;
		video_is_new = false;
	}
	
	/**
	 * 
	 * @return total number of videos in current folder....
	 */	
	public int getTotalChildVideos(){
		return number_of_child_video;
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
	 * sets the video to new video....
	 */
	public void setNewVideoFlag(){
		this.video_is_new = true;
	}
	
	/**
	 * removes the new video tag from the video....
	 */
	public void removeNewVideoFlag(){
		this.video_is_new = false;
	}
	
	/**
	 * 
	 * @return true if folder contains new videos....
	 */
	public boolean isFolderHasNewVideos(){
		for(VideoItem itm : child_Videos){
			if(itm.isVideoNew())
				return (this.folder_has_new_video = true);
		}
		return this.folder_has_new_video;
	}
	
	/**
	 * 
	 * @return true if this video is new.....
	 */
	public boolean isVideoNew(){
		return this.video_is_new;
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addVideo(VideoItem item) {
		number_of_child_video++ ;
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
