package xyz.whyle.pointcount;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils
{
	public static final String URL = "http://www.whyle.xyz/data/";
	public static final String GET = URL + "Get.php"; 
	public static final String ADD = URL + "Add.php";
	public Context mContext;
	
	public Utils(Context mContext)
	{
		this.mContext = mContext;
	}
	
	public static RequestBody getFrom(String s)
	{
		RequestBody bodys = new FormBody.Builder()
			.add("username", s)
			.build();
		return bodys;
	}
	
	public static ArrayList<MyData> decodeJson(String msg)
	{
		Log.i("decodeJson()", "-------->Start");
		Log.i("decodeJson()", "-------->Start with value:" + msg);
		String[] msgs = msg.split("§");
		ArrayList<MyData> array = new ArrayList<>();
		Log.i("decodeJson()", "-------->(1) with value:" + msgs[1]);
		try
		{
			
			for (int i = 0; i < msgs.length; i++)
			{
				Log.i("Utils", msgs[i]);
				MyData data = new MyData();
				JSONObject json = new JSONObject(msgs[i]);
				data.setGroup1(json.getString("a"));
				data.setGroup2(json.getString("b"));
				data.setGroup3(json.getString("c"));

				data.setFrom(json.getString("from"));
				data.setTime(json.getString("time"));
				
				array.add(data);
				Log.i("Utils","test " + data.getGroup1());
			}
//			JSONObject json = new JSONObject(msgs[msgs.length - 1]);
//			MyData data = new MyData();
//			data.setGroup1(json.getString("a"));
//			data.setGroup2(json.getString("b"));
//			data.setGroup3(json.getString("c"));
//			array.add(data);
		}
		catch (JSONException e)
		{
			Log.e("utils", e.toString());
		}
		return array;
	}


}
