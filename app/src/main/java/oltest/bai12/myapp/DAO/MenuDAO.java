package oltest.bai12.myapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.Database.CreateDatabase;

public class MenuDAO {
    SQLiteDatabase database;

    public MenuDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    // function add a new food
    public boolean AddFood(Menu menu)
    {
        ContentValues contentValues = new ContentValues();
        //contentValues.put(CreateDatabase.TABLE_FOODID,menu.getFoodId());
        contentValues.put(CreateDatabase.TABLE_FOODNAME,menu.getFoodName());
        contentValues.put(CreateDatabase.TABLE_FOODCOST,menu.getFoodCost());
        contentValues.put(CreateDatabase.TABLE_FOODIMAGE,menu.getFoodImage());

        long check = database.insert(CreateDatabase.FOOD_TABLE,null,contentValues);
        if (check != 0 )
        {
            return true;
        }else return false;
    }
    // function detele a food
    public boolean DeleteFood(int id)
    {
        long check = database.delete(CreateDatabase.FOOD_TABLE,CreateDatabase.TABLE_FOODID + " = " +id,null);
        if (check != 0 )
        {
            return true;
        }else return false;
    }
    // function edit food
    public boolean UpdateFood(Menu menu, int id)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_FOODID,menu.getFoodId());
        contentValues.put(CreateDatabase.TABLE_FOODNAME,menu.getFoodName());
        contentValues.put(CreateDatabase.TABLE_FOODCOST,menu.getFoodCost());
        contentValues.put(CreateDatabase.TABLE_FOODIMAGE,menu.getFoodImage());

        long check = database.update(CreateDatabase.FOOD_TABLE,contentValues,CreateDatabase.TABLE_FOODID + " = " + id,null);
        if (check != 0 )
        {
            return true;
        }else return false;
    }

   @SuppressLint("Range")
    public List<Menu> getAllMenu()
    {
        List<Menu> menulist = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.FOOD_TABLE;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Menu menu = new Menu();
            menu.setFoodId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_FOODID)));
            menu.setFoodName(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_FOODNAME)));
            menu.setFoodCost(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_FOODCOST)));
            menu.setFoodImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TABLE_FOODIMAGE)));

            menulist.add(menu);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return menulist;
    }

    @SuppressLint("Range")
    public Menu getFoodbyID(int id)
    {
        Menu menu = new Menu();
        String query = " SELECT * FROM " + CreateDatabase.FOOD_TABLE + " WHERE " + CreateDatabase.TABLE_FOODID + " = " + id;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            menu.setFoodId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_FOODID)));
            menu.setFoodName(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_FOODNAME)));
            menu.setFoodCost(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_FOODCOST)));
            menu.setFoodImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TABLE_FOODIMAGE)));

            cursor.moveToNext();
        }
        return menu;

    }

}
