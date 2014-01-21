package com.smart.gratuity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GratuityCalcActivity extends Activity {

	private long gratuity = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gratuity_calc_activity);
	}

	/**
	 * This method will be invoked when the copy button is clicked.
	 * This method mapping is given in the gratuity_calc_activity.xml file.
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void copyGratuity(View view)
	{

		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
		    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		    clipboard.setText(gratuity+"");
		} else {
		    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
		    android.content.ClipData clip = android.content.ClipData.newPlainText("label", gratuity+"");
		    clipboard.setPrimaryClip(clip);
		}
		
		Toast toast = Toast.makeText(this, "Gratuity Amount '"+gratuity+"' copied into clipboard.", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * This method will be invoked when the calculate button is clicked.
	 * This method mapping is given in the gratuity_calc_activity.xml file.
	 * @param view
	 */
	public void calculateGratuity(View view)
	{
		long basicPay = Long.parseLong(((EditText)findViewById(R.id.basic_pay)).getText().toString());
		long da = Long.parseLong(((EditText)findViewById(R.id.da)).getText().toString());
		long total = basicPay + da;
		long years = Long.parseLong(((EditText)findViewById(R.id.years)).getText().toString());
		long months = Long.parseLong(((EditText)findViewById(R.id.months)).getText().toString());
		if(months>6)
		{
			years++;
		}
		if(!validateForm(years,months))
		{
			return;
		}
		gratuity = (total*15*years)/26;
		Log.e("Gratuity:", gratuity+"");
		((TextView)findViewById(R.id.gratuity_value)).setVisibility(View.VISIBLE);
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			((TextView)findViewById(R.id.gratuity_value)).setText("Total Gratuiry : "+"Rs. "+gratuity);	
		}
		else
		{
			((TextView)findViewById(R.id.gratuity_value)).setText("Total Gratuiry : "+"\u20B9 "+gratuity);
		}
		
	}

	private boolean validateForm(long years, long months) {

		if(years<5)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You must be in service for atleast 5 years to be eligible for gratuity.");
			builder.setTitle("Warning");
			builder.setPositiveButton("OK", 
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		if(months<0 || months>12)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(GratuityCalcActivity.this);
			builder.setMessage("Month should be between 0 and 12.");
			builder.setTitle("Warning");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		return true;
	}
}
