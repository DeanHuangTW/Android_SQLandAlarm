package com.example.alarmsampleactivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public final class AlarmReceiver extends BroadcastReceiver {
	private String TAG = "Dean";
    private String[] mAlarmTimeWayList = {"Toast" , "Vibrate", "sound"};
    private DBHelper DH = null;
    
    @Override
    public void onReceive(Context context,Intent intent){
    	Long eventId = intent.getLongExtra("EventID", 0);
    	String data = intent.getStringExtra("data");
    	Log.v(TAG, data);
    	
    	String notifyWay = getNotifyWayFromDatabase(context, eventId);    	    	
    	
    	if (notifyWay.equals(mAlarmTimeWayList[0])) {
    		Toast. makeText(context , "Alarm work!", Toast .LENGTH_LONG).show ();
    	} else if (notifyWay.equals(mAlarmTimeWayList[1])){
    		Vibrator myVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    		 myVibrator.vibrate(1000);
    	} else {
    		
    	}
    }
    
    /* 根據eventID取的Database中的Notify way*/
    private String getNotifyWayFromDatabase(Context context, Long eventId) {
    	DH = new DBHelper(context);
        SQLiteDatabase db = DH.getReadableDatabase();
        String[] columns = {"_EventID", "_NotifyWay"};
        String Select = "(_EventID=" + String.valueOf(eventId) +")";
        Cursor cursor = db.query("AlarmTable", columns, Select, null, null, null, null);
        
        String notifyWay = "";
        while (cursor.moveToNext()) {
        	notifyWay = cursor.getString(1);  // index 1 是notifyWay
        	Log.v(TAG, "notifyWay " + notifyWay);
        }
        
        DH.close();
        return notifyWay;
    }
}
