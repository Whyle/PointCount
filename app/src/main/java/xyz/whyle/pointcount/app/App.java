package xyz.whyle.pointcount.app;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import java.util.*;
import okhttp3.*;
import xyz.whyle.pointcount.*;
import android.content.SharedPreferences.*;
import android.widget.Toast;

public class App extends Application
{
	public static SharedPreferences share;

	public static ArrayList<MyData> data;

	public static OkHttpClient client;
	public static Handler mHandler;
	public static Request request;

	public static String body = "";

	public static SharedPreferences.Editor editor;

	public static Context mContext;
	@Override
	public void onCreate()
	{
		super.onCreate();
		this.mContext = this;
		Log.i("APP", "------->Is start");
		
		client = new OkHttpClient.Builder().build();
		mHandler = new Handler(Looper.getMainLooper());
		Log.i("APP", "------->client:" + client);
		Log.i("APP", "------->handler:" + mHandler);
		
		share = getSharedPreferences("AppSetting", MODE_PRIVATE);
		editor = share.edit();
	}
	
	
	public static String getUser()
	{
		return share.getString("user","");
	}
	public static void setUser(String str)
	{
		editor.putString("user",str).commit();
	}
	
	
	
	
	public static boolean is(Context  context)
	{ 
		//是否有网络连接
		if (context != null)
		{ 
			ConnectivityManager 
				mConnectivityManager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null)
			{ 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
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
	
	public static int getThemeAccentColor(Context context) {
		int colorAttr;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			colorAttr = android.R.attr.colorAccent;
		} else {
			//Get colorAccent defined for AppCompat
			colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
		}
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(colorAttr, outValue, true);
		return outValue.data;
	}
	
	public static void sendToast(String msg)
	{
		Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
	}
}
