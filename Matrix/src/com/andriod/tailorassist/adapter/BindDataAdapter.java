package com.andriod.tailorassist.adapter;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andriod.tailorassist.MeasurmentDetailVO;
import com.andriod.tailorassist.R;

public class BindDataAdapter extends BaseAdapter {
	Activity mLocal;
	int[] imgArray;
	String titleA[];
	String details[];
	LayoutInflater mLayoutInflater;

	public BindDataAdapter(Activity activity, int[] imageArray, String[]

	title, String[] details) {
		mLocal = activity;
		imgArray = imageArray;
		titleA = title;
		this.details = details;
		mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public BindDataAdapter(Activity activity, Map<String, MeasurmentDetailVO> mesurments) {
			
				mLocal = activity;
				int itemCount = mesurments.size();
				
				imgArray = new int[itemCount] ;
				titleA = new String[itemCount];
				this.details = new String[itemCount];
				int index =0;
				for(Entry<String, MeasurmentDetailVO> item :  mesurments.entrySet()){
					titleA[index] = item.getKey();
					MeasurmentDetailVO vo = item.getValue();
					details[index]= vo.getMeasurments();
					imgArray[index]=vo.getIconId();
					index++;
				}
				mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
	public int getCount() {
		return imgArray.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	private class Holder {
		ImageView image;
		TextView textView;
		TextView detailView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder = null;
		if (convertView == null) {
			convertView =	mLayoutInflater.inflate(R.layout.measurment_detail_item, null);
			holder = new Holder();
			holder.image = (ImageView)convertView.findViewById(R.id.iamge);
			holder.textView = (TextView)convertView.findViewById(R.id.title);
			holder.detailView = (TextView)convertView.findViewById(R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.image.setBackgroundResource(imgArray[position]);
		holder.textView.setText(titleA[position]);
		holder.detailView.setText(details[position]);
		return convertView;
	}

}
