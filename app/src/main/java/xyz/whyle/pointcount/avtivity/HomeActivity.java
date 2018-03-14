package xyz.whyle.pointcount.avtivity;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.gms.common.api.*;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.ui.*;
import xyz.whyle.pointcount.*;
import xyz.whyle.pointcount.adapter.*;
import xyz.whyle.pointcount.app.*;
import xyz.whyle.pointcount.base.*;
import xyz.whyle.pointcount.fragment.*;
import xyz.whyle.pointcount.server.*;
import xyz.whyle.pointcount.viewmodule.*;

import android.support.v7.widget.Toolbar;
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
	private TextInputLayout address;

	public static CoordinatorLayout rootLayout;

    @Override
    public void initContentView()
	{
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initViews()
	{

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.toolbar_layout);
        collapsingToolbar.setTitleEnabled(false);
		rootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setOffscreenPageLimit(5);
	
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					if (Utils.isFastClick()) {
						// 进行点击事件后的逻辑操作
						NewPersonDialog newPerson = new NewPersonDialog(HomeActivity.this, mHandler);
						newPerson.create(viewPager.getCurrentItem());
					}
					
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
        adapter.addFragment(new Group1(this).newInstance(this), "第一组");
        adapter.addFragment(new Group2(this).newInstance(this), "第二组");
        adapter.addFragment(new Group3(this).newInstance(this), "第三组");
	    adapter.addFragment(new Group4(this).newInstance(this), "未分组");
	    
		viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
	
	private static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch(msg.what)
			{
				case 0: // error this app
				    App.sendToast("0");
					break;
				case -21: // falied
				    App.sendToast("-1");
				    break;
				case 21: // done
				    App.sendToast("1");
					Group1.init();
					Group2.init();
					Group3.init();
					Group4.init();
					break;
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == Utils.PLACE_AUTOCOMPLETE_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				Place place = PlaceAutocomplete.getPlace(this, data);
				if (NewPersonDialog.address != null)
					NewPersonDialog.address.getEditText().setText(place.getAddress());
				Log.i(TAG, "Place: " + place.getName());
			}
			else if (resultCode == PlaceAutocomplete.RESULT_ERROR)
			{
				Status status = PlaceAutocomplete.getStatus(this, data);
				// TODO:Handle the error.
				Log.i(TAG, status.getStatusMessage());

			}
			else if (resultCode == RESULT_CANCELED)
			{
				// The user canceled the operation.
			}
		}
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
			Group1.init();
			//invalidata("admin");
			GroupBase.init();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
