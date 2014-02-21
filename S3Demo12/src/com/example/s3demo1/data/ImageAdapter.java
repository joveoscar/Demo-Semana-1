package com.example.s3demo1.data;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.s3demo1.BitmapLRUCache;
import com.example.s3demo1.R;
import com.example.s3demo1.activities.MainActivity;

public class ImageAdapter extends BaseAdapter
{
	private ImageLoader imageLoader;
	private LayoutInflater inflater;
	private ArrayList<Image> dataArray;
	
	public ImageAdapter(Context context, ArrayList<Image> dataArray)
	{
		this.dataArray = dataArray;
		this.inflater = LayoutInflater.from(context);
		this.imageLoader = new ImageLoader(MainActivity.requestQueue, new BitmapLRUCache());
	}
	
	@Override
	public int getCount() {
		return dataArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Image current = dataArray.get(position);
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.grid_image, null);
			
			holder = new ViewHolder();
			
			holder.txtName = (TextView)convertView.findViewById(R.id.txtName);
			holder.imgFlag = (NetworkImageView)convertView.findViewById(R.id.imageFlag);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		
		holder.txtName.setText(current.getUserName());
		holder.imgFlag.setImageUrl(current.getImgUrl(), imageLoader);
		return convertView;
	}
	
	static class ViewHolder
	{
		public TextView txtName;
		public NetworkImageView imgFlag;
		
	}
	
	//http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	//Si es igual
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
	{
	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	//No es igual a la del video del demo pero al parecer es equivalente:
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth)
	    {
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
	        {
	        	inSampleSize *= 2;
	        }
	    }
	    return inSampleSize;
	}

}
