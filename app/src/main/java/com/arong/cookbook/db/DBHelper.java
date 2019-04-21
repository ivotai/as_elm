package com.arong.cookbook.db;

import java.util.ArrayList;

import com.arong.app.ICSAppFood;
import com.arong.cookbook.bean.Food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TABLE_FOOD = "TABLE_FOOD";
	private static DBHelper instance;
	private static int version = 3;

	public DBHelper(Context context) {
		super(context, ICSAppFood.dbPath, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("-----------------------------------------来了");
		createTables(db, prepareTableInfo());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}
		return instance;
	}

	private ArrayList<Table> prepareTableInfo() {
		ArrayList<Table> tables = new ArrayList<Table>();
		Table t;

		t = new Table(TABLE_FOOD); // 通讯录
		t.addFeild(new Feild("NAME", "text")); // 姓名
		t.addFeild(new Feild("TYPE", "text")); // 号码
		t.addFeild(new Feild("STAR", "text")); // 号码
		t.addFeild(new Feild("PRICE", "text")); // 号码

		tables.add(t);

		return tables;
	}

	/**
	 * 创建数据表
	 * 
	 * @param db
	 *            数据库名称
	 * @param tables
	 *            所要创建的数据库表列表
	 */
	private void createTables(SQLiteDatabase db, ArrayList<Table> tables) {
		db.beginTransaction();
		for (Table table : tables) {
			db.execSQL(table.createSQL());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	/**
	 * 添加菜单
	 * 
	 * @param tz
	 */
	public long addFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("NAME", food.name);
		cv.put("TYPE", food.type);
		cv.put("PRICE", food.price);
		cv.put("STAR", food.star);

		long insert = db.insert(DBHelper.TABLE_FOOD, null, cv);
		db.close();
		return insert;
	}

	/**
	 * 修改菜单
	 * 
	 * @param tz
	 */
	public void updateFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("NAME", food.name);
		cv.put("TYPE", food.type);
		cv.put("PRICE", food.price);
		cv.put("STAR", food.star);

		db.insert(DBHelper.TABLE_FOOD, null, cv);
		db.close();

	}

	/**
	 * 删除菜单
	 * 
	 * @param food
	 */
	public int deleteFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

		int i = db.delete(TABLE_FOOD, "NAME = ?",
				new String[] { food.name });
		db.close();
		return i;
	}

	/**
	 * 获取所有菜单
	 * 
	 * @param tz
	 */
	public ArrayList<Food> getAllFood() {
		ArrayList<Food> rlts = new ArrayList<Food>();
		SQLiteDatabase db = this.getReadableDatabase();
		String whereClause = null;
		String[] columns = { "NAME", "TYPE", "PRICE", "STAR" };
		String orderby = "TYPE desc";

		Cursor cursor = db.query(TABLE_FOOD, columns, whereClause, null, null,
				null, orderby, null);
		Food food = null;
		while (cursor.moveToNext()) {
			food = new Food();
			food.name = cursor.getString(cursor.getColumnIndex("NAME"));
			food.type = cursor.getString(cursor.getColumnIndex("TYPE"));
			food.price = cursor.getString(cursor.getColumnIndex("PRICE"));
			food.star = cursor.getString(cursor.getColumnIndex("STAR"));

			rlts.add(food);
		}
		cursor.close();
		db.close();
		return rlts;
	}
}
