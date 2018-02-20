package xyz.whyle.pointcount.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.whyle.pointcount.MyData;
import xyz.whyle.pointcount.R;
import xyz.whyle.pointcount.Utils;
import xyz.whyle.pointcount.app.App;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
 {
		public static ArrayList<MyData> mDataset;

		// Provide a reference to the views for each data item
		// Complex data items may need more than one view per item, and
		// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder
	{

		private CardView cv;

		private TextView time;

		private TextView point;

		private TextView from;
			// each data item is just a string in this case
		
			public ViewHolder(View v) {
				super(v);
				cv = (CardView)v.findViewById(R.id.cv);
				point = (TextView)v.findViewById(R.id.point);
				time = (TextView)v.findViewById(R.id.time);
				from = (TextView)v.findViewById(R.id.from);
				
				
			}
		}

		// Provide a suitable constructor (depends on the kind of dataset)
		public RecyclerViewAdapter(ArrayList myDataset) {
			this.mDataset = myDataset;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
													   int viewType) {
			// create a new view
			View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
			// set the view's size, margins, paddings and layout parameters
			
			ViewHolder vh = new ViewHolder(v);
			return vh;
		}

		// Replace the contents of a view (invoked by the layout manager)
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			// - get element from your dataset at this position
			// - replace the contents of the view with that element
		    
			holder.point.setText(mDataset.get(position).getGroup1());
			holder.time.setText(mDataset.get(position).getTime());
			holder.from.setText(mDataset.get(position).getFrom());
			
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return mDataset.size();
		}
	}

