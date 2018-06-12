
package com.iraqcom.asiacell;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.iraqcom.asiacell.R;

public class CustomAdapter extends BaseAdapter {
	Context context;
	ArrayList<SingleItem> list;
		
		
	
	public CustomAdapter(Context con, ArrayList<SingleItem> actorsList) {
		this.list=actorsList;
		this.context=con;
		
	}
	@Override
	public int getCount() {
			return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class Viewholder{
		ImageView myImages;
		TextView myText;
		int position;
		public Viewholder(View v, int position) {
			
			myImages = (ImageView) v.findViewById(R.id.imageViewgrid);
			myImages.getLayoutParams().height=(int) (MainActivity.width/2);
			myImages.getLayoutParams().width=(int) (MainActivity.width/2);
			myImages.setPadding((int)MainActivity.width/12,(int)MainActivity.width/12, (int)MainActivity.width/12,(int)MainActivity.width/24);
			
			myImages.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					myImages.setBackgroundColor(color.transparent);
					return false;
				}
			 
			 });
			myText = (TextView) v.findViewById(R.id.textView1);
			myText.setTextSize(16);
			if(EnglishLanguage.position==0){
				Typeface type=Typeface.createFromAsset(context.getAssets(),
				        "EurostileLTStd-Cn.otf");
				myText.setTypeface(type);
				}
				else if(EnglishLanguage.position==1)
				{
					Typeface type=Typeface.createFromAsset(context.getAssets(),
					        "GE_SS_Two_Light.otf");
					myText.setTypeface(type);
				}
				else {
					Typeface type=Typeface.createFromAsset(context.getAssets(),
					        "AdobeArabic-Regular.otf");
					myText.setTypeface(type);
					myText.setTextSize(22);
				}
			
			
			RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams)myText.getLayoutParams();
			layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
			myText.setLayoutParams(layoutParams);
			int positionOfText= (int) ((MainActivity.width/2 - 2*(MainActivity.width/12))+MainActivity.width/12);
			myText.setPadding(10, positionOfText, 10, 0);	
		}
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		View row=view;
		Viewholder holder=null;
		if(row==null)
		{				
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row=inflater.inflate(R.layout.englishcustomgridview,parent,false );
		holder = new Viewholder(row,position);
		row.setTag(holder);
		}
		else
		{
			holder = (Viewholder)row.getTag();
		}

	String baseUrl="http://iraqcomprojects.com/";
		Picasso.with(context).load(baseUrl+list.get(position).images).into(holder.myImages);
		holder.myText.setText(list.get(position).text);
		return row;
	}
	

}
