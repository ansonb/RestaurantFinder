

package com.codingc.team66.restofinder;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Object;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.codingc.team66.adapters.CuisinesAdapter;
import com.codingc.team66.adapters.ParticularRestaurantAdapter;
import com.codingc.team66.adapters.RestaurantAdapter;
import com.codingc.team66.database.CuisineDbAdapter;
import com.codingc.team66.database.DbFunctions;
import com.codingc.team66.database.ParticularRestaurantDbAdapter;
import com.codingc.team66.database.RestaurantDbAdapter;
import com.codingc.team66.helpers.AppStatus;
import com.codingc.team66.helpers.Constants;
import com.codingc.team66.models.CuisinesModel;
import com.codingc.team66.models.ParticularRestaurantModel;
import com.codingc.team66.models.RestaurantsModel;
import com.codingc.team66.parsers.CuisinesParser;
import com.codingc.team66.parsers.ParticularRestaurantParser;
import com.codingc.team66.parsers.RestaurantParser;
import com.codingc.team66.restservices.RestClient;
import com.codingc.team66.restofinder.ParticularRestaurantActivity;
import com.codingc.team66.restofinder.R;
import com.google.android.gms.common.images.ImageManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestInfoActivity extends Activity {
	AppStatus appStatus;
	String RestaurantId;
	String Restaurantaddr;
	String Restaurantname;
	String strJsonReponse;
	String phoneNo;
	String photo;
	JSONObject jobj;
	WebView restview;
	WebViewClient wvcobject;
    
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.restinfo);
		Intent intent=getIntent();

		RestaurantId=(String)intent.getSerializableExtra("RestaurantId");
		Restaurantaddr=(String)intent.getSerializableExtra("Restaurantaddr");
		Restaurantname=(String)intent.getSerializableExtra("Restaurantname");

		// To display name of the restaurant
        TextView name=(TextView)findViewById(R.id.label);
        name.setText(Restaurantname);
        
        restview=(WebView)findViewById(R.id.restaurantphoto);
        
		

		appStatus = AppStatus.getInstance(this);
		
		intent=getIntent();
		if (appStatus.isOnline(RestInfoActivity.this))
		{
			Log.v("RestInfo_SCREEN", "App is Online");
			// To display webview
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>(0);
				params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

				strJsonReponse = RestClient.getInstance(this).doApiCall("restaurant.json/"+RestaurantId, "GET", params);
	            jobj=new JSONObject(strJsonReponse);
				Log.d("RestInfo's details...",String.valueOf(strJsonReponse));
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				/*JSONObject Photo=jobj.getJSONObject("photos");
				photo=Photo.getString("photos_url").toString();
				Log.d("photo_url",photo);*/
				
				photo=jobj.getString("url").toString();
				
				WebSettings wbset=restview.getSettings();
			    wbset.setJavaScriptEnabled(true);
			    restview.setWebViewClient(new WebViewClient());
			    restview.getSettings().setJavaScriptEnabled(true);
			    restview.loadUrl(photo);
			}catch(Exception e){
				Log.d("photo_url exception",photo);
			}

		}
		else
		{
			Log.v("RestInfo_SCREEN", "App is Offline");
			TextView message=(TextView)findViewById(R.id.label1);
			message.setText("App not connected to internet.Check Internet settings");
		}
		
		
	
	}
   
	
	// To return to previous page in webview 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(restview.canGoBack()){
                	restview.goBack();
                }else{
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    
}
