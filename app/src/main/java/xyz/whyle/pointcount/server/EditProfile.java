package xyz.whyle.pointcount.server;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import xyz.whyle.pointcount.MyData;
import xyz.whyle.pointcount.R;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.app.App;
import android.support.v7.widget.DropDownListView;
import xyz.whyle.pointcount.fragment.GroupBase;

public class EditProfile implements OnMenuItemClickListener
{
	public Context mContext;
	private View mView;

	private MyData.Data user;

	private Handler mHandler;
	
	
	public EditProfile(Context mContext, MyData.Data user)
	{
		this.mContext = mContext;
		this.user = user;
	}
	
	
	public EditProfile create(View view, Handler mHandler)
	{
		mView = view;
		this.mHandler = mHandler;
		PopupMenu mPM = new PopupMenu(mContext, view);
		mPM.setGravity(Gravity.RIGHT);
		mPM.inflate(R.menu.more_options);
		mPM.setOnMenuItemClickListener(this);
		mPM.show();
		return this;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem p1)
	{
		switch(p1.getItemId())
		{
			case R.id.move:
				selectGroup();
				break;
			
			case R.id.set_group_1:
				Thread("1");
				break;
			case R.id.set_group_2:
				Thread("2");
				break;
			case R.id.set_group_3:
				Thread("3");
				break;
			case R.id.set_group_0:
				Thread("0");
				break;
		}
		return false;
	}
	
	public void selectGroup()
	{
		PopupMenu mPM = new PopupMenu(mContext, mView);
		mPM.setGravity(Gravity.RIGHT);
		mPM.inflate(R.menu.more_option_group);
		mPM.setOnMenuItemClickListener(this);
		mPM.show();
		
	}
	
	/*******************************
	 *                             *
	 * Server                      *
	 *                             *
	 *******************************/
	
	// an Thread
	// resend messages with Handler
	public void Thread(final String group)
	{
		Runnable run = new Runnable(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				String json = changeGroup(user.getId(), group);
				Message msg = new Message();
				msg.what = App.is(mContext) ? Utils.getStateFromJson(json) : -1;
				msg.obj = json;
				reflesh.sendMessage(msg);
			}
		};
		new Thread(run).start();
	}
	
	public Handler reflesh = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			GroupBase.init();
			Message msg2 = mHandler.obtainMessage();
			msg2.what = msg.what;
			mHandler.sendMessage(msg2);
		}
	};
	
	public static String changeGroup(String id, String group)
	{
		try
		{
			final RequestBody bodys = new FormBody.Builder()
				.add("group", group)
				.add("id", id)
				.add("mode", "0")
				.add("user", App.getUser())
				.build();
			return Utils.Post(Utils.EditProfile, bodys);
		}
		catch (IOException e)
		{
			System.out.println(e);
			return "";
		}
	}
	
}
