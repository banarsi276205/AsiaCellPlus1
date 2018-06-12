//Author Android Developer - Rishabh Gupta

/*langvalue = 0; For English=English
 *langvalue = 1; For Arabic=العربية
 *langvalue = 2; For Kurdish=كوردى*/
//splashScreen.langvalue=EnglishLanguage.position
package com.iraqcom.asiacell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.iraqcom.asiacell.R;

public class EnglishLanguage extends Fragment {
	public static com.iraqcom.asiacell.GridView grid;
	static int index;
	View rootView;
	Context cont;
	CustomAdapter custom;
	static View header;
	static JSONAsyncTaskAdvertisement jsonAdver;
	ArrayList<SingleItem> actorsList;
	ArrayList<Advertisement> adver;
	static Timer t;
	public static String id;
	public static int position;
	public static int length;
	public static int griPosition;
	static ImageView advertisement;
	static int Flag = 0;

	public EnglishLanguage(Context mainActivity, int position) {
		this.cont = mainActivity;
		EnglishLanguage.position = position;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		actorsList = new ArrayList<SingleItem>();
		adver = new ArrayList<Advertisement>();
		splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
		splashScreen.advertisement = splashScreen.settings
				.getInt("adverpos", 0);
		rootView = inflater.inflate(R.layout.englishgridview, container, false);

		header = View.inflate(getActivity(), R.layout.item, null);

		grid = (com.iraqcom.asiacell.GridView) rootView.findViewById(R.id.list);
		grid.setNumColumns(2);
		grid.addHeaderView(header);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("posi" + arg2);
				if (splashScreen.advertisement == 0) {
					arg2 = arg2 - 2;
					System.out.println("posi" + arg2);
				}

				else if (splashScreen.advertisement == 1) {

					System.out.println("posi" + (arg2));
				}
				displayView(arg2);
			}

		});

		// grid.removeHeaderView();

		RelativeLayout adverRelative = (RelativeLayout) rootView
				.findViewById(R.id.imageadverrelative);
		adverRelative.getLayoutParams().height = (int) (MainActivity.width * 2 / 3);
		adverRelative.setBackgroundColor(Color.rgb(205, 16, 65));
		advertisement = (ImageView) rootView.findViewById(R.id.imageadver);
		callOfGridData();

		callOfAdverMethod();

		if (splashScreen.langvalue == 0) {
			Typeface type = Typeface.createFromAsset(cont.getAssets(),
					"EurostileLTStd-Cn.otf");
			MainActivity.notification.setTypeface(type);
			MainActivity.notification.setText("Notification");
		} else if (splashScreen.langvalue == 2) {
			Typeface type = Typeface.createFromAsset(cont.getAssets(),
					"AdobeArabic-Regular.otf");
			MainActivity.notification.setTypeface(type);
			MainActivity.notification.setTextSize(27);
			MainActivity.notification.setText("ئاگاداری");
		} else if (splashScreen.langvalue == 1) {
			Typeface type = Typeface.createFromAsset(cont.getAssets(),
					"GE_SS_Two_Light.otf");
			MainActivity.notification.setTypeface(type);
			MainActivity.notification.setText("التنبيهات");

		}

		return rootView;

	}

	private void callOfGridData() {

		new JSONAsyncTask(cont, actorsList)
				.execute("http://iraqcomprojects.com/WEB%20SERVICES/getcatagories.php?langid="
						+ (splashScreen.langvalue + 1));
	}

	private void callOfAdverMethod() {
		if (t != null)
			t.cancel();
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Flag = 1;
				// advertisement.setImageDrawable(null);
				if (splashScreen.langvalue == 0)
					jsonAdver = (JSONAsyncTaskAdvertisement) new JSONAsyncTaskAdvertisement(
							cont, adver)
							.execute("http://iraqcomprojects.com/WEB%20SERVICES/showbanner.php?langid=1");
				else if (splashScreen.langvalue == 1)
					jsonAdver = (JSONAsyncTaskAdvertisement) new JSONAsyncTaskAdvertisement(
							cont, adver)
							.execute("http://iraqcomprojects.com/WEB%20SERVICES/showbanner.php?langid=2");
				else if (splashScreen.langvalue == 2)
					jsonAdver = (JSONAsyncTaskAdvertisement) new JSONAsyncTaskAdvertisement(
							cont, adver)
							.execute("http://iraqcomprojects.com/WEB%20SERVICES/showbanner.php?langid=3");

				System.out.println("check");
			}

		}, 0, 5000);

	}

	public void onResume() {

		super.onResume();
		callOfAdverMethod();
	}

	public void onPause() {
		super.onPause();
		System.out.println("onPause!!!!!!");
		t.cancel();

	}

	public void displayView(int arg2) {
		Fragment fragment = new SubDetailItemFragment(getActivity(), arg2,
				actorsList);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).addToBackStack(null)
				.commit();

	}

}

class JSONAsyncTaskDevice extends AsyncTask<String, Void, Boolean> {

	@Override
	protected Boolean doInBackground(String... urls) {
		Log.e("urlll", "" + urls[0]);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urls[0]);

		try {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);
			JSONObject jsono = new JSONObject(data);

			String responsehttp = jsono.getString("posts");
			System.out.println("Response" + responsehttp);

		}

		catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

}

class JSONAsyncTaskAdvertisement extends AsyncTask<String, Void, Boolean> {
	static JSONArray jarray;
	static JSONObject jsono;
	Context con;
	int imagePosition;

	ArrayList<Advertisement> advertisement;

	public JSONAsyncTaskAdvertisement(Context cont,
			ArrayList<Advertisement> actorsList) {

		this.advertisement = actorsList;
		this.con = cont;

	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		splashScreen.advertisement = splashScreen.settings
				.getInt("adverpos", 0);
		

	}

	@Override
	protected Boolean doInBackground(String... urls) {
		Log.e("urlll", "" + urls[0]);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urls[0]);

		try {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);
			jsono = new JSONObject(data);
			System.out.println("success" + jsono.getInt("success"));
			jarray = jsono.getJSONArray("posts");

			int i = 0;
			advertisement.clear();
			while (i < jarray.length()) {
				JSONObject object = jarray.getJSONObject(i);
				Advertisement actor = new Advertisement();
				actor.setImages(object.getString("image"));
				actor.setUrl(object.getString("hlink"));
				advertisement.add(actor);
				System.out.println("advertisement" + advertisement);
				i++;
			}

			return true;
		}

		// ------------------>>
		catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (result != null) {
			try

			{

				/*
				 * Timer t = new Timer(); t.scheduleAtFixedRate(new TimerTask()
				 * {
				 * 
				 * @Override public void run() {
				 */
				splashScreen.advertisement = splashScreen.settings.getInt(
						"adverpos", 0);
				System.out.println("adverpositionrishabh"
						+ splashScreen.advertisement);
				System.out.println("check 1" + splashScreen.advertisement);
				imagePosition = splashScreen.advertisement;
				System.out.println("array" + jarray.length());
				if (splashScreen.advertisement < jarray.length()) {

					// System.out.println("banner2");
					String baseUrl = "http://iraqcomprojects.com/";

					EnglishLanguage.advertisement.setImageDrawable(null);
					// EnglishLanguage.advertisement.setImageBitmap(d);

					System.out.println("index" + splashScreen.advertisement);
					Picasso.with(con)
							.load(baseUrl
									+ advertisement.get(
											splashScreen.advertisement)
											.getImages())
							.into(EnglishLanguage.advertisement);

					EnglishLanguage.advertisement
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// EnglishLanguage.t.cancel();
									if (!advertisement.equals("")) {
										Log.e("url now", "" + "http://"
												+ advertisement.get(0).getUrl());
										Intent intent = new Intent(
												Intent.ACTION_VIEW,
												Uri.parse("http://"
														+ advertisement.get(0)
																.getUrl()));
										con.startActivity(intent);
									} else {
										Log.e("url now", "kkkkkkk");
									}

								}
							});

					imagePosition = imagePosition + 1;
					splashScreen.editor.putInt("adv", imagePosition).commit();

				} else {
					splashScreen.editor.putInt("adv", 0).commit();
				}
				/*
				 * }
				 * 
				 * },0,5000);
				 */
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("e");
			} catch (NullPointerException e) {

				System.out.println("exception1" + e);
			} catch (Exception e) {
				System.out.println("hello" + e);
			}

			helper();

		}

	}

	private void helper() {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					if (jsono.getInt("success") == 0) {

						EnglishLanguage.grid.removeHeaderView();

						splashScreen.editor.putInt("adverpos", 1).commit();
						EnglishLanguage.t.cancel();
					} else
						splashScreen.editor.putInt("adverpos", 0).commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

}// end of class

class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
	CustomAdapter adapter;
	Context con;
	ProgressDialog dialog;
	ArrayList<SingleItem> actorsList;

	public JSONAsyncTask(Context cont, ArrayList<SingleItem> actorsList) {

		this.actorsList = actorsList;
		this.con = cont;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(con);
		dialog.setMessage("Loading");

		// dialog.setTitle("Connecting server");
		dialog.show();

		dialog.setCancelable(false);
		actorsList.clear();
	}

	@Override
	protected Boolean doInBackground(String... urls) {
		Log.e("urlll", "" + urls[0]);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(urls[0]);

		try {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity);
			JSONObject jsono = new JSONObject(data);

			JSONArray jarray = jsono.getJSONArray("posts");
			int i = 0;
			while (i < jarray.length()) {
				JSONObject object = jarray.getJSONObject(i);
				EnglishLanguage.id = object.getString("id");

				SingleItem actor = new SingleItem(object.getString("icon"),
						object.getString("cname"));
				actor.setId(EnglishLanguage.id);
				actorsList.add(actor);

				i++;
			}
			adapter = new CustomAdapter(con, actorsList);

			return true;
		}

		// ------------------>>
		catch (ParseException e1) {
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
		dialog.cancel();
		if (result != null) // add this
		{
			try {
				EnglishLanguage.grid.setAdapter(adapter);
			} catch (Exception e) {
				/*
				 * ProgressDialog dialog = new ProgressDialog(con);
				 * dialog.setMessage("Internet is Not working on your device");
				 * dialog.show(); dialog.setCancelable(true);
				 */
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
				builder.setMessage("Internet is Not working on your device")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});

				AlertDialog alert = builder.create();
				alert.show();
				System.out.println("Exceptional" + e);
			}
		}
	}
}
