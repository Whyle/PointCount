package xyz.whyle.pointcount.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.animated.BuildConfig;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import xyz.whyle.pointcount.MyData;
import xyz.whyle.pointcount.R;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.adapter.RecyclerViewAdapterGroup1;
import xyz.whyle.pointcount.app.App;
import xyz.whyle.pointcount.server.EditProfile;
import xyz.whyle.pointcount.server.PersonDetail;
import xyz.whyle.pointcount.server.ServerData;
import xyz.whyle.pointcount.avtivity.HomeActivity;


public class Group1 extends Fragment
{
	public static final String TAG = "Group1 ";
    public static View myView;
	public static RecyclerView mRecyclerView;
	public static ArrayList<MyData.Data> data = new ArrayList<>();
	public static RecyclerViewAdapterGroup1 adapter;
	public static SwipeRefreshLayout swipeRefreshLayout;
	public static Context mContext;

	public static Group1 newInstance(Context c) {
		Log.d(TAG, "newInstance");
		Bundle args = new Bundle();
		args.putLong("EXTRA_DATE", 0);
		
		Group1 fragment = new Group1(c);
		fragment.setArguments(args);
		return fragment;
	}
	
	public Group1(){}
    public Group1(Context mContext)
	{ this.mContext = mContext;
		if (BuildConfig.DEBUG) Log.i(TAG + "Group1()", "START");
	}

	// 0 = decode json and return MyDayete objct.
	//-1 = haven't connect internet.
	// Receved from a Thread.
	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 1:
					// Set List of View (from list)
					adapter.setData( ServerData.jsonDecode((String)msg.obj).getData());
					// Animation of item
					Utils.runLayoutAnimation(mRecyclerView);
					break;
				case -1:
					Toast.makeText(mContext, "No internet!", Toast.LENGTH_SHORT).show();
					break;
				
				case 31:
					App.sendToast("1");
					break;
				case -31:
					App.sendToast("-1");
					break;
			}
			// Close Refresh.
			if (swipeRefreshLayout != null)
			    swipeRefreshLayout.setRefreshing(false);
		}
	};

	// Get json(string group)
	// Send message to Handler
	public static void init()
	{
		swipeRefreshLayout.setRefreshing(true);
		if (BuildConfig.DEBUG) Log.i(TAG + "init()", "START");
		Runnable get = new Runnable(){
			@Override
			public void run()
			{
				String json = ServerData.getFromGroup(ServerData.GROUP1);
				if (BuildConfig.DEBUG) Log.i(TAG + "init()", "json: " + json);
				Message msg = new Message();
				msg.what = App.is(mContext) ? 1 : -1;
				msg.obj = json;
				handler.sendMessage(msg);
			}
		};
		Thread thread = new Thread(get);
		thread.start();
	}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (BuildConfig.DEBUG) Log.i(TAG + "onCreateView()", "START " + mContext);
		
		
		myView = inflater.inflate(R.layout.fragment_tab1, container, false);
        mRecyclerView = (RecyclerView)myView.findViewById(R.id.my_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		adapter = new RecyclerViewAdapterGroup1();
		mRecyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.id_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
												   android.R.color.holo_green_light,
												   android.R.color.holo_blue_light, android.R.color.holo_red_light);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

				@Override
				public void onRefresh()
				{
				    Log.i(TAG + "onCreateView()", "start refresh.");
					init();
				}
			});
		init();
        adapter.setOnItemClickListener(new RecyclerViewAdapterGroup1.OnItemClickListener() {

				@Override
				public void onItemLongClick(final View view, final xyz.whyle.pointcount.MyData.Data data)
				{
					// TODO: Implement this method
					EditProfile edit = new EditProfile(mContext, data).create(view, handler);
					
				}

				@Override
				public void onItemClick(View view, MyData.Data array)
				{
					Log.i(TAG + "onCreateView()", "item onclick");
					
					new PersonDetail(mContext, array).show();
					
				}
			});
		return myView;
    }


public boolean time(){
	Calendar now = Calendar.getInstance();
	Calendar dt = Calendar.getInstance();
	dt.setTimeInMillis(System.currentTimeMillis());
	return dt.after(now);
}



}
