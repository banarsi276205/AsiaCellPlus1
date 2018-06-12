//Author Android Developer - Rishabh Gupta

/*langvalue = 0; For English=English
 *langvalue = 1; For Arabic=العربية
 *langvalue = 2; For Kurdish=كوردى*/
package com.iraqcom.asiacell;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iraqcom.asiacell.R;

public class PhoneNumber extends Activity {
	EditText txt;
	int number;
	Typeface type;// For Font Style

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title bar

		splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
		setContentView(R.layout.promptbox);// our layout xml

		RelativeLayout relative = (RelativeLayout) findViewById(R.id.relativeLayout1);
		relative.setBackgroundColor(Color.rgb(100, 38, 41));

		TextView txtphone = (TextView) findViewById(R.id.textViewPhone);

		txt = (EditText) findViewById(R.id.editTextPhone);
		 
		if (splashScreen.langvalue == 0) {
			type = Typeface.createFromAsset(this.getAssets(),
					"EurostileLTStd-Cn.otf");
			txtphone.setTypeface(type);
			txt.setHint("PHONE NUMBER");
			txtphone.setText("Please enter your phone number");
		}

		else if (splashScreen.langvalue == 1) {

			type = Typeface.createFromAsset(this.getAssets(),
					"GE_SS_Two_Light.otf");
			txtphone.setTypeface(type);
			txt.setHint("رقم الهاتف");
			txtphone.setText("الرجاء أدخال رقم هاتفك");
		}

		else if (splashScreen.langvalue == 2) {
			type = Typeface.createFromAsset(this.getAssets(),
					"AdobeArabic-Regular.otf");

			txtphone.setTypeface(type);
			txt.setHint("ژماره‌ی ته‌له‌فۆن");
			txtphone.setText("تكایه‌ ژماره‌ی ته‌له‌فۆنه‌كه‌ت داخڵ بكه");
		}

		txt.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER);
		txt.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					alertBox();
				}
				return false;
			}
		});
		ImageButton done = (ImageButton) findViewById(R.id.imageButtondone);
		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertBox();
			}
		});
	}// End of onCreate Method

	protected void alertBox() {
		number = ("077" + txt.getText().toString()).length();
		Log.e("number is", "" +"077" + txt.getText().toString());
		if (number < 11) {
			new AlertDialog.Builder(PhoneNumber.this)
					.setTitle("Asiacell")
					.setMessage("Please Insert Phone Number of 8 digit")
					.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();

		} else {

			splashScreen.editor.putInt("phone", number).commit();
			Log.e("url",
					""
							+ "http://iraqcomprojects.com/WEB%20SERVICES/mobile.php?no="
							+"077" + txt.getText().toString());
			new JSONAAsyncTaskPhone()
					.execute("http://iraqcomprojects.com/WEB%20SERVICES/mobile.php?no="
							+"077" + txt.getText().toString());
			Intent nextActivity = new Intent(PhoneNumber.this,
					MainActivity.class);
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(nextActivity);
		}

	}// End of alertBox
}// End of class PhoneNumber

class JSONAAsyncTaskPhone extends AsyncTask<String, Void, Boolean> {
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
			Log.e("post is","" +post);
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}// End of doInBackground
}// End of class JSONAAsyncTaskPhone