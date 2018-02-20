package xyz.whyle.pointcount.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.adapter.HomeViewPagerAdapter;
import xyz.whyle.pointcount.app.App;
import xyz.whyle.pointcount.avtivity.HomeActivity;
import xyz.whyle.pointcount.fragment.HomeTabFragment1;
import xyz.whyle.pointcount.fragment.HomeTabFragment2;
import xyz.whyle.pointcount.fragment.HomeTabFragment3;


/**
 * Created by Crazyfzw on 2016/4/26.
 */
public abstract class BaseActivity extends AppCompatActivity
{

	public static final String TAG = "BaseActivity";
	OkHttpClient client;

	private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		initContentView();
		initViews();
		initListeners();
		invalidata("admin");
    }

	public void invalidata(String user)
	{
		Log.i(TAG + "invalidata()", "-------->Start invalidata with user:" + user);

		// Progress Dialog
		load = new ProgressDialog(this);
		load.setMessage("Getting Data");
		load.setCancelable(false);
		Log.i(TAG + "invalidata()", "-------->Dialog:" + load);

		App.connectServer(Utils.GET, Utils.getFrom(user)).newCall(App.request).enqueue(new Callback() 
			{
				@Override
				public void onFailure(Call call, final IOException e)
				{
					App.mHandler.post(new Runnable() {
							@Override
							public void run()
							{
								Log.i(TAG + "invalidata()", "------->Call falied:" + e.toString());
							}
						});		
				}
				
				@Override
				public void onResponse(Call call, final Response response)
				{
					App.mHandler.post(new Runnable() {

							@Override
							public void run()
							{
								try
								{
									App.body = response.body().string();
									initData();
									load.dismiss();
									Log.i(TAG + "invalidata()", "-------->Dialog:" + App.body);

								}
								catch (IOException e)
								{
									Log.i(TAG + "invalidata()", "------->Falied get string:" + e.toString());
								}
							}
						});			
				}
			});
	}

    /**
     * 加载布局文件
     */
    public abstract void initContentView();


    /**
     * 初始化布局文件中的控件
     */
    public abstract void initViews();


    /**
     * 初始化控件的监听
     */
    public abstract void initListeners();


    /** 进行数据初始化
     * initData
     */
    public abstract void initData();

//	public void put(int a, int b, int c)
//	{
//		RequestBody post = new FormBody.Builder()
//			.add("mode", "put")
//			.add("A", String.valueOf(a))
//		.add("B", String.valueOf(b))
//		.add("C", String.valueOf(c))
//			.build();
//        //开始请求，填入url，和表单
//        final Request request = new Request.Builder()
//			.url("http://www.whyle.xyz/points.php")
//			.post(post)
//			.build();
//
//        //客户端回调
//        client.newCall(request).enqueue(new Callback() {
//				@Override
//				public void onFailure(Call call, IOException e)
//				{
//					//失败的情况（一般是网络链接问题，服务器错误等）
//				}
//
//				@Override
//				public void onResponse(Call call, final Response response) throws IOException
//				{
//					//UI线程运行
//					runOnUiThread(new Runnable() {
//
//							@Override
//							public void run()
//							{
//
//								try
//								{
//									//临时变量（这是okhttp的一个锅，一次请求的response.body().string()只能用一次，否则就会报错）
//									String s = response.body().string();
//
//									//解析出后端返回的数据来
//									JSONObject jsonObject = new JSONObject(String.valueOf(s));
//									//retCode = jsonObject.getInt("success");
//									int code = jsonObject.getInt("code");
//									Toast.makeText(BaseActivity.this, "Retrun code: " + code,Toast.LENGTH_SHORT).show();
//									get("");
//								}
//								catch (JSONException e)
//								{
//									e.printStackTrace();
//								}
//								catch (IOException e)
//								{
//									e.printStackTrace();
//								}
//
//							}
//						});
//				}
//			});
//		
//	}
//	
//	public void get(String s)
//	{
//        //创建post表单，获取username和password（没有做非空判断）
//        RequestBody post = new FormBody.Builder()
//			.add("mode", "get")
//			.build();
//			
//        //开始请求，填入url，和表单
//        final Request request = new Request.Builder()
//			.url("http://www.whyle.xyz/points.php")
//			.post(post)
//			.build();
//
//        //客户端回调
//        client.newCall(request).enqueue(new Callback() {
//				@Override
//				public void onFailure(Call call, IOException e)
//				{
//					//失败的情况（一般是网络链接问题，服务器错误等）
//				}
//
//				@Override
//				public void onResponse(Call call, final Response response) throws IOException
//				{
//					//UI线程运行
//					runOnUiThread(new Runnable() {
//
//							@Override
//							public void run()
//							{
//
//								try
//								{
//									//临时变量（这是okhttp的一个锅，一次请求的response.body().string()只能用一次，否则就会报错）
//									String s = response.body().string();
//
//									//解析出后端返回的数据来
//									JSONObject jsonObject = new JSONObject(String.valueOf(s));
//									
//									int g1 = jsonObject.getInt("g1");
//									int g2 = jsonObject.getInt("g2");
//									int g3 = jsonObject.getInt("g3");
//									
//									HomeActivity.tabLayout.getTabAt(0).setText("A(" + g1 + ")");
//									HomeActivity.tabLayout.getTabAt(1).setText("B(" + g2 + ")");
//									HomeActivity.tabLayout.getTabAt(2).setText("C(" + g3 + ")");
//									
//								}
//								catch (JSONException e)
//								{
//									e.printStackTrace();
//								}
//								catch (IOException e)
//								{
//									e.printStackTrace();
//								}
//
//								
//
//
//							}
//						});
//				}
//			});
//		
	//}


}
