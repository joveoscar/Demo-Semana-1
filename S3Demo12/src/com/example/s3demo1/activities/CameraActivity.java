package com.example.s3demo1.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.s3demo1.R;

public class CameraActivity extends Activity implements OnClickListener{
	
	private static final int LOAD_IMAGE = 1;
	private static final int CAMERA = 2;
	
	Button btnFromCamera;
	Button btnFromGallery;
	String photoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		btnFromCamera = (Button)findViewById(R.id.btnFromCamera);
		btnFromGallery = (Button)findViewById(R.id.btnFromGallery);
		btnFromCamera.setOnClickListener(this);
		btnFromGallery.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode)
		{
			case CAMERA:
				if(resultCode == RESULT_OK)
				{
					fromCamera(data);
				}
				break;
			case LOAD_IMAGE:
				if(resultCode == RESULT_OK)
				{
					fromGallery(data);
				}
				break;
		}
		
	}
	
	//Documentacion oficial
	//http://stackoverflow.com/questions/10413659/how-to-resize-image-in-android
	public Bitmap resizeBitmap(int targetW, int targetH)
	{
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(photoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    int scaleFactor = 1;
	    if ((targetW > 0) || (targetH > 0)) {
	            scaleFactor = Math.min(photoW/targetW, photoH/targetH);        
	    }

	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    return BitmapFactory.decodeFile(photoPath, bmOptions);            
	}
	
	public void fromCamera(Intent data)
	{
		ImageView img = (ImageView)findViewById(R.id.image);
		Bitmap bitmap = resizeBitmap(img.getWidth(), img.getHeight());
		img.setImageBitmap(bitmap);
		
		Intent mediaScan = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File file = new File(photoPath);
		Uri contentUri = Uri.fromFile(file);
		mediaScan.setData(contentUri);
		sendBroadcast(mediaScan);
	}
	
	public void fromGallery(Intent data)
	{
		if(data != null)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};
			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			
			if(cursor.moveToFirst())
			{
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				ImageView img = (ImageView)findViewById(R.id.image);
				img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		int code = 0;
		if(v.getId() == btnFromCamera.getId())
		{
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File photo = setupFile();
			photoPath = photo.getAbsolutePath();
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			code = CAMERA;
		}
		else if(v.getId() == btnFromGallery.getId())
		{
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			code = LOAD_IMAGE;
		}
		
		startActivityForResult(intent, code);
		
	}

	private File setupFile() {
		File albumDir;
		String albumName = "Ejemplo";
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
		{
			albumDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
		}
		else
		{
			albumDir = new File(Environment.getExternalStorageDirectory() + "/dcim/" + albumName);
		}
		
		albumDir.mkdirs();
		
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmSS", Locale.getDefault()).format(Calendar.getInstance().getTime());
		
		String imageFileName = "IMG_" + timeStamp + ".jpg";
		File image = new File(albumDir + "/" + imageFileName);
		
		return image;
	}

}
