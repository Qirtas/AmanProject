package com.aman.amanapp;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;


public class DatabaseForApps {
	public static final String KEY_ROWID="_id";
	public static final String APP_NAME="APP_NAME";
	public static final String APP_PACKAGE_NAME="APP_PACKAGE_NAME";
	public static final String ACCESS_TIMES="ACCESS_TIMES";
	private static final String DATABASE_NAME="OHO_DATABASW";
	private static final String TABLE_NAME="APPS_TABLE";
	private static final int DATABASE_VERSION=1;
	private static int rowToUpdate = -555;
	private DbHelper dbHelper;
	private final Context context;
	private SQLiteDatabase database;
	
	private class DbHelper extends SQLiteOpenHelper
	{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+
			KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					APP_NAME+" TEXT NOT NULL, "+ APP_PACKAGE_NAME+ " TEXT NOT NULL, "+
					 ACCESS_TIMES +" TEXT NOT NULL);"
					);
			
	}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROPTABLE IF EXISTS "+ TABLE_NAME);

			onCreate(db);
					
		}
		
		
	}
	 public DatabaseForApps(Context c)
	 {
		 context=c;
		 
	 }
	 
	 public DatabaseForApps open()
	 {
		dbHelper=new DbHelper(context);
		database= dbHelper.getWritableDatabase();
		return this; 
	 }
	 
	 public void close()
	 {
		dbHelper.close(); 
	 }

	public void insertRecord(AppItem appItem) {
		AppItem temp = DoesRecordExist(appItem);
		ContentValues contentValues = new ContentValues();
		contentValues.put(APP_NAME, appItem.getName());
		contentValues.put(APP_PACKAGE_NAME, appItem.getPacakgeName());
		if(temp==null) {

			contentValues.put(ACCESS_TIMES, "1");
			database.insert(TABLE_NAME, null, contentValues);
			Toast.makeText(context,"Inserted",Toast.LENGTH_LONG).show();
		}
		else
		{
			int accessTime= Integer.parseInt(temp.getAccessTimes());
			accessTime +=1;
			contentValues.put(ACCESS_TIMES,accessTime+"");
			database.update(TABLE_NAME,contentValues,KEY_ROWID+"="+rowToUpdate,null);
			Toast.makeText(context,"updated "+accessTime,Toast.LENGTH_LONG).show();

		}
		
		
		
	}

	public void insertRecordFirstTime(AppItem appItem) {
		AppItem temp = DoesRecordExist(appItem);
		if(temp==null) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(APP_NAME, appItem.getName());
			contentValues.put(APP_PACKAGE_NAME, appItem.getPacakgeName());
			contentValues.put(ACCESS_TIMES, "0");
			database.insert(TABLE_NAME, null, contentValues);
		}



	}

	 
	 
	 public ArrayList<AppItem> getData()
	 {

		 ArrayList<AppItem> Result = new ArrayList<AppItem>();
		 String []columns={KEY_ROWID,APP_NAME,APP_PACKAGE_NAME,ACCESS_TIMES};
		 Cursor c=database.query(TABLE_NAME, columns,null, null, null, null, ACCESS_TIMES+" DESC");
		 int iIndex=c.getColumnIndex(KEY_ROWID);
		 int iAppName=c.getColumnIndex(APP_NAME);
		 int iAppPkgName=c.getColumnIndex(APP_PACKAGE_NAME);
		 int iAccTime=c.getColumnIndex(ACCESS_TIMES);
		 String appName,appPackageName,accessTimes;
		 int id;
		 //UserActivity obj=new UserActivity();
		 for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		 {
			 try {
				 appName = c.getString(iAppName);
				 appPackageName = c.getString(iAppPkgName);
				 accessTimes = c.getString(iAccTime);
				 Drawable icon = context.getPackageManager().getApplicationIcon(appPackageName);
				 Result.add(new AppItem(appName,icon,appPackageName,accessTimes));
			 }
			 catch (Exception ex)
			 {

			 }
		 }
		 
		return Result;
		 
		 
	 }

	public AppItem DoesRecordExist(AppItem appItem)
	{
		String []columns={KEY_ROWID,APP_NAME,APP_PACKAGE_NAME,ACCESS_TIMES};
		Cursor c=database.query(TABLE_NAME, columns,APP_NAME+"=? AND "+APP_PACKAGE_NAME+"=?", new String[]{appItem.getName(),appItem.getPacakgeName()}, null, null, null);
		int iIndex=c.getColumnIndex(KEY_ROWID);
		int iAppName=c.getColumnIndex(APP_NAME);
		int iAppPkgName=c.getColumnIndex(APP_PACKAGE_NAME);
		int iAccTime=c.getColumnIndex(ACCESS_TIMES);
		String appName,appPackageName,accessTimes;
		int id;
		//UserActivity obj=new UserActivity();
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			appName=c.getString(iAppName);
			appPackageName= c.getString(iAppPkgName);
			id = c.getInt(iIndex);
			rowToUpdate = id;
			accessTimes=c.getString(iAccTime);
			appItem.setAccessTimes(accessTimes);
			return appItem;
		}
		return null;
	}


}
