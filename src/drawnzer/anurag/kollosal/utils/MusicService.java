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


import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
/**
 * 
 * @author Anurag....
 *
 */
public class MusicService extends Service implements 
				MediaPlayer.OnCompletionListener,
				MediaPlayer.OnErrorListener,
				Handler.Callback {

	
	
	MediaPlayer mplayer;
	private NotificationManager mNotificationManager;
	private AudioManager mAudioManager;
	
	private WakeLock wakeLock;
	
	public InCallListener inCallListener;
	
	private Receiver mRec;
	
	/**
	 * If set, music will play.
	 */
	public static final int FLAG_PLAYING = 0x1;
	
	
	private Looper mLooper;
	
	private Handler mHandler;
	
	/**
	 * Action for startService: toggle playback on/off.
	 */
	public static final String ACTION_TOGGLE_PLAYBACK = "drawnzer.anurag.kollosal.action.TOGGLE_PLAYBACK";
	
	/**
	 * Action for startService: start playback if paused.
	 */
	public static final String ACTION_PLAY = "drawnzer.anurag.kollosal.action.PLAY";
	/**
	 * Action for startService: pause playback if playing.
	 */
	public static final String ACTION_PAUSE = "drawnzer.anurag.kollosal.action.PAUSE";
	
	/**
	 * Action for startService: toggle playback on/off.
	 *
	 * Unlike {@link PlaybackService#ACTION_TOGGLE_PLAYBACK}, the toggle does
	 * not occur immediately. Instead, it is delayed so that if two of these
	 * actions are received within 400 ms, the playback activity is opened
	 * instead.
	 */
	public static final String ACTION_TOGGLE_PLAYBACK_DELAYED = "drawnzer.anurag.kollosal.action.TOGGLE_PLAYBACK_DELAYED";
	/**
	 * Action for startService: toggle playback on/off.
	 *
	 * This works the same way as ACTION_PLAY_PAUSE but prevents the notification
	 * from being hidden regardless of notification visibility settings.
	 */
	public static final String ACTION_TOGGLE_PLAYBACK_NOTIFICATION = "drawnzer.anurag.kollosal.action.TOGGLE_PLAYBACK_NOTIFICATION";
	/**
	 * Action for startService: advance to the next song.
	 */
	public static final String ACTION_NEXT_SONG = "drawnzer.anurag.kollosal.action.NEXT_SONG";
	/**
	 * Action for startService: advance to the next song.
	 *
	 * Unlike {@link PlaybackService#ACTION_NEXT_SONG}, the toggle does
	 * not occur immediately. Instead, it is delayed so that if two of these
	 * actions are received within 400 ms, the playback activity is opened
	 * instead.
	 */
	public static final String ACTION_NEXT_SONG_DELAYED = "drawnzer.anurag.kollosal.action.NEXT_SONG_DELAYED";
	/**
	 * Action for startService: advance to the next song.
	 *
	 * Like ACTION_NEXT_SONG, but starts playing automatically if paused
	 * when this is called.
	 */
	public static final String ACTION_NEXT_SONG_AUTOPLAY = "drawnzer.anurag.kollosal.action.NEXT_SONG_AUTOPLAY";
	/**
	 * Action for startService: go back to the previous song.
	 */
	public static final String ACTION_PREVIOUS_SONG = "drawnzer.anurag.kollosal.action..PREVIOUS_SONG";
	/**
	 * Action for startService: go back to the previous song.
	 *
	 * Like ACTION_PREVIOUS_SONG, but starts playing automatically if paused
	 * when this is called.
	 */
	public static final String ACTION_PREVIOUS_SONG_AUTOPLAY = "drawnzer.anurag.kollosal.action.PREVIOUS_SONG_AUTOPLAY";
	/**
	 * Change the shuffle mode.
	 */
	public static final String ACTION_CYCLE_SHUFFLE = "drawnzer.anurag.kollosal.CYCLE_SHUFFLE";
	/**
	 * Change the repeat mode.
	 */
	public static final String ACTION_CYCLE_REPEAT = "drawnzer.anurag.kollosal.CYCLE_REPEAT";
	/**
	 * Pause music and hide the notifcation.
	 */
	public static final String ACTION_CLOSE_NOTIFICATION = "drawnzer.anurag.kollosal.CLOSE_NOTIFICATION";

	public static final int NEVER = 0;
	public static final int WHEN_PLAYING = 1;
	public static final int ALWAYS = 2;

	/**
	 * Notification click action: open LaunchActivity.
	 */
	private static final int NOT_ACTION_MAIN_ACTIVITY = 0;
	/**
	 * Notification click action: open MiniPlaybackActivity.
	 */
	private static final int NOT_ACTION_MINI_ACTIVITY = 1;
	/**
	 * Notification click action: skip to next song.
	 */
	private static final int NOT_ACTION_NEXT_SONG = 2;

	/**
	 * If a user action is triggered within this time (in ms) after the
	 * idle time fade-out occurs, playback will be resumed.
	 */
	private static final long IDLE_GRACE_PERIOD = 60000;
	
	/**
	 * If true, the notification should not be hidden when pausing regardless
	 * of user settings.
	 */
	private boolean mForceNotificationVisible;
	
	/**
	 * The PlaybackService state, indicating if the service is playing,
	 * repeating, etc.
	 *
	 * The format of this is 0b00000000_00000000_00000000f_feeedcba,
	 * where each bit is:
	 *     a:   {@link PlaybackService#FLAG_PLAYING}
	 *     b:   {@link PlaybackService#FLAG_NO_MEDIA}
	 *     c:   {@link PlaybackService#FLAG_ERROR}
	 *     d:   {@link PlaybackService#FLAG_EMPTY_QUEUE}
	 *     eee: {@link PlaybackService#MASK_FINISH}
	 *     ff:  {@link PlaybackService#MASK_SHUFFLE}
	 */
	int mState;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		HandlerThread thread = new HandlerThread("MusicService", Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		
		mplayer = new MediaPlayer();
		mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mplayer.setOnErrorListener(this);
		mplayer.setOnCompletionListener(this);
		
		
		
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		PowerManager power = (PowerManager) getSystemService(POWER_SERVICE);
		wakeLock = power.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "KOLLOSAL_MUSIC_LOCK");
		
		try{
			inCallListener = new InCallListener();
			TelephonyManager teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			teleManager.listen(inCallListener, PhoneStateListener.LISTEN_CALL_STATE);
		}catch(SecurityException e){
			
		}
		
		mRec = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(mRec, filter);
		
		getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				true, mObserver);
		
		mLooper = thread.getLooper();
		mHandler = new Handler(mLooper, this);
	}
	
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent != null){
			String action = intent.getAction().toString();
			if(ACTION_TOGGLE_PLAYBACK.equals(action)){
				playPause();
			}else if(ACTION_TOGGLE_PLAYBACK_NOTIFICATION.equals(action)){
				mForceNotificationVisible = true;
				synchronized (wakeLock) {
					if((mState & FLAG_PLAYING) !=0)
						pause();
					else 
						play();
				}
			}
		}
		return START_NOT_STICKY;
	}



	private void play() {
		// TODO Auto-generated method stub
		
	}



	private void pause() {
		// TODO Auto-generated method stub
		
	}



	private void playPause() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private class InCallListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
			case TelephonyManager.CALL_STATE_OFFHOOK: {
				
				break;
			}
			case TelephonyManager.CALL_STATE_IDLE: {
				
				break;
			}
		}
		}
	}
	
	private class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context content, Intent intent){
			String action = intent.getAction();
			if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(action)) {
				
			} else if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
				
			} else if (Intent.ACTION_SCREEN_ON.equals(action)) {
			
			}
		}
	}
	
	private final ContentObserver mObserver = new ContentObserver(null) {
		@Override
		public void onChange(boolean selfChange)
		{
			//update the song's list....
		}
	};
}
