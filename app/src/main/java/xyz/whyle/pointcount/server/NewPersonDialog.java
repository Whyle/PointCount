package xyz.whyle.pointcount.server;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.whyle.pointcount.BuildConfig;
import xyz.whyle.pointcount.R;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.app.App;
import xyz.whyle.pointcount.fragment.Group1;
import xyz.whyle.pointcount.fragment.Group2;
import xyz.whyle.pointcount.fragment.Group3;
import xyz.whyle.pointcount.fragment.Group4;
import android.app.AlertDialog.Builder;
import java.io.IOException;
import android.content.DialogInterface;
import android.widget.*;

public class NewPersonDialog implements OnTouchListener, OnClickListener
{


	public static String TAG = "NewPersonDiaolg";

	String currentGroup = "0";

	public static TextInputLayout address;

	private Context mContext;

	private View layout;

	private TextInputLayout name;

	private RadioGroup sex;

	private TextInputLayout birthday;

	private TextInputLayout phone;

	private RadioGroup group;

	private AlertDialog addNewPerson;

	private boolean hasError;

	private Handler mHandler;

	private int currentPage;

	private RadioButton group1;

	private RadioButton group3;

	private RadioButton group2;

	private RadioButton group0;

	public NewPersonDialog(Context mContext, Handler mHandler)
	{
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	public void create(int groupChecked)
	{
		this.currentPage = groupChecked;
		// init view
		layout = LayoutInflater.from(mContext).inflate(R.layout.add_person, null);
	    name = (TextInputLayout)layout.findViewById(R.id.add_person_name);
	    sex  = (RadioGroup)layout.findViewById(R.id.add_person_sex);
		birthday = (TextInputLayout) layout.findViewById(R.id.add_person_born);
		phone = (TextInputLayout)layout.findViewById(R.id.add_person_phone);
		group = (RadioGroup) layout.findViewById(R.id.add_person_group);
	    group1 = (RadioButton) layout.findViewById(R.id.add_person_group1);
		group2 = (RadioButton) layout.findViewById(R.id.add_person_group2);
		group3 = (RadioButton) layout.findViewById(R.id.add_person_group3);
		group0 = (RadioButton) layout.findViewById(R.id.add_person_groupnone);

		switch (groupChecked)
		{
			case 0:
				group1.setChecked(true);
				currentGroup = "1";
				break;
			case 1:
				group2.setChecked(true);
				currentGroup = "2";
				break;
			case 2:
				group3.setChecked(true);
				currentGroup = "3";
				break;
			case 3:
				group0.setChecked(true);
				currentGroup = "0";
				break;
			default:
				group1.setChecked(true);
				currentGroup = "1";
				break;

		}

		address = (TextInputLayout)layout.findViewById(R.id.add_person_address);


		// init setup
		name.setCounterEnabled(true);
		name.setErrorEnabled(true);
		birthday.setCounterEnabled(true);
		birthday.setCounterMaxLength(10);
		birthday.setErrorEnabled(true);
		phone.setCounterEnabled(true);
		phone.setCounterMaxLength(10);
		phone.setErrorEnabled(true);

		// init listner
		addTextWatcher(name);
		addTextWatcher(birthday);
		addTextWatcher(phone);
		birthday.getEditText().setId(1);
		address.getEditText().setId(2);
		birthday.getEditText().setOnTouchListener(this);
		address.getEditText().setOnTouchListener(this);

		// Dialog
		addNewPerson = new AlertDialog.Builder(mContext).create();
		addNewPerson.setTitle("增加新成员");
		addNewPerson.setIcon(R.drawable.account);
		addNewPerson.setView(layout);
		addNewPerson.setCancelable(false);
		addNewPerson.setButton("保存", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{

				}
			});
		addNewPerson.setButton2("关闭", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					addNewPerson.dismiss();
				}
			});
		addNewPerson.show();

		// Dialog listner
		addNewPerson.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);

	}

	public static void addTextWatcher(final TextInputLayout et)
	{
		et.getEditText().addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					et.setError("");
				}
			});
	}

	@Override
	public boolean onTouch(View p1, MotionEvent p2)
	{
		final int DRAWABLE_LEFT = 0;
		final int DRAWABLE_TOP = 1;
		final int DRAWABLE_RIGHT = 2;
		final int DRAWABLE_BOTTOM = 3;
		if (p2.getAction() == MotionEvent.ACTION_UP)
		{
			switch (p1.getId())
			{
				case 1: // Date
					EditText date = (EditText)p1;
					if (p2.getX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
					{
						date.requestFocus();
						if (Utils.isFastClick())
							Utils.getDate(date);
						if (BuildConfig.DEBUG) Log.i(TAG, "onTouch: " + date);
						return true;
					}
					break;

				case 2:// Adrress
					EditText address = (EditText)p1;
					if (p2.getX() >= (address.getRight() - address.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
					{
						p1.requestFocus();
						if (Utils.isFastClick())
							Utils.initMap((Activity)mContext);
						if (BuildConfig.DEBUG) Log.i(TAG, "onTouch: " + address);
						return true;
					}
					break;
			}
		}
		return false;
	}

	@Override
	public void onClick(View p1)
	{
		hasError = false;

		// get String
		String name_text = name.getEditText().getText().toString();
		String born_text = birthday.getEditText().getText().toString().trim();
		String phone_text = phone.getEditText().getText().toString();
		String address_text = address.getEditText().getText().toString();
		String sex_text = "M";
		String group_text = currentGroup;

		// controll for name
		if (name_text.isEmpty() || name_text.trim().length() <= 1)
		{
			name.setError("请输入正确的名字.");
			hasError = true;
		}

		// controll for birstday
		if (!Utils.isThisDateValid(born_text) || born_text.trim().length() != 10)
		{
			birthday.setError("请输入正确的格式.");
			hasError = true;
		}

		// controll for phone number
		if (phone_text.isEmpty() || phone_text.trim().length() != 10)
		{
			phone.setError("请输入正确的联系方式");
			hasError = true;
		}

		switch (sex.getCheckedRadioButtonId())
		{
			case R.id.add_person_sex_m:
				sex_text = "M"; break;
			case R.id.add_person_sex_f:
				sex_text = "F"; break;
		}

		switch (group.getCheckedRadioButtonId())
		{
			case R.id.add_person_group1:
				group_text = "1"; break;
			case R.id.add_person_group2:
				group_text = "2"; break;
			case R.id.add_person_group3:
				group_text = "3"; break;
			case R.id.add_person_groupnone:
				group_text = "0"; break;
		}

		if (hasError)
			return;

		RequestBody bodys = new FormBody.Builder()
			.add("name", name_text)
			.add("sex", sex_text) // M or F
			.add("birthday", born_text) // xx/xx/xxxx
			.add("phone", phone_text)
			.add("group", group_text) // 0-1
			.add("address", address_text)
			.add("user", App.getUser())
			.build();

		Thread(bodys);
	}


	public void Thread(final RequestBody body)
	{
		Runnable run = new Runnable(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				try
				{
					String json = Utils.Post(Utils.NewPerson, body);
					Message msg = new Message();// 21 -21
					msg.what = App.is(mContext) ? Utils.getStateFromJson(json) : -1;
					msg.obj = json; 
					mHandler.sendMessage(msg);
				}
				catch (IOException e)
				{}
			}
		};
		new Thread(run).start();
		addNewPerson.dismiss();
	}


}
