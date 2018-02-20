package xyz.whyle.pointcount.app;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import java.util.*;
import okhttp3.*;
import xyz.whyle.pointcount.*;

public class App extends Application
{

	public static SharedPreferences share;

	public static ArrayList<MyData> data;

	public static OkHttpClient client;
	public static Handler mHandler;
	public static Request request;

	public static String body = "";
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i("APP", "------->Is start");
		
		client = new OkHttpClient.Builder().build();
		mHandler = new Handler(Looper.getMainLooper());
		Log.i("APP", "------->client:" + client);
		Log.i("APP", "------->handler:" + mHandler);
		
		share = getSharedPreferences("user", MODE_PRIVATE);
	}
	
	public static OkHttpClient connectServer(String url, RequestBody post)
	{
		if (url == null && post == null && mHandler == null)
			return null;
			
		Log.i("APP", "------->Start Get From:" + url);
		Log.i("APP", "------->Post:" + post);
		
		request = new Request.Builder()
			.url(url)
			.post(post)
			.build();
		
		return client;
	}
	
	

	// Open Google Map
	public static void openMap(Context mContext, String position)
	{
		Uri uri = Uri.parse("http://maps.google.com/?q=" + position);
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setData(uri);
		mContext.startActivity(intent);
		Log.i("APP", "------->openMap():" + "opend!");
	}
	

}
