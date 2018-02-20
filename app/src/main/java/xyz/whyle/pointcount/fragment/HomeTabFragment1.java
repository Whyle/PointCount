package xyz.whyle.pointcount.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.whyle.pointcount.R;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.adapter.RecyclerViewAdapter;
import xyz.whyle.pointcount.app.App;
import java.util.ArrayList;
import xyz.whyle.pointcount.MyData;

public class HomeTabFragment1 extends Fragment{

    private static final String ARG_PARAM1 = "tab1";
    private View myView;
    private TextView textView;
	String[] myDataset = {"1","2","3"};
	
	private RecyclerView mRecyclerView;

	private LinearLayoutManager mLayoutManager;

    public static HomeTabFragment1 newInstance(String param1) {
        HomeTabFragment1 fragment = new HomeTabFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    public HomeTabFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
								 
		// Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_tab1, container, false);
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
		
	    ArrayList<MyData> mDataset = Utils.decodeJson(App.body);
		RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(mDataset);
		mRecyclerView.setAdapter(mAdapter);
		
        // specify an adapter (see also next example)
        
        return myView;
    }
	


}
