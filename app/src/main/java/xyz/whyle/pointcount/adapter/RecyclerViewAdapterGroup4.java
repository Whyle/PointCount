package xyz.whyle.pointcount.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import xyz.whyle.pointcount.MyData;
import xyz.whyle.pointcount.R;
import android.graphics.Color;
import xyz.whyle.pointcount.Utils;
import java.util.Calendar;
import java.util.Locale;

public class RecyclerViewAdapterGroup4 extends RecyclerView.Adapter<RecyclerViewAdapterGroup4.ViewHolder>
{
	public static ArrayList<MyData.Data> mDataset = new ArrayList<MyData.Data>();

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder
	{

		private CardView cv;


		private TextView point;

		private LinearLayout root;

		private TextView name;

		private TextView sex;

		private TextView birthday;

		private TextView phone;

		private TextView address;

		private LinearLayout goneroot;
		// each data item is just a string in this case

		public ViewHolder(View v) {
			super(v);
			cv = (CardView)v.findViewById(R.id.cv);
			name = (TextView)v.findViewById(R.id.item_name);
			sex = (TextView)v.findViewById(R.id.item_sex);
			birthday = (TextView)v.findViewById(R.id.item_birthday);
			phone = (TextView)v.findViewById(R.id.item_phone);
			address = (TextView)v.findViewById(R.id.item_address);
			root = (LinearLayout)v.findViewById(R.id.item_root);
			goneroot = (LinearLayout)v.findViewById(R.id.item_gone_layout);
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public RecyclerViewAdapterGroup4() {
	}

	public void setData( List<MyData.Data> data)
	{
		this.mDataset=new ArrayList<MyData.Data>(data);
	}

	//定义一个接口
    public static interface  OnItemClickListener{
        void onItemClick(View view, MyData.Data data);
		void onItemLongClick(View view, MyData.Data data);
    }

	private OnItemClickListener mOnItemClickListener;
    //添加接口和设置Adapter的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

	// Create new views (invoked by the layout manager)
	@Override
	public RecyclerViewAdapterGroup4.ViewHolder onCreateViewHolder(ViewGroup parent,
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
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element

		holder.name.setText(mDataset.get(position).getName());
		holder.sex.setText(mDataset.get(position).getSex().equals("M") ? "♂" : "♀");
		holder.sex.setTextColor(mDataset.get(position).getSex().equals("M") ? Color.BLUE : Color.RED);
		holder.birthday.setText(mDataset.get(position).getBirthday());
		holder.birthday.append(" (" + Utils.getAge(mDataset.get(position).getBirthday()) + ")");
		holder.phone.setText(mDataset.get(position).getPhone());
		holder.address.setText(mDataset.get(position).getAddress());
		holder.root.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(holder.goneroot.getVisibility()==View.GONE)
						holder.goneroot.setVisibility(View.VISIBLE);
					else
						holder.goneroot.setVisibility(View.GONE);
				}
			});
		if (mOnItemClickListener!=null){

			
			holder.root.setOnLongClickListener(new View.OnLongClickListener(){

					@Override
					public boolean onLongClick(View p1)
					{
						// TODO: Implement this method
						mOnItemClickListener.onItemLongClick(holder.itemView, mDataset.get(position));
						return false;
					}
				});
		}

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}
}

