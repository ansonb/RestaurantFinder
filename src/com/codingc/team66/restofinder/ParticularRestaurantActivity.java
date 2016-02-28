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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
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

public class ParticularRestaurantActivity extends Activity {
	AppStatus appStatus;
	String RestaurantId;
	String RestaurantLat;
	String RestaurantLng;
	String Restaurantaddr;
	String Restaurantname;
	String Restaurantrating="0";
	String strJsonReponse;
	String phoneNo;
	String photo;
	float newrating;
	int upvoteno=0;
	int downvoteno=0;
	boolean upvoted=false;
	boolean downvoted=false;
	int ratingcount=1;
	JSONObject jobj;

	ArrayList<ParticularRestaurantModel> particularrestaurantArray;
	DbFunctions dbFunctions = new DbFunctions(ParticularRestaurantActivity.this);

	ListView list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.particular_restaurant);
		Intent intent=getIntent();

		RestaurantId=(String)intent.getSerializableExtra("RestaurantId");
		RestaurantLat=(String)intent.getSerializableExtra("RestaurantLat");
		RestaurantLng=(String)intent.getSerializableExtra("RestaurantLng");
		Restaurantaddr=(String)intent.getSerializableExtra("Restaurantaddr");
		Restaurantname=(String)intent.getSerializableExtra("Restaurantname");
		Restaurantrating=(String)intent.getSerializableExtra("Restaurantrating");

        // To display name of the restaurant
		TextView namedisplay;
		namedisplay=(TextView)findViewById(R.id.label0);
		namedisplay.setText(Restaurantname);
		
		
		final EditText input=(EditText)findViewById(R.id.etreview);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		
		final EditText mailinput=(EditText)findViewById(R.id.etmail);
    	mailinput.setInputType(InputType.TYPE_CLASS_TEXT);


   	    final RatingBar ratingbar;
    	ratingbar=(RatingBar)findViewById(R.id.ratingBar1);
    	ratingbar.setRating(Float.parseFloat(Restaurantrating));

    	final TextView ratingno;
    	ratingno=(TextView)findViewById(R.id.textView3);


    	newrating=Float.parseFloat(Restaurantrating);

		// To show location in google maps
    	final Button buttonmap = (Button) findViewById(R.id.button4);
		buttonmap.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v) {
		    	Intent browserintent=new Intent("android.intent.action.VIEW",Uri.parse("http://maps.google.com/?q="+Restaurantname+", "+Restaurantaddr));
		        startActivity(browserintent);
		    }

		});

		appStatus = AppStatus.getInstance(this);
		intent=getIntent();
		if (appStatus.isOnline(ParticularRestaurantActivity.this))
		{
			Log.v("ParticularRestaurant_SCREEN", "App is Online");
			//cityId=(String)intent.getSerializableExtra("cityId");
			getParticularRestaurantsInfo();

			
		}
		else
		{
			Log.v("Restaurant_SCREEN", "App is Offline");
			try{
			ParticularRestaurantDbAdapter mdbAdapter = new ParticularRestaurantDbAdapter(this, Constants.strParticularRestaurantTableName);
			particularrestaurantArray  = mdbAdapter.getParticularRestaurantModels();
			displayList();
			}catch(Exception e){
				
			}
		}
		
		
		// Setting initial ratings count
		try{
			ratingcount=(jobj.optInt("count",1));
			}catch(Exception e){
				e.printStackTrace();
				Log.d("count","couldn't obtain count");
			}

			ratingno.setText(String.valueOf(ratingcount)+" ratings");

			// To go to restaurant's web page
			final Button buttonurl = (Button) findViewById(R.id.button5);
			buttonurl.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	try{
			    	Intent browserintent1=new Intent("android.intent.action.VIEW",Uri.parse(jobj.getString("url").toString()));
			        startActivity(browserintent1);
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			    }

			});

		   //  To give reviews on the restaurant
			final Button buttonreview = (Button) findViewById(R.id.button6);
			buttonreview.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	TextView reviewdisplay;
			    	reviewdisplay=(TextView)findViewById(R.id.textView4);
			    	String s=input.getText().toString();
			    	reviewdisplay.setText("you:-\n"+s);
			    }

			});
			
			// To recommend to a friend
			final Button buttonrecon = (Button) findViewById(R.id.buttonmail);
			buttonrecon.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	String s=mailinput.getText().toString();
			    	Intent i = new Intent(Intent.ACTION_SEND);
			    	i.setType("message/rfc822");
			    	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{s});
			    	i.putExtra(Intent.EXTRA_SUBJECT, "me ,restaurant recommendation");
			    	i.putExtra(Intent.EXTRA_TEXT   , "check out "+Restaurantname+Restaurantaddr);
			    	try {
			    	    startActivity(Intent.createChooser(i, "Send mail..."));
			    	} catch (android.content.ActivityNotFoundException ex) {
			    	    Toast.makeText(ParticularRestaurantActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			    	}
			    }
				
			});

			// To display contacts 
			TextView contactdisplay;
			contactdisplay=(TextView)findViewById(R.id.textView5);
			try{
			phoneNo=jobj.getString("phone").toString();	
			contactdisplay.setText("contacts: "+jobj.getString("phone").toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			// To go to dialler
			contactdisplay.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	Log.d("phone no:",phoneNo);
			    	Intent dial = new Intent();
			    	dial.setAction("android.intent.action.DIAL");
			    	dial.setData(Uri.parse("tel:"+phoneNo));
			    	startActivity(dial);
			    }

			});
			
			
            // To display discounts if any
			TextView discountdisplay;
			discountdisplay=(TextView)findViewById(R.id.discountoffer);
			try{
				if(jobj.getJSONObject("discounts")!=null){
				  JSONObject jsonobject=jobj.getJSONObject("discounts");	
				  discountdisplay.setText("Offers/Discounts:- "+jsonobject.getString("summary").toString());
				}
				}catch(Exception e){
				e.printStackTrace();
			}

			final Button buttonupvote = (Button) findViewById(R.id.button1);
			final Button buttondownvote = (Button) findViewById(R.id.button2);
			
			// to up vote
			buttonupvote.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	if(!(upvoted==true)&&downvoted==true){
			    		downvoteno--;
			    	    downvoted=false;
			    	}
			    	else if(!(upvoted==true)&&downvoted==false){
			    		upvoteno++;
			    		upvoted=true;
			    	}
			    	
			    	if(upvoteno==1){
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup_green);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown);
			    	}
			    	else if(downvoteno==1){
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown_red);
			    	}
			    	else {
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown);
			    	}
			    }
			});

			// To down vote
			buttondownvote.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	if(!(downvoted==true)&&upvoted==true){
			    		upvoteno--;
			    	    upvoted=false;
			    	}
			    	else if(!(downvoted==true)&&upvoted==false){
			    		downvoteno++;
			    		downvoted=true;
			    	}
			    	
			    	if(upvoteno==1){
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup_green);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown);
			    	}
			    	else if(downvoteno==1){
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown_red);
			    	}
			    	else {
			    		buttonupvote.setBackgroundResource(R.drawable.thumbsup);
			    		buttondownvote.setBackgroundResource(R.drawable.thumbsdown);
			    	}
			    }
			});

			// To submit and compute rating
			final Button buttonrating = (Button) findViewById(R.id.button3);
			buttonrating.setOnClickListener(new View.OnClickListener(){
			    public void onClick(View v) {
			    	newrating=(newrating*ratingcount +ratingbar.getRating())/(ratingcount+1);
			    	ratingcount++;
			    	ratingbar.setRating(newrating);
			    	ratingno.setText(String.valueOf(ratingcount)+" ratings");
			    }
			});
		
	}

	private void displayList() {
		// TODO Auto-generated method stub
		Log.d("In displaylist","here");
		list = (ListView)findViewById(R.id.list);
		ParticularRestaurantAdapter adapter = new ParticularRestaurantAdapter(ParticularRestaurantActivity.this, particularrestaurantArray);
		dbFunctions.storeParticularRestaurantDataInDB(particularrestaurantArray);
		list.setAdapter(adapter);
	}

	private void getParticularRestaurantsInfo() {
		// TODO Auto-generated method stub

		ParticularRestaurantDbAdapter particularrestaurantDbAdapter = new ParticularRestaurantDbAdapter(this, Constants.strParticularRestaurantTableName);
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>(0);
			params.add(new BasicNameValuePair("apikey", Constants.AUTH_KEY));

			strJsonReponse = RestClient.getInstance(this).doApiCall("restaurant.json/"+RestaurantId, "GET", params);
            jobj=new JSONObject(strJsonReponse);
			Log.d("Particular Restaurant's details...",String.valueOf(strJsonReponse));


			ParticularRestaurantParser parser=new ParticularRestaurantParser();
			particularrestaurantArray = parser.parseParticularRestaurant(strJsonReponse);



			for(ParticularRestaurantModel r:particularrestaurantArray)
			{
				Log.d("Particular Restaurant: ", r.toString());
			}


			displayList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}





}
