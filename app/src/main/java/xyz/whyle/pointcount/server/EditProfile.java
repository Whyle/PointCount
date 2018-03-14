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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.View.OnClickListener;
import android.content.DialogInterface;

public class EditProfile implements OnMenuItemClickListener
{
	public static final int MODE_CHANGE_GROUP = 0;
	
	public static final int MODE_DELETE_PERSON = 1;
	
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
			case R.id.item_change_group:
				selectGroup();
				break;
			case R.id.item_delete_person:
				
				break;
				
			case R.id.set_group_1:
				Thread(MODE_CHANGE_GROUP, "1");
				break;
			case R.id.set_group_2:
				Thread(MODE_CHANGE_GROUP, "2");
				break;
			case R.id.set_group_3:
				Thread(MODE_CHANGE_GROUP, "3");
				break;
			case R.id.set_group_0:
				Thread(MODE_CHANGE_GROUP, "0");
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
	
	public void confermDelete()
	{
		AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
		delete.setTitle("Delete " + user.getName() + "?");
		delete.setPositiveButton("yes", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					Thread(MODE_DELETE_PERSON,null);
				}
			});
		delete.setNegativeButton("no", null);
		delete.show();
	}

	/*******************************
	 *                             *
	 * Server                      *
	 *                             *
	 *******************************/
	
	// an Thread
	// resend messages with Handler
	public void Thread(final int mode, final String group)
	{
		Runnable run = new Runnable(){

			@Override
			public void run()
			{
				String json = "";
				switch(mode)
				{
					case 0: //change group
						json = changeGroup(user.getName(), group);
						break;
					case 1: //delete
						json = delete(user.getName());
						break;
				}
				Message msg = new Message();
				msg.what = App.is(mContext) ? Utils.getStateFromJson(json) : -1;
				msg.obj = user;
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
			msg2.what = msg.what;
			mHandler.sendMessage(msg2);
		}
	};
	
	public static String changeGroup(String name, String group)
	{
		try
		{
			final RequestBody bodys = new FormBody.Builder()
				.add("group", group)
				.add("name", name)
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
	
	public static String delete(String name)
	{
		try
		{
			final RequestBody bodys = new FormBody.Builder()
				.add("name", name)
				.add("mode", "delete")
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
