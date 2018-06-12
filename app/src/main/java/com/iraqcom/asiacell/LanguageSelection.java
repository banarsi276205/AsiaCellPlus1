//Author Android Developer - Rishabh Gupta

/*langvalue = 0; For English=English
 *langvalue = 1; For Arabic=العربية
 *langvalue = 2; For Kurdish=كوردى*/
package com.iraqcom.asiacell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.iraqcom.asiacell.R;

public class LanguageSelection extends Activity
{
	Typeface type;//For Font Style
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title bar
		setContentView(R.layout.languageselection);// our layout xml
	
		splashScreen.langvalue = splashScreen.settings.getInt("lang", 0);
	
		RelativeLayout relative = (RelativeLayout)findViewById(R.id.relativeSelection);
		relative.setBackgroundColor(Color.rgb(100, 38, 41));
	
		//Arabic Button
		Button buttonArabic = (Button)findViewById(R.id.Buttonarabic);
		type=Typeface.createFromAsset(this.getAssets(),"GE_SS_Two_Light.otf");
	
		
			buttonArabic.setTypeface(type);
			buttonArabic.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					splashScreen.editor.putInt("lang", 1).commit();
					nextActivity();
				}
			});
			
		//kurdish Button	
		Button buttonkurtish = (Button)findViewById(R.id.Buttonkurtish);
		type=Typeface.createFromAsset(this.getAssets(),"AdobeArabic-Regular.otf");
			buttonkurtish.setTypeface(type);
			buttonkurtish.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					splashScreen.editor.putInt("lang", 2).commit();
					nextActivity();
				}
			});
		
		//English Button
		Button buttonEnglish = (Button)findViewById(R.id.Buttonenglish);
			type=Typeface.createFromAsset(this.getAssets(),"EurostileLTStd-Cn.otf");
			buttonEnglish.setTypeface(type);
			buttonEnglish.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v)
				{
					splashScreen.editor.putInt("lang", 0).commit();
					nextActivity();
					
				}
			});
	}//End of onCreate Method

	protected void nextActivity() 
	{
		Intent nextActivity = new Intent(LanguageSelection.this, PhoneNumber.class);
        startActivity(nextActivity);
		
	}//End of nextActivity
}//End of class

