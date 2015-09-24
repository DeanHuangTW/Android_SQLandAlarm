package com.example.alarmsampleactivity;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener{
	private String TAG = "Dean";
	
	private String[] mAlarmTimeWayList = {"Toast" , "Vibrate", "sound"};
	private String mCheckWay;
	
	private Button mBtnAddEvent;
	private Button mBtnModifyEvent;
	private Button mBtnCancelEvent;
	private int mStartHour;
	private int mStartMinute;
	private TimePicker mTimePicker;
	private EditText mEventId;
	private TextView mAlarmList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBtnAddEvent = (Button) findViewById(R.id.button1);
		mBtnAddEvent.setOnClickListener(this);
		mBtnModifyEvent = (Button) findViewById(R.id.button2);
		mBtnModifyEvent.setOnClickListener(this);
		mBtnCancelEvent = (Button) findViewById(R.id.button3);
		mBtnCancelEvent.setOnClickListener(this);
		
		mAlarmList = (TextView) findViewById(R.id.alarmlist);
		
		mTimePicker = (TimePicker) findViewById(R.id.timePicker1);
		mTimePicker.setIs24HourView(true);
		
		mEventId = (EditText) findViewById(R.id.editText1);
	}

	@Override
	public void onClick(View v) {
		String id = mEventId.getText().toString();
		mStartHour = mTimePicker.getCurrentHour();
		mStartMinute = mTimePicker.getCurrentMinute();			
		Log.d(TAG, mStartHour + ":" + mStartMinute);
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(2015, 8, 24, mStartHour, mStartMinute);
		long startMillis = beginTime.getTimeInMillis();
		if (v == mBtnAddEvent) {					
			AlarmService newAlarm = new AlarmService(this, Integer.valueOf(id));
			newAlarm.setAlarm(startMillis, 60, "Vibrate");
			
			mAlarmList.setText(newAlarm.showAllAlarm());
		} else if (v == mBtnModifyEvent) {
			AlarmService newAlarm = new AlarmService(this, Integer.valueOf(id));
			newAlarm.updateAlarm(startMillis, 60, "Toast");
			
			mAlarmList.setText(newAlarm.showAllAlarm());
		} else if (v == mBtnCancelEvent) {
			AlarmService newAlarm = new AlarmService(this, Integer.valueOf(id));
			newAlarm.cancelAlarm();
			
			mAlarmList.setText(newAlarm.showAllAlarm());
		}
	}
	
}
