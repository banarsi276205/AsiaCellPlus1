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

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iraqcom.asiacell.R;

public class SubDetailItemFragment extends Fragment {
	View rootView;
	public static int position;
	public static ListView listItem;
	DetailAdapter subItemAdapter;
	ArrayList<SingleItemDetail> arraylist;
	ArrayList<SingleItem> array;
	static TextView txt;
	AlertDialog alertDialog, fpDialog;
	// int langPosition;
	Context con;

	// String id;
	public SubDetailItemFragment(Context englishLanguage, int position,
			ArrayList<SingleItem> array) {
		this.con = englishLanguage;
		SubDetailItemFragment.position = position;

		this.array = array;
		System.out.println("position" + position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		arraylist = new ArrayList<SingleItemDetail>();
		EnglishLanguage.t.cancel();
		rootView = inflater.inflate(R.layout.itemdetail, container, false);

		splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				new JSONAsyncTaskItem(con, arraylist)
						.execute("http://iraqcomprojects.com/WEB%20SERVICES/getitem.php?langid="
								+ (splashScreen.langvalue + 1)
								+ "&cid="
								+ array.get(SubDetailItemFragment.position).id
										.toString());

			}
		}, 200);

		listItem = (ListView) rootView.findViewById(R.id.listviewsub);
		txt = (TextView) rootView.findViewById(R.id.txt);
		subItemAdapter = new DetailAdapter(getActivity(), arraylist);

		listItem.setAdapter(subItemAdapter);

		listItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				displayView(arg2);
			}
		});
		return rootView;
	}

	private void displayView(int arg2) {
		Fragment fragment = new DataFragment(getActivity(), arg2, array);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).addToBackStack(null)
				.commit();
		System.out.println("back stack1"
				+ getFragmentManager().getBackStackEntryCount());
	}
}

class JSONAsyncTaskItem extends AsyncTask<String, Void, Boolean> {
	DetailAdapter adapter;
	Context con;
	ProgressDialog dialog;
	AlertDialog fpDialog, fpDialog1;
	public static ArrayList<SingleItemDetail> actorsListDetail;

	public JSONAsyncTaskItem(Context con, ArrayList<SingleItemDetail> arrayList) {

		JSONAsyncTaskItem.actorsListDetail = arrayList;
		this.con = con;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		dialog = new ProgressDialog(con);
		dialog.setMessage("Loading");

		// dialog.setTitle("Connecting server");
		dialog.show();
		dialog.setCancelable(false);

	}

	@Override
	protected Boolean doInBackground(String... urls) {
		Log.e("urlll", "" + urls[0]);
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

				SingleItemDetail actor = new SingleItemDetail();
				actor.largeText = object.getString("item");
				actor.smallText = object.getString("desc");
				actor.image = object.getString("icon");
				actor.id = object.getString("id");
				actorsListDetail.add(actor);

				i++;
			}
			adapter = new DetailAdapter(con, actorsListDetail);
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
		dialog.cancel();
		if (result != null) {
			try {

				SubDetailItemFragment.listItem.setAdapter(adapter);
				Log.e("language", "" + splashScreen.langvalue);
				if (actorsListDetail.size() == 0) {
					if (splashScreen.langvalue == 0) {
						showDialog();

					} else if (splashScreen.langvalue == 2) {

						showDialogTwo();
					} else if (splashScreen.langvalue == 1) {

					}
				}
				// adapter.notifyDataSetChanged();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (result == false)
			Toast.makeText(con.getApplicationContext(),
					"Unable to fetch data from server", Toast.LENGTH_LONG)
					.show();

	}

	private void showDialogTwo() {
		LayoutInflater inflater = (LayoutInflater) con
				.getSystemService(con.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.reset_pass_dialog, null);
		TextView fp_chang = (TextView) layout.findViewById(R.id.fp_chang);
		TextView fp_sorry = (TextView) layout.findViewById(R.id.fp_sorry);
		fp_sorry.setText("دڵگران");
		TextView fp_con_pass = (TextView) layout.findViewById(R.id.fp_con_pass);
		fp_con_pass
				.setText("هیچ خزمەتگوزاریك نیە لە ژێر ئەم بابەتا، تکایە بابەتێكی تر هەڵبژێرە");
		fp_chang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fpDialog1.dismiss();
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setView(layout);
		fpDialog1 = builder.create();
		fpDialog1.show();
	}

	private void showDialog() {
		LayoutInflater inflater = (LayoutInflater) con
				.getSystemService(con.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.reset_pass_dialog, null);
		TextView fp_chang = (TextView) layout.findViewById(R.id.fp_chang);
		TextView fp_sorry = (TextView) layout.findViewById(R.id.fp_sorry);
		fp_sorry.setText("Sorry");
		TextView fp_con_pass = (TextView) layout.findViewById(R.id.fp_con_pass);
		fp_con_pass
				.setText("There are no services under this category, please try another category");
		fp_chang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fpDialog.dismiss();
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setView(layout);
		fpDialog = builder.create();
		fpDialog.show();

	}

}
