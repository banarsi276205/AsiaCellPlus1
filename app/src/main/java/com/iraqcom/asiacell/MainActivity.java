//Author Android Developer - Rishabh Gupta

/*langvalue = 0; For English=English
 *langvalue = 1; For Arabic=العربية
 *langvalue = 2; For Kurdish=كوردى*/


package com.iraqcom.asiacell;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    ImageButton navButton;
    ImageButton navButtonInside;
    String[] mLanguageTitles;
    DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    public static float width;
    public static float height;
    static ImageView imageLogo;

    RelativeLayout drawerLayout;
    static TextView notification;
    public static ImageButton switchnotify;
    Bundle outState;

    boolean switchValue;
    AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
//		android_id = Secure.getString(getContentResolver(),Secure.ANDROID_ID); // Device ID
        imageLogo = (ImageView) findViewById(R.id.imageView1); //Logo on top right side

        new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/insertid.php?id=" + splashScreen.regId + "&langid=" + (splashScreen.langvalue + 1)); //To send Device Id and Language on web Services

        deviceSize();//Device Size
        notifyAndSwitchButton(); // For Notification and Switch Button Status
        navigationButton();    //For navigation toggle button
        //networkCheck();

        drawerLayout = (RelativeLayout) findViewById(R.id.drawerlayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerLayout.getLayoutParams();
                params.width = (int) (MainActivity.width / 2) + 25;
                drawerLayout.setLayoutParams(params);
            }
        }, 200);

        //DRAWER LANGUAGE LIST AND SENDING OF ANDROID ID AND LANGUAGE ON WEB SERVICES
        mDrawerList = (ListView) findViewById(R.id.left_drawer);//DrawerAdapter

        mLanguageTitles = getResources().getStringArray(R.array.nav_drawer_items);//placing Language Titles on list in drawer

        // mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mLanguageTitles));// Set the adapter for the list view

        mDrawerList.setAdapter(new DrawerAdapter(this, mLanguageTitles));// Set the adapter for the list view

        mDrawerList.setDivider(null);
        mDrawerList.setPadding(0, (int) (MainActivity.width / 8), (int) (MainActivity.width / 32) + 12, 0);
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if (position < 3) {
                    splashScreen.editor.putInt("lang", position).commit();
                }
                if (position == 0) {
                    if (switchValue != false)
                        new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/insertid.php?id=" + splashScreen.regId + "&langid=" + (position + 1));
                    imageLogo.setImageResource(R.drawable.ic_logo_english);
                } else if (position == 1) {
                    if (switchValue != false)
                        new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/insertid.php?id=" + splashScreen.regId + "&langid=" + (position + 1));
                    imageLogo.setImageResource(R.drawable.ic_logo_arabic);
                } else if (position == 2) {
                    if (switchValue != false)
                        new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/insertid.php?id=" + splashScreen.regId + "&langid=" + (position + 1));
                    imageLogo.setImageResource(R.drawable.ic_logo_kurtish);
                }
                displayView(position);//language selection at drawer
            }
        });


        if (savedInstanceState == null) {
            if (splashScreen.langvalue == 0)
                imageLogo.setImageResource(R.drawable.ic_logo_english);
            else if (splashScreen.langvalue == 1)
                imageLogo.setImageResource(R.drawable.ic_logo_arabic);
            else if (splashScreen.langvalue == 2)
                imageLogo.setImageResource(R.drawable.ic_logo_kurtish);
            displayView(splashScreen.langvalue);
        }
        imageLogo.getLayoutParams().height = (int) (MainActivity.width / 5);
        imageLogo.getLayoutParams().width = (int) (MainActivity.width / 3);
        imageLogo.setScaleType(ScaleType.FIT_XY);


    }

    /*private void networkCheck()
    {
        if(isNetworkAvailable()==false)
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Check Connection");
            dialog.show();
            dialog.setCancelable(true);
        }
    }
*/
    private void navigationButton() {
        navButton = (ImageButton) findViewById(R.id.imageButton1);
        navButton.setScaleType(ScaleType.FIT_XY);
        navButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        mDrawerLayout.openDrawer(Gravity.START);
                    }
                }, 100);


            }
        });


        navButtonInside = (ImageButton) findViewById(R.id.imagebuttoninside);
        navButtonInside.setScaleType(ScaleType.FIT_XY);
        navButtonInside.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawers();
                    }
                }, 200);


            }
        });
    }

    private void notifyAndSwitchButton() {
        notification = (TextView) findViewById(R.id.notification);
        notification.setTextSize(20);
        notification.setPadding(0, (int) (width / 4), (int) (MainActivity.width / 32) + 12, 0);

        switchnotify = (ImageButton) findViewById(R.id.switch1);
        switchnotify.setScaleType(ScaleType.CENTER_INSIDE);
        switchnotify.setPadding(0, 0, (int) (MainActivity.width / 32) + 12, 0);
        switchnotify.setImageResource(R.drawable.noti_off);
        switchnotify.setBackgroundColor(Color.TRANSPARENT);
        switchValue = splashScreen.settings.getBoolean("switch", true);

        if (switchValue == true)
            switchnotify.setImageResource(R.drawable.noti_on);
        else
            switchnotify.setImageResource(R.drawable.noti_off);

        switchnotify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (switchValue == false) {
                    new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/insertid.php?id=" + splashScreen.regId + "&langid=" + (splashScreen.langvalue + 1));
                    switchnotify.setImageResource(R.drawable.noti_on);
                    switchValue = true;
                    splashScreen.editor.putBoolean("switch", switchValue).commit();
                } else if (switchValue == true) {
                    new JSONAsyncTaskDevice().execute("http://iraqcomprojects.com/WEB%20SERVICES/deleteid.php?id=" + splashScreen.regId);
                    switchnotify.setImageResource(R.drawable.noti_off);
                    switchValue = false;
                    splashScreen.editor.putBoolean("switch", switchValue).commit();
                }
            }
        });
    }

    private void deviceSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawers();
        if (getFragmentManager().findFragmentByTag("EnglishLanguage").isVisible()) {
            if (splashScreen.langvalue == 0) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Close")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

            } else if (splashScreen.langvalue == 1) {
                new AlertDialog.Builder(MainActivity.this).setTitle("أغلاق")
                        .setMessage("هل تريد الخروج من التطبيق؟")
                        .setPositiveButton("لا", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("نعم   ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);
                            }
                        })
                        .show();
            } else if (splashScreen.langvalue == 2) {
                new AlertDialog.Builder(MainActivity.this).setTitle("چوونه‌ده‌ره‌وه")
                        .setMessage("دڵنیایت ده‌ته‌وێت وازبهێنیت؟")
                        .setPositiveButton("نه‌خێر", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("به‌ڵێ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);
                            }
                        })
                        .show();
            }
        } else
            getFragmentManager().popBackStackImmediate();
    }
	
/*	private boolean isNetworkAvailable() 
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}*/

    public void displayView(int position) {
        if (position < 3) {
            Fragment fragment = new EnglishLanguage(this, position);
            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "EnglishLanguage").commit();
                mDrawerList.setItemChecked(position, false);
                mDrawerLayout.closeDrawers();

            }
        } else if (position == 3) {

            Fragment fragment = new PrivacyPolicyFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "EnglishLanguage").commit();

            mDrawerList.setItemChecked(position, false);
            mDrawerLayout.closeDrawers();


        } else if (position == 4) {
            Fragment fragment = new TermConditionFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "EnglishLanguage").commit();
            mDrawerList.setItemChecked(position, false);
            mDrawerLayout.closeDrawers();
        }


    }

}
			
