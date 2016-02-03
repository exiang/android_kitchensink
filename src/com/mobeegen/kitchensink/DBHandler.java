
package com.mobeegen.kitchensink;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper
{
	static final String TAG = "DBHandler";
	static final int DATABASE_VERSION = 1;
	static final String DATABASE_NAME = "database.db";
	static final String TABLE_NAME = "sample_table";
	static final String KEY_ID = "id";
	static final String ITEM1_NAME = "item1text";
	static final String ITEM2_NAME = "item2text";
	static final String ITEMNUM_NAME = "itemnum";

	private SQLiteDatabase db;
	private final Context context;

	public DBHandler(Context c)
	{
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
		context = c;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.v(TAG, "Creating all SQL tables...");
		try
		{
			final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
					+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ ITEM1_NAME + " TEXT," + ITEM2_NAME + " TEXT,"
					+ ITEMNUM_NAME + " NUMERIC" + ");";
			db.execSQL(CREATE_TABLE);
		} catch (SQLiteException ex)
		{
			Log.e("Create table exception", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TAG, "Upgrading from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public void deleteDatabase()
	{
		context.deleteDatabase(DATABASE_NAME);
		Log.v(TAG, "Database deleted");
	}

	public void close()
	{
		db.close();
		Log.v(TAG, "Database closed");
	}

	public void open() throws SQLiteException
	{
		try
		{
			db = getWritableDatabase();
			Log.v(TAG, "Writeable database opened");
		} catch (SQLiteException ex)
		{
			Log.w(TAG, ex.getMessage());
			db = getReadableDatabase();
			Log.v(TAG, "Readable database opened");
		}
	}

	public void addData(SQLData data)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ITEM1_NAME, data.item1);
		values.put(ITEM2_NAME, data.item2);
		values.put(ITEMNUM_NAME, data.itemnum);
		db.insert(TABLE_NAME, null, values);
		Log.v(TAG, "Data added to " + TABLE_NAME);
	}

	// Gets all
	public List<SQLData> getData(String query)
	{
		List<SQLData> mDataList = new ArrayList<SQLData>();

		String selectQuery = "SELECT " + query + " FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst())
		{
			// insert an empty data. Otherwise, first entry is 0, which causes
			// confusion later.
			mDataList.add(new SQLData());
			do
			{
				SQLData mdata = new SQLData();
				mdata.item1 = cursor.getString(cursor
						.getColumnIndex(ITEM1_NAME));
				mdata.item2 = cursor.getString(cursor
						.getColumnIndex(ITEM2_NAME));
				mdata.itemnum = cursor.getInt(cursor
						.getColumnIndex(ITEMNUM_NAME));
				mDataList.add(mdata);
			} while (cursor.moveToNext());

			Log.v(TAG, "Data queried");
		} else
		{
			Log.e(TAG, "Record doesn't exist");
		}

		cursor.close();
		return mDataList;
	}

	public int getCount()
	{
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		// return count
		Log.v(TAG, "Got count");
		return count;
	}

	public void updateData(int id, SQLData mdata)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ITEM1_NAME, mdata.item1);
		values.put(ITEM2_NAME, mdata.item2);
		values.put(ITEMNUM_NAME, mdata.itemnum);

		// updating row
		db.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[]{Integer.toString(id)});
		Log.v(TAG, "Data at ID" + KEY_ID + " updated");
	}

	public void deleteData(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[]{Integer.toString(id)});
		Log.v(TAG, "Data at ID" + KEY_ID + " deleted");
	}

	public boolean exists(int id)
	{
		Cursor cursor = db.rawQuery("select 1 from " + TABLE_NAME + " where "
				+ KEY_ID + "=" + id, null);
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}

}