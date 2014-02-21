package com.example.s3demo1.data;

public class Helper {
	public final static String INSTAGRAM_API_KEY = "447c33ee9ccf41dcabb95f44a4c385ef";
	public final static String BASE_API_URL = "https://api.instagram.com/v1";
	
	public static String getRecentMediaUrl(String tag)
	{
		return BASE_API_URL + "/tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_API_KEY;
	}

}
