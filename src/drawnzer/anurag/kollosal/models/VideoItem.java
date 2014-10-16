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

import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
public class VideoItem {
	
	private String videoPath;
	private String folderDisplayName;
	private Bitmap thumb;
	private ArrayList<VideoItem> child_Videos;
	
	/**
	 * 
	 * @param path path for video.....
	 * @param ctx
	 * @param load_thumb if true loads the thumb....
	 */
	public VideoItem(File path , Context ctx , boolean load_thumb) {
		// TODO Auto-generated constructor stub
		this.child_Videos = new ArrayList<VideoItem>();
		this.videoPath = path.getAbsolutePath();
		this.folderDisplayName = path.getParentFile().getName();
		if(load_thumb)
			this.thumb = makeThumb(path.getAbsolutePath(), ctx);
		else
			this.thumb = null;
	}
	
	/**
	 * THIS FUNCTION GENERATES A THUMBNAIL OF THE VIDEO...
	 * THIS FUNCTION USES VITAMIO LIBRARY INSTEAD OF ANDROID PROVIDED
	 * FUNCTION..... 
	 * @param path
	 * @return
	 */
	private Bitmap makeThumb(String path , Context ctx) {
		// TODO Auto-generated method stub
		//MediaMetadataRetriever ret = new MediaMetadataRetriever();
		//ret.setDataSource(path);
		Bitmap map;// = ret.getFrameAtTime(10000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
		map = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(ctx,
               path, MediaStore.Video.Thumbnails.MINI_KIND), 500, 500);
		return map;
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
	
	/**
	 * 
	 * @param item
	 */
	public void addVideo(VideoItem item) {
		this.child_Videos.add(item);
	}
	
}
