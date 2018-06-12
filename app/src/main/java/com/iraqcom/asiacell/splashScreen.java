//Author Android Developer - Rishabh Gupta
package com.iraqcom.asiacell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import java.util.Timer;
import java.util.TimerTask;

import static com.iraqcom.asiacell.CommonUtilities.EXTRA_MESSAGE;

//....

public class splashScreen extends Activity {
    public static SharedPreferences settings;
    static Editor editor;
    public static int langvalue;
    int phonestatus;
    TimerTask task;
    public static String regId, message;
    ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager();
    public static int advertisement;

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove title bar
        setContentView(R.layout.splash); // our layout xml
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                CommonUtilities.DISPLAY_MESSAGE_ACTION));
        // Get GCM registration id
        regId = GCMRegistrar.getRegistrationId(this);
        Log.e("Reg Id", "-" + regId);

        // Check if regid already presents
        if (regId.equals("")) {
            GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    regId = GCMRegistrar.getRegistrationId(splashScreen.this);
                    Log.e("Reg Id", "-" + regId);
                }
            }, 5000);
        }
        regId = GCMRegistrar.getRegistrationId(this);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(splashScreen.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = splashScreen.settings.edit();
        phonestatus = settings.getInt("phone", 0);
        langvalue = settings.getInt("lang", 0);

    }// End of onCreate Method

    private void task() {
        // TODO Auto-generated method stub
        task = new TimerTask() {

            @Override
            public void run() {
                if (phonestatus == 0 && langvalue == 0) {
                    Intent nextActivity = new Intent(splashScreen.this,
                            LanguageSelection.class);
                    startActivity(nextActivity);
                } else {
                    Intent nextActivity = new Intent(splashScreen.this,
                            MainActivity.class);
                    startActivity(nextActivity);
                }
                splashScreen.this.finish();
            }

        };
        new Timer().schedule(task, 3000);
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegist Receiver Error", "" + e.getMessage());
        }
        super.onDestroy();
    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message depending upon your app
             * requirement For now i am just displaying it on the screen
             * */

            // Showing received message
            Toast.makeText(getApplicationContext(),
                    "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();

        try {
            if (getIntent().getStringExtra("fromnotification") != null) {
                Log.e("asd", "asd " + getIntent().getExtras().getString("fromnotification",
                        "false"));
                if (getIntent().getExtras().getString("fromnotification",
                        "false").equals("true")) {
//					getIntent().removeExtra("fromnotification");
                    final AlertDialog alertDialog = new AlertDialog.Builder(
                            this).create();
                    alertDialog.setTitle("AsiaCell");
                    Log.e("asd", "asd " + message);
//					if (getIntent().getStringExtra("message") != null)

                    alertDialog.setMessage("" + message);
                    Log.e("asd", "asdfghj ");
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    task();
                                }
                            });

                    alertDialog.show();
                }
            } else {
                task();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}// End of class
