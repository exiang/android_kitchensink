
package com.mobeegen.kitchensink;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQLActivity extends Activity
{
	final Activity activity = this;

	DBHandler dbh;
	EditText etId, etItem1, etItem2, etItemNum;
	Button btAdd, btUpdate, btLoad, btDelete, btGetCount, btDelDb, btnExists;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sql_activity);
		dbh = new DBHandler(this);
		etItem1 = (EditText) findViewById(R.id.et_item1);
		etItem2 = (EditText) findViewById(R.id.et_item2);
		etItemNum = (EditText) findViewById(R.id.et_itemnum);
		etId = (EditText) findViewById(R.id.et_id);
		btAdd = (Button) findViewById(R.id.btn_add);
		btLoad = (Button) findViewById(R.id.btn_load);
		btUpdate = (Button) findViewById(R.id.btn_update);
		btDelete = (Button) findViewById(R.id.btn_delete);
		btGetCount = (Button) findViewById(R.id.btn_getcount);
		btDelDb = (Button) findViewById(R.id.btn_deldatabase);
		btnExists = (Button) findViewById(R.id.btn_exists);

		dbh.open();

		btAdd.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SQLData data = new SQLData();
				data.item1 = etItem1.getText().toString();
				data.item2 = etItem2.getText().toString();
				data.itemnum = Integer.parseInt(etItemNum.getText().toString());
				dbh.addData(data);
			}
		});

		btLoad.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SQLData data = new SQLData();
				int id = Integer.parseInt(etId.getText().toString());
				List<SQLData> dataList = new ArrayList<SQLData>();
				dataList = dbh.getData("*");
				if (dbh.exists(id))
				{
					data = dataList.get(id);

					String display;
					display = "Item1: " + data.item1 + "\n";
					display += "Item2: " + data.item2 + "\n";
					display += "Itemnum: " + data.itemnum;

					Toast.makeText(activity, display, Toast.LENGTH_LONG).show();
				} else
				{
					Toast.makeText(activity, "Row " + id + " doesn't exist",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btUpdate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SQLData data = new SQLData();
				int id = Integer.parseInt(etId.getText().toString());

				if (dbh.exists(id))
				{
					data.item1 = etItem1.getText().toString();
					data.item2 = etItem2.getText().toString();
					data.itemnum = Integer.parseInt(etItemNum.getText()
							.toString());
					dbh.updateData(id, data);
				} else
				{
					Toast.makeText(activity, "Row " + id + " doesn't exist",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btDelete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int id = Integer.parseInt(etId.getText().toString());

				if (dbh.exists(id))
				{
					dbh.deleteData(id);
				} else
				{
					Toast.makeText(activity, "Row " + id + " doesn't exist",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btGetCount.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int count;
				count = dbh.getCount();
				Toast.makeText(activity, "row count: " + count,
						Toast.LENGTH_SHORT).show();
			}
		});

		btDelDb.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dbh.deleteDatabase();
			}
		});

		btnExists.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int id = Integer.parseInt(etId.getText().toString());
				if (dbh.exists(id))
				{
					Toast.makeText(activity, "Row " + id + " exists",
							Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(activity, "Row " + id + " doesn't exist",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onDestroy()
	{
		dbh.close();
		super.onDestroy();
	}

}
