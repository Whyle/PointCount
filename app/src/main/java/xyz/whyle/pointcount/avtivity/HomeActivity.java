package xyz.whyle.pointcount.avtivity;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RadioGroup.*;
import java.util.*;
import xyz.whyle.pointcount.*;
import xyz.whyle.pointcount.adapter.*;
import xyz.whyle.pointcount.base.*;
import xyz.whyle.pointcount.fragment.*;
import xyz.whyle.pointcount.viewmodule.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import xyz.whyle.pointcount.viewmodule.CircleImageView;

public class HomeActivity extends BaseActivity
{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private CircleImageView currentUserAvater;
    private TextView currentUserName;
    private TextView currentUserSignature;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static HomeViewPagerAdapter adapter;

	public String s;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

	}

	public void addPerson()
	{
		// Get layout
		View layout = LayoutInflater.from(this).inflate(R.layout.add_person, null);
		final EditText name = (EditText)layout.findViewById(R.id.add_person_name);
		final RadioGroup sex  = (RadioGroup)layout.findViewById(R.id.add_person_sex);
		final EditText born = (EditText) layout.findViewById(R.id.add_person_born);
		final EditText phone = (EditText)layout.findViewById(R.id.add_person_phone);
		final RadioGroup group = (RadioGroup) layout.findViewById(R.id.add_person_group);
		final EditText address = (EditText)layout.findViewById(R.id.add_person_address);
		//address.setRawInputType(Configuration.KEYBOARD_QWERTY);
		address.setRawInputType(Configuration.KEYBOARD_QWERTY);
		// Select date
		born.setOnFocusChangeListener(new OnFocusChangeListener(){

				@Override
				public void onFocusChange(View p1, boolean p2)
				{
					// TODO: Implement this method
					String text = born.getText().toString();
					if (text.isEmpty()&&p2)
					{
						Calendar c = Calendar.getInstance();
						new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener(){

								@Override
								public void onDateSet(DatePicker p1, int p2, int p3, int p4)
								{
									born.setText(p4 + "/" + p3 + "/" + p2);
									born.clearFocus();
								}
							}, 2000, c.get(Calendar.MONTH), c.get(c.DAY_OF_MONTH)).show();
					}
				}
			});

		// Create dialog
		AlertDialog add = new AlertDialog.Builder(this)
			.setTitle("Add new person.")
			.setView(layout)
			.setCancelable(false)
			.setPositiveButton("Add", null)
			.setNegativeButton("Cancel", null)
			.show();
		add.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener(){
				boolean hasError = false;
				String sex_text;
				String group_text;
				@Override
				public void onClick(View p1)
				{

					String name_text = name.getText().toString();
					String born_text = born.getText().toString();
					String phone_text = phone.getText().toString();
					String address_text = address.getText().toString();

					if (name_text.length() >= 2) // if it has 2 words
					{
						name.setError("Invalied Text");
						hasError = true;
					}

					if (born_text.isEmpty())
					{
						born.setError("Please inserit date");
						hasError = true;
					}

					if (!born_text.contains("/") && born_text.length() > 10 &&born_text.length() < 8)
					{
						born.setError("Error data Format");
						hasError = true;
					}

					if (phone_text.isEmpty())
					{
						phone.setError("Please inserit phone number!");
						hasError = true;
					}

					if (phone_text.trim().length() < 10 && phone_text.trim().length() >= 14)
					{
						phone.setError("Please inserit correct phone number!");
						hasError = true;
					}

					sex.setOnCheckedChangeListener(new OnCheckedChangeListener(){

							@Override
							public void onCheckedChanged(RadioGroup p1, int p2)
							{
								if (p2 == R.id.add_person_sex_m)
								{
									sex_text = "M";
								}
								else
								{
									sex_text = "F";
								}
							}
						});
					group.setOnCheckedChangeListener(new OnCheckedChangeListener(){


							@Override
							public void onCheckedChanged(RadioGroup p1, int p2)
							{
								if (p2 == R.id.add_person_group_1)
								{
									group_text = "1";
								}
								else if (p2 == R.id.add_person_group_2)
								{
									group_text = "2";
								}
								else
								{
									group_text = "3";
								}
							}
						});
					if (hasError)
						return;
					// upload files

				}
			});
	}

    @Override
    public void initContentView()
	{
        setContentView(R.layout.activity_home);
		addPerson();
    }

    @Override
    public void initViews()
	{

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);


		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{

					AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
					dialog.setTitle("Add Point");
					View layout = LayoutInflater.from(HomeActivity.this).inflate(R.layout.point, null);
					dialog.setMessage("Please Type number of Points.");
					dialog.setView(layout);
					final EditText a = (EditText) layout.findViewById(R.id.a);
					final EditText b = (EditText) layout.findViewById(R.id.b);
					final EditText c = (EditText) layout.findViewById(R.id.c);
					dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method  
								String aa = a.getText().toString().trim();
								String bb = b.getText().toString().trim();
								String cc = c.getText().toString().trim();

								aa = (aa.isEmpty() ? "0" : aa);
								bb = (bb.isEmpty() ? "0" : bb);
								cc = (cc.isEmpty() ? "0" : cc);

								//	put(Integer.valueOf(aa), Integer.valueOf(bb), Integer.valueOf(cc));
							}
						});
					dialog.setNegativeButton("Cancel", null);
					dialog.setCancelable(false);
					dialog.show();
				}
			});
        /**
         * init DrawLayout
         */
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // 设置NavigationView中menu的item被选中后要执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);

        /**
         * 当前用户信息
         */
        View navheaderView = mNavigationView.getHeaderView(0);  //获取头部布局

        currentUserAvater = (CircleImageView) navheaderView.findViewById(R.id.current_userAvater);
        currentUserName = (TextView) navheaderView.findViewById(R.id.current_userName);
        currentUserSignature = (TextView) navheaderView.findViewById(R.id.current_userSignature);

    }

    @Override
    public void initListeners()
	{

	}

    @Override
    public void initData()
	{

        currentUserAvater.setImageResource(R.drawable.default_avater);
        currentUserName.setText("Admin");
        currentUserSignature.setText("平静温和地前进");
		if (viewPager != null)
		{
			setupViewPager(viewPager); // set View
        }
	}



    /**
     * 设置ViewPager+Fragment
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager)
	{
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new HomeTabFragment1().newInstance("Page1"), "第一组");
        adapter.addFragment(new HomeTabFragment2().newInstance("Page2"), "第二组");
        adapter.addFragment(new HomeTabFragment3().newInstance("Page3"), "第三组");
	    viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }



    /**
     * 设置NavigationView中menu的item被选中后要执行的操作
     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav)
	{
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem)
				{

					String msgString = "";

					switch (menuItem.getItemId())
					{

							// do your business here eg:
							// case R.id.nav_menu_home:
							//    msgString = (String) menuItem.getTitle();
							//     break;

					}

					// Menu item点击后选中，并关闭Drawerlayout
					menuItem.setChecked(true);
					mDrawerLayout.closeDrawers();
					return true;
				}
			});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
		{
			invalidata("admin");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
