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

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * 
 * @author Anurag....
 *
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,
	MediaPlayer.OnErrorListener {

	private boolean isPLaying;
	private boolean isUIOpen;
	private int PLAYING_COMPLETED = 0;
	private int PAUSE = 1;
	private int NEXT = 2;
	private int PREVIOUS = 3;
	private int SHUFFLE = 4;
	private int LOOP = 5;
	private int ERROR = 6;
	private int SEEKBAR_MAX = 7;
	private Serve serve;
	private Handler handle;
	private MediaPlayer player;	
	private String ACTION;	
	private String PATH;
	
	public class LocalBinder extends Binder{
		public MusicService getService(){
			return MusicService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new LocalBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		serve = new Serve();
		//serve.setPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		super.onCreate();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		ACTION = intent.getAction();
		if(ACTION.equalsIgnoreCase("play")){
			PATH = intent.getData().toString();			
		}
		return Service.START_STICKY;		
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void setHandler(final Handler handler){
		this.handle = handler;
	}
	
	private void prepare(){
		try {
			if(player == null)
				player = new MediaPlayer();
			player.setDataSource(PATH);
			player.prepare();
			player.setOnCompletionListener(this);
			player.setOnErrorListener(this);
			player.start();
			Message msg = new Message();
			msg.what = SEEKBAR_MAX;
			msg.obj = player.getDuration();
			handle.sendMessage(msg);
			isPLaying = true;
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play(){
		serve.start();
	}
	
	public void seekTo(int progress){
		serve.seekTo(progress);
	}
	
	/**
	 * 
	 *
	 */
	private class Serve extends Thread{
		
		public Serve() {
			// TODO Auto-generated constructor stub
		}		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			prepare();
			handle.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					handle.sendEmptyMessage(player.getCurrentPosition());
					if(isPLaying)
						handle.postDelayed(this, 1000);
				}
			}, 1000);	
		}

		public void seekTo(int prog){
			player.seekTo(prog);
		}
		
		@Override
		public void interrupt() {
			// TODO Auto-generated method stub
			player.release();
			player = null;
		}		
	}
	
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		isPLaying = false;
		this.handle.sendEmptyMessage(PLAYING_COMPLETED);
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		this.handle.sendEmptyMessage(ERROR);
		return false;
	}	
}
