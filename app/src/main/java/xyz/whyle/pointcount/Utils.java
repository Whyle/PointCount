package xyz.whyle.pointcount;

import android.app.*;
import android.content.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.animation.*;
import android.widget.*;
import com.google.android.gms.common.*;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.maps.model.*;
import java.io.*;
import java.text.*;
import java.util.*;
import okhttp3.*;
import org.json.*;
import xyz.whyle.pointcount.*;
import xyz.whyle.pointcount.app.*;

public class Utils
{
	public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
	public static final String URL = "http://www.whyle.xyz/youth/";
	public static final String GET = URL + "Get.php"; 
	public static final String ADD = URL + "Add.php";
	public static final String NewPerson = URL + "NewPerson.php";
	public static final String GetPeopleList = URL + "GetPeopleList.php";
	public static final String EditProfile = URL + "EditProfile.php";
	public static final String DATAJSON = "data";
	
	
	public Context mContext;

	

	public Utils(Context mContext)
	{
		this.mContext = mContext;
	}

	private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
	
	/**
	 * GET date Dialog
	 * @getDate(Edittext)
	 * @return data with edittext
	 */

	public static void getDate(final EditText et)
	{
		final Calendar c = getCalendar(et.getText().toString().trim());
		new DatePickerDialog(et.getContext(), new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker p1, int p2, int p3, int p4)
				{
					c.set(p2, p3, p4);
					et.setText(sdf.format(c.getTime()));
				}
			}, c.get(Calendar.YEAR), 
			c.get(Calendar.MONTH), 
			c.get(Calendar.DAY_OF_MONTH)
		).show();
	}

	/**
	 * GET Date
	 * @getCalendar(String)
	 * @return Calender
	 */
	public static Calendar getCalendar(String str)
	{
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(sdf.parse(str));
			return cal;
		}
		catch (ParseException e)
		{
			cal.set(Calendar.YEAR, 1999);
			return cal;
		}
	}

	/**
	 * GET boolean
	 * @isThisDateValid(String,String)
	 * @return boolean
	 */
	public static boolean isThisDateValid(String dateToValidate){

		if(dateToValidate == null){
			return false;
		}

		SimpleDateFormat sdf2 = (SimpleDateFormat) sdf.clone();
		sdf2.setLenient(false);

		try {

			//if not valid, it will throw ParseException
			Date date = sdf2.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * GET map
	 * @initMap(Activity)
	 * @return activity's onActivityResult
	 */
	public static void initMap(Activity mActivity)
	{
		try
		{
			Intent intent =
				new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
				.setBoundsBias(new LatLngBounds(// Padua
								   new LatLng(45.162064, 11.538806),
								   new LatLng(45.613010, 12.178044)))
				.build(mActivity);
			mActivity.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
		}
		catch (GooglePlayServicesRepairableException e)
		{
			// TODO:Handle the error.
			System.out.println(e);
		}
		catch (GooglePlayServicesNotAvailableException e)
		{
			// TODO:Handle the error.
			System.out.println(e);
		}
	}

	/**
	 * OkHttp3
	 * get请求
	 */
	public static String Get(String url) throws IOException {
		/**
		 * 创建一个Request
		 */
		Request request = new Request.Builder()
            .url(url)
            .build();
		Response response = App.client.newCall(request).execute();
		return response.body().string();
	}
	
	/**
	 * OkHttp3
	 * post请求
	 */
	public static String Post(String url, RequestBody form) throws IOException {
		Request request = new Request.Builder()
			.url(url)
			.post(form)
			.build();
		Response response = App.client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		return response.body().string();
	}
	
	
	
	
	
	public static int getStateFromJson(String json)
	{
		if (BuildConfig.DEBUG) Log.i("getStateFromJson()" , "jsonDecode: " + json);
		try
		{
			JSONObject jsonObject = new JSONObject(String.valueOf(json));
			return Integer.valueOf(jsonObject.get("state"));
		}
		catch (JSONException e)
		{
			System.out.println(e);
			return 0;
		}
		// State 1 = putPerson Done
		// State -1 = putPerson Falied
		// state 0 = error app
		//if (BuildConfig.DEBUG) Log.i(TAG, "jsonDecodeState: " + state);
		//Toast.makeText(mActivity, "Result:" + state, Toast.LENGTH_SHORT).show();
	}
	
	
	
	
	
	/**
	 *@author chenzheng_Java 
	 *保存用户输入的内容到文件
	 */
	public static void savetojson(Context c, String str, String group) {

		String content = str;
		try {
			/* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的，
			 * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的
			 *   public abstract FileOutputStream openFileOutput(String name, int mode)
        	 *   throws FileNotFoundException;
			 * openFileOutput(String name, int mode);
			 * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名
			 *  		该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt
			 * 第二个参数，代表文件的操作模式
			 * 			MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖
			 * 			MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件
			 * 			MODE_WORLD_READABLE 公用  可读
			 * 			MODE_WORLD_WRITEABLE 公用 可读写
			 *  */
			FileOutputStream outputStream = c.openFileOutput(DATAJSON + group,
														   Activity.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.flush();
			outputStream.close();
			Toast.makeText(c, "成功", Toast.LENGTH_LONG).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author chenzheng_java 
	 * 读取刚才用户保存的内容
	 */
	public static String readfromjson(Context c, String group) {
		try {
			FileInputStream inputStream = c.openFileInput(DATAJSON+group);
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());
			return content;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Animation for list
	public static void runLayoutAnimation(final RecyclerView recyclerView) {
		final Context context = recyclerView.getContext();
		final LayoutAnimationController controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

		recyclerView.setLayoutAnimation(controller);
		recyclerView.getAdapter().notifyDataSetChanged();
		recyclerView.scheduleLayoutAnimation();
	}
	
	
	

	public static RequestBody getFrom(String s)
	{
		RequestBody bodys = new FormBody.Builder()
			.add("username", s)
			.build();
		return bodys;
	}

//	public static ArrayList<MyData> decodeJson(String msg)
//	{
//		Log.i("decodeJson()", "-------->Start");
//		Log.i("decodeJson()", "-------->Start with value:" + msg);
//		String[] msgs = msg.split("§");
//		ArrayList<MyData> array = new ArrayList<>();
//		Log.i("decodeJson()", "-------->(1) with value:" + msgs[1]);
//		try
//		{
//
//			for (int i = 0; i < msgs.length; i++)
//			{
//				Log.i("Utils", msgs[i]);
//				MyData data = new MyData();
//				JSONObject json = new JSONObject(msgs[i]);
//				data.setGroup1(json.getString("a"));
//				data.setGroup2(json.getString("b"));
//				data.setGroup3(json.getString("c"));
//
//				data.setFrom(json.getString("from"));
//				data.setTime(json.getString("time"));
//
//				array.add(data);
//				Log.i("Utils", "test " + data.getGroup1());
//			}
////			JSONObject json = new JSONObject(msgs[msgs.length - 1]);
////			MyData data = new MyData();
////			data.setGroup1(json.getString("a"));
////			data.setGroup2(json.getString("b"));
////			data.setGroup3(json.getString("c"));
////			array.add(data);
//		}
//		catch (JSONException e)
//		{
//			Log.e("utils", e.toString());
//		}
//		return array;
//	}


}
