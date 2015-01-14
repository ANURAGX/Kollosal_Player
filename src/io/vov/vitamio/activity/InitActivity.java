/*
 * Copyright (C) 2013 YIXIA.COM
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
 */

package io.vov.vitamio.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import io.vov.vitamio.Vitamio;


public class InitActivity extends Activity {
  public static final String FROM_ME = "fromVitamioInitActivity";
  private ProgressDialog mPD;
  //private UIHandler uiHandler;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    getWindow().getAttributes().alpha = 0.0f;
   // uiHandler = new UIHandler(this);

    new AsyncTask<Object, Object, Boolean>() {
      @Override
      protected void onPreExecute() {
        mPD = new ProgressDialog(InitActivity.this);
        mPD.setCancelable(false);
        mPD.setMessage(InitActivity.this.getString(getResources().getIdentifier("vitamio_init_decoders", "string", getPackageName())));
        mPD.show();
      }

      @Override
      protected Boolean doInBackground(Object... params) {
        return Vitamio.initialize(InitActivity.this, getResources().getIdentifier("libarm", "raw", getPackageName()));
      }

      @Override
      protected void onPostExecute(Boolean inited) {
    	  mPD.dismiss();
    	  InitActivity.this.finish();
    	  if (!inited) {
    		  //failed to initialize the decoders.... 
    		  Toast.makeText(InitActivity.this,
        			  InitActivity.this.getString(getResources().getIdentifier("failedToInitLibs", "string", getPackageName())), 
        			  Toast.LENGTH_SHORT).show();
    	  }
      }
    }.execute();
  } 
}
