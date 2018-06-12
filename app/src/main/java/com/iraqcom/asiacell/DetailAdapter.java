package com.iraqcom.asiacell;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.iraqcom.asiacell.R;

public class DetailAdapter extends BaseAdapter {
	Context context;
	ArrayList<SingleItemDetail> list;
	//Spanned text;
	
	public DetailAdapter(Context con, ArrayList<SingleItemDetail> actorsList) {
		this.list=actorsList;
		this.context=con;
	
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class Viewholder{
		ImageView myImagesSub;
		TextView myTextSubLarge;
		TextView myTextSubSmall;
	
		public Viewholder(View v) {
			myImagesSub = (ImageView) v.findViewById(R.id.imageViewprofile);
				myImagesSub.setPadding((int) (MainActivity.width/32), (int) (MainActivity.width/32), (int) (MainActivity.width/32), (int) (MainActivity.width/32));
				myImagesSub.setScaleType(ScaleType.FIT_XY);
				myTextSubLarge = (TextView) v.findViewById(R.id.textViewlarge);
				myTextSubSmall = (TextView) v.findViewById(R.id.textViewsmall);
				if(EnglishLanguage.position==0){
					Typeface type=Typeface.createFromAsset(context.getAssets(),
					        "EurostileLTStd-Cn.otf");
					myTextSubLarge.setTypeface(type);
					myTextSubSmall.setTypeface(type);
//					myTextSubLarge.setPadding(0, 25, (int) (MainActivity.width/32), 0);
					}
					else if(EnglishLanguage.position==1)
					{
						Typeface type=Typeface.createFromAsset(context.getAssets(),
						        "GE_SS_Two_Light.otf");
						myTextSubLarge.setTypeface(type);
						myTextSubSmall.setTypeface(type);
//						myTextSubLarge.setPadding((int) (MainActivity.width/32), 25, 0, 0);
					}
					else{
						Typeface type=Typeface.createFromAsset(context.getAssets(),
						        "AdobeArabic-Regular.otf");
						myTextSubLarge.setTypeface(type);
						myTextSubSmall.setTypeface(type);
						myTextSubLarge.setTextSize(20);
						myTextSubSmall.setTextSize(18);
					}
//				myTextSubLarge.setTextSize(15);
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row=convertView;
		Viewholder holder=null;
		splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
		if(row==null)
		{	
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(EnglishLanguage.position==0)
		row=inflater.inflate(R.layout.itemcustomlistview,parent,false );
		else
			row=inflater.inflate(R.layout.itemcustomlistviewarabic,parent,false );
		holder = new Viewholder(row);
		row.setTag(holder);
		}
		else
		{
			holder = (Viewholder)row.getTag();
		}
		String baseUrl="http://iraqcomprojects.com/";
		
		
		System.out.println("length"+splashScreen.langvalue);
		String largetext = list.get(position).largeText;
		String smalltext = list.get(position).smallText;
		holder.myTextSubLarge.setText(largetext);
		holder.myTextSubSmall.setText(smalltext);
		Picasso.with(context).load(baseUrl+list.get(position).image).resize((int) (MainActivity.width/3), (int) (MainActivity.width/3)).into(holder.myImagesSub);

		return row;
	}

}
