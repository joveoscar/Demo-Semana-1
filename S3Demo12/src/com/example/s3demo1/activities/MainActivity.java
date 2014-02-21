package com.example.s3demo1.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.s3demo1.R;
import com.example.s3demo1.data.Helper;
import com.example.s3demo1.data.Image;
import com.example.s3demo1.data.ImageAdapter;
import com.example.s3demo1.fragments.PhotoDialogFragment;
import com.example.s3demo1.fragments.PhotoDialogFragment.NoticeDialogListener;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends FragmentActivity implements OnClickListener, NoticeDialogListener{

	Button btnPhoto;
	Button btnUpdate;
	Button btnParse;
	ImageAdapter adapter;
	ArrayList<Image> imagesArray;
	public static RequestQueue requestQueue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "EmJE6iJLpr4MMG5Sl6hy1r7vNK3RkZ6u6PZ3OhrH", "eKqWTJrKPYuztFlunHFWhvSsNODZDHEGchI8vla7");
		
		setContentView(R.layout.activity_main);
		requestQueue = Volley.newRequestQueue(this);
		
		GridView gridView = (GridView)findViewById(R.id.grid);
		imagesArray = new ArrayList<Image>();
		adapter = new ImageAdapter(this, imagesArray);
		gridView.setAdapter(adapter);
		
		btnPhoto = (Button)findViewById(R.id.btnPhoto);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		btnParse = (Button)findViewById(R.id.btnParse);
		btnPhoto.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnParse.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == btnPhoto.getId())
		{
			new PhotoDialogFragment().show(getSupportFragmentManager(), "");
		}
		else if(v.getId() == btnUpdate.getId())
		{
			APICall();
		}
		else if(v.getId() == btnParse.getId())
		{
			parse();
		}
	}
	
	private void parse()
	{
		ParseObject test = new ParseObject("Prueba");
		test.put("nombre", "Jose");
		test.saveInBackground();
		Log.e("TAG", "Guardando...");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Prueba");
		query.getInBackground("eXw556fGW4", new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject obj, ParseException arg1)
			{
				if(obj != null)
				{
					Toast.makeText(getApplicationContext(), obj.getString("nombre"), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void showNotification()
	{
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle(getString(R.string.txt_notif_title)).setContentText(getString(R.string.txt_notif_subtitle));
		
		Intent result = new Intent(this, CameraActivity.class);
		
		TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
		stackBuilder.addParentStack(CameraActivity.class);
		stackBuilder.addNextIntent(result);
		
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		builder.setContentIntent(resultPendingIntent);
		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, builder.build());
	}

	private void APICall() {
		String url = Helper.getRecentMediaUrl("guatemala");
		findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		btnUpdate.setEnabled(false);
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response)
			{
				findViewById(R.id.progressBar).setVisibility(View.GONE);
				findViewById(R.id.grid).setVisibility(View.VISIBLE);
				btnUpdate.setEnabled(true);
				//Log.e("Respuesta", response.toString());
				JSONArray data;
				try
				{
					data = response.getJSONArray("data");
					for(int i = 0; i < data.length(); i++)
					{
						JSONObject element = data.getJSONObject(i);
						String type = element.getString("type");
						if(type.equals("image"))
						{
							JSONObject user = element.getJSONObject("user");
							JSONObject images = element.getJSONObject("images");
							JSONObject standardResolution = images.getJSONObject("standard_resolution");
							
							String userName = user.getString("username");
							String imgUrl = standardResolution.getString("url");
							
							Image image = new Image();
							image.setImgUrl(imgUrl);
							image.setUserName(userName);
							imagesArray.add(image);
									
						}
					}
					
					adapter.notifyDataSetChanged();
					showNotification();
				}
				catch(JSONException e)
				{
					Log.e("ERROR", Log.getStackTraceString(e));
				}
			}
			
		};
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, null);
		requestQueue.add(request);
	}

	@Override
	public void onDialogPositiveClick() {
		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);
		
	}

	@Override
	public void onDialogNegativeClick() {
		Toast.makeText(this, "Hizo click en No", Toast.LENGTH_SHORT).show();
		
	}
}
