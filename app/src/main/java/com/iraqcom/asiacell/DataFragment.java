package com.iraqcom.asiacell;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.iraqcom.asiacell.R;

public class DataFragment extends Fragment {
	View rootView;
	Button subscribe;
	Button unsubscribe;
	Context con;
	public static int position;
	public static ImageView imageView;
	ArrayList<SingleItem> array;
	public static TextView textViewlabel;
	public static TextView textView;

	public DataFragment(Context con, int position, ArrayList<SingleItem> array) {
		this.con = con;
		this.array = array;
		DataFragment.position = position;
		System.out.println("test4" + DataFragment.position);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.suscribeunsuscribe, container,
				false);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new JSONAsyncTaskItemSubUnsub(con)
						.execute("http://iraqcomprojects.com/WEB%20SERVICES/getitem.php?langid="
								+ (EnglishLanguage.position + 1)
								+ "&cid="
								+ array.get(SubDetailItemFragment.position).id
										.toString());
			}
		}, 200);

		imageView = (ImageView) rootView.findViewById(R.id.imageViewSubUnsub);
		imageView.getLayoutParams().height = (int) (MainActivity.width) * 3 / 8;
		imageView.getLayoutParams().width = (int) (MainActivity.width);
		imageView.setScaleType(ScaleType.FIT_XY);
		textViewlabel = (TextView) rootView.findViewById(R.id.redlabel);
		if (EnglishLanguage.position == 0) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"EurostileLTStd-Cn.otf");
			textViewlabel.setTypeface(type);
		} else if (EnglishLanguage.position == 1) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"GE_SS_Two_Light.otf");
			textViewlabel.setTypeface(type);
		} else {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"AdobeArabic-Regular.otf");
			textViewlabel.setTypeface(type);
			textViewlabel.setTextSize(30);
		}

		textViewlabel.setBackgroundColor(Color.rgb(205, 16, 65));
		textView = (TextView) rootView.findViewById(R.id.textViewSubUnsub);
		if (EnglishLanguage.position == 0) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"EurostileLTStd-Cn.otf");
			textView.setTypeface(type);
		} else if (EnglishLanguage.position == 1) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"GE_SS_Two_Light.otf");
			textView.setTypeface(type);
		} else {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"AdobeArabic-Regular.otf");
			textView.setTypeface(type);
			textView.setTextSize(25);
		}
		// textView.setTextSize(24);
		textView.setPadding((int) (MainActivity.width / 16),
				(int) (MainActivity.width / 32),
				(int) (MainActivity.width / 16),
				(int) (MainActivity.width / 16));

		subscribe = (Button) rootView.findViewById(R.id.subscribe);
		if (EnglishLanguage.position == 0) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"EurostileLTStd-Cn.otf");
			subscribe.setTypeface(type);
			subscribe.setText("Subscribe");
		} else if (EnglishLanguage.position == 1) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"GE_SS_Two_Light.otf");
			subscribe.setTypeface(type);
			subscribe.setText("الاشتراك");
		} else if (EnglishLanguage.position == 2) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"AdobeArabic-Regular.otf");
			subscribe.setTypeface(type);
			subscribe.setText("بەشداریکردن");
			// subscribe.setTextSize(25);
		}
		subscribe.setBackgroundColor(Color.rgb(205, 16, 65));
		subscribe.getLayoutParams().height = (int) (MainActivity.width / 8);
		subscribe.getLayoutParams().width = (int) (MainActivity.width / 2.5);
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
				subscribe.getLayoutParams().width,
				subscribe.getLayoutParams().height);
		params1.setMargins((int) (MainActivity.width / 32),
				(int) (MainActivity.width / 16), 0, 0);
		subscribe.setLayoutParams(params1);
		subscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (JSONAsyncTaskItemSubUnsub.actorsListDetail
						.get(DataFragment.position).custom.equals("0"))
					sendSMSSub();
				else
					sendSMSsub0();

			}
		});
		unsubscribe = (Button) rootView.findViewById(R.id.unsubscribe);
		if (EnglishLanguage.position == 0) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"EurostileLTStd-Cn.otf");
			unsubscribe.setTypeface(type);
			unsubscribe.setText("Unsubscribe");
		} else if (EnglishLanguage.position == 1) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"GE_SS_Two_Light.otf");
			unsubscribe.setTypeface(type);
			unsubscribe.setText(" إلغاء الاشتراك");
		} else if (EnglishLanguage.position == 2) {
			Typeface type = Typeface.createFromAsset(con.getAssets(),
					"AdobeArabic-Regular.otf");
			unsubscribe.setTypeface(type);
			unsubscribe.setText("ڕاگرتن");
			// unsubscribe.setTextSize(25);
		}
		unsubscribe.setBackgroundColor(Color.rgb(205, 16, 65));
		unsubscribe.getLayoutParams().height = (int) (MainActivity.width / 8);
		unsubscribe.getLayoutParams().width = (int) (MainActivity.width / 2.5);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				unsubscribe.getLayoutParams().width,
				unsubscribe.getLayoutParams().height);
		params.setMargins((int) (MainActivity.width / 8),
				(int) (MainActivity.width / 16),
				(int) (MainActivity.width / 32), (int) (MainActivity.width / 8));
		unsubscribe.setLayoutParams(params);
		unsubscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (JSONAsyncTaskItemSubUnsub.actorsListDetail
						.get(DataFragment.position).custom.equals("0"))
					sendSMSUnsub();
				else
					sendSMSUnsub0();
			}
		});

		return rootView;

	}

	protected void sendSMSUnsub0() {
		String phoneNo = JSONAsyncTaskItemSubUnsub.actorsListDetail
				.get(DataFragment.position).u_no;
		String sms = JSONAsyncTaskItemSubUnsub.actorsListDetail
				.get(DataFragment.position).u_data;

		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
			Toast.makeText(getActivity(), "SMS Sent!", Toast.LENGTH_LONG)
					.show();

		} catch (Exception e) {
			Toast.makeText(getActivity(), "SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		

	}

	protected void sendSMSsub0() {
		String phoneNo = JSONAsyncTaskItemSubUnsub.actorsListDetail.get(0).s_no;
		String sms = JSONAsyncTaskItemSubUnsub.actorsListDetail
				.get(DataFragment.position).s_data;

//		try {
//			SmsManager smsManager = SmsManager.getDefault();
//			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
//			Toast.makeText(getActivity(), "SMS Sent!", Toast.LENGTH_LONG)
//					.show();
//		} catch (Exception e) {
//			Toast.makeText(getActivity(), "SMS faild, please try again later!",
//					Toast.LENGTH_LONG).show();
//			e.printStackTrace();
//		}

		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("smsto:"));
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address",
				JSONAsyncTaskItemSubUnsub.actorsListDetail
						.get(DataFragment.position).s_no);
		smsIntent.putExtra("sms_body",
				JSONAsyncTaskItemSubUnsub.actorsListDetail
						.get(DataFragment.position).s_data.toString());
		try {
			startActivity(smsIntent);

		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(getActivity(), "SMS faild, please try again later.",
					Toast.LENGTH_SHORT).show();
		}
	}

	protected void sendSMSUnsub() {

		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(
					JSONAsyncTaskItemSubUnsub.actorsListDetail
							.get(DataFragment.position).u_no, null,
					JSONAsyncTaskItemSubUnsub.actorsListDetail
							.get(DataFragment.position).u_data.toString(),
					null, null);
			Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(getActivity(), "SMS faild, please try again.",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	protected void sendSMSSub() {

		// try {
		// Log.e("s data",
		// ""
		// + JSONAsyncTaskItemSubUnsub.actorsListDetail
		// .get(DataFragment.position).s_no
		// + ", "
		// + JSONAsyncTaskItemSubUnsub.actorsListDetail
		// .get(DataFragment.position).s_data
		// .toString());
		//
		// Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		// smsIntent.setData(Uri.parse("smsto:"));
		// smsIntent.setType("vnd.android-dir/mms-sms");
		// smsIntent.putExtra("address",
		// JSONAsyncTaskItemSubUnsub.actorsListDetail
		// .get(DataFragment.position).s_no);
		// smsIntent.putExtra("sms_body",
		// JSONAsyncTaskItemSubUnsub.actorsListDetail
		// .get(DataFragment.position).s_data.toString());
		// startActivity(smsIntent);
		//
		// } catch (android.content.ActivityNotFoundException ex) {
		// Toast.makeText(getActivity(), "SMS faild, please try again later.",
		// Toast.LENGTH_SHORT).show();
		// }
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(
					JSONAsyncTaskItemSubUnsub.actorsListDetail
							.get(DataFragment.position).s_no, null,
					JSONAsyncTaskItemSubUnsub.actorsListDetail
							.get(DataFragment.position).s_data.toString(),
					null, null);
			Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(getActivity(), "SMS faild, please try again.",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

}

class JSONAsyncTaskItemSubUnsub extends AsyncTask<String, Void, Boolean> {
	// DataDetailAdapter adapter;
	Context con;
	ProgressDialog dialog;
	public static ArrayList<DataSingle> actorsListDetail;
	public static String category_id;

	public JSONAsyncTaskItemSubUnsub(Context con) {

		this.con = con;
		actorsListDetail = new ArrayList<DataSingle>();

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(con);
		dialog.setMessage("Loading");
		// dialog.setTitle("Connecting server");
		dialog.show();
		dialog.setCancelable(false);

		// actorsListDetail.clear();

	}

	@Override
	protected Boolean doInBackground(String... urls) {
		Log.e("urlll22", "" + urls[0]);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urls[0]);
		post.setHeader("Content-type", "appliction/json");
		Log.e("post", "" + post);
		try {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);
			JSONObject jsono = new JSONObject(data);

			JSONArray jarray = jsono.getJSONArray("posts");

			int i = 0;
			while (i < jarray.length()) {

				JSONObject object = jarray.getJSONObject(i);
				category_id = object.getString("id");
				DataSingle finalPage = new DataSingle();
				finalPage.image = object.getString("banner");
				finalPage.text = object.getString("desc");
				finalPage.s_data = object.getString("s_data");
				finalPage.s_no = object.getString("s_no");
				finalPage.u_data = object.getString("u_data");
				finalPage.u_no = object.getString("u_no");
				finalPage.custom = object.getString("custom");
				finalPage.cname = object.getString("cname");
				finalPage.item = object.getString("item");
				actorsListDetail.add(finalPage);

				i++;
			}
			System.out.println("list" + actorsListDetail.toString());
			// adapter = new DataDetailAdapter(con, actorsListDetail);
			return true;

		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);
		if (result != null) {
			try {
				DataFragment.textView.setText(actorsListDetail
						.get(DataFragment.position).text.toString());
				DataFragment.textViewlabel.setText(actorsListDetail
						.get(DataFragment.position).item.toString());
				String baseUrl = "http://iraqcomprojects.com/";
				Picasso.with(con)
						.load(baseUrl
								+ actorsListDetail.get(DataFragment.position).image
										.toString())
						.into(DataFragment.imageView);
			} catch (Exception e) {
				ProgressDialog dialog = new ProgressDialog(con);
				dialog.setMessage("Internet is Not working on your device");
				dialog.show();
				dialog.setCancelable(true);
				System.out.println(e);
			}
		}
		dialog.cancel();

	}
}
