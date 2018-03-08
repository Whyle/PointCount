// all function on sever
package xyz.whyle.pointcount.server;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import xyz.whyle.pointcount.MyData;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.app.App;

public class ServerData
{
	// get All people list
	public static final String GROUP0 = "0";
	public static final String GROUP1 = "1";
	public static final String GROUP2 = "2";
	public static final String GROUP3 = "3";

	private static String TAG;

	private static Gson gson;
	static{
		gson = new GsonBuilder().create();
	}

	// Grop
	public static String getFromGroup(String group)
	{
		try
		{
			final RequestBody bodys = new FormBody.Builder()
				.add("group", group)
				.add("user", App.getUser())
				.build();
			return Utils.Post(Utils.GetPeopleList, bodys);
		}
		catch (IOException e)
		{
			System.out.println(e);
			return "";
		}
	}
	
	
	
	/**
	 * 解析有数据头的纯数组
	 */
	

	public static MyData jsonDecode(String str)
	{
		Log.i("jsonDecode(0)","Json data: " + str);
		if(str.trim().isEmpty())
		   return new MyData();
		
		//GSON直接解析成对象
		MyData resultBean = new Gson().fromJson(str,MyData.class);
		Log.i("jsonDecode(test)", "group: " + resultBean.getGroup());
		return resultBean;
		
	}
	
	// Sleep for 2 second
	public void sleep()
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{}
	}

}
