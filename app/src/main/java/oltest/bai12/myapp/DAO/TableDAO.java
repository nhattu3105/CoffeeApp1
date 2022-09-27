package oltest.bai12.myapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.Database.CreateDatabase;

public class TableDAO {
    SQLiteDatabase Database;

    public  TableDAO(Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        Database = createDatabase.open();
    }

    public boolean AddTable(Table table)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_NAME,table.getTableName());

        long check = Database.insert(CreateDatabase.TABLE_VIEW,null,contentValues);
        if (check != 0 )
        {
            return true;
        }else return false;
    }

    public boolean DeleteTable(int id)
    {
        long check = Database.delete(CreateDatabase.TABLE_VIEW,CreateDatabase.TABLE_ID + " = " +id,null);
        if (check != 0 )
        {
            return true;
        }else return false;
    }

    public boolean UpdateTable(Table table, int id)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_ID,table.getTableID());
        contentValues.put(CreateDatabase.TABLE_NAME,table.getTableName());
        ;
        long check = Database.update(CreateDatabase.TABLE_VIEW,contentValues,CreateDatabase.TABLE_ID + " = " + id,null);
        if (check != 0 )
        {
            return true;
        }else return false;
    }

    @SuppressLint("Range")
    public List<Table> getAllTable()
    {
        List<Table> tablelist = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TABLE_VIEW;
        Cursor cursor = Database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Table table = new Table();
            table.setTableID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_ID)));
            table.setTableName(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_NAME)));


            tablelist.add(table);
            cursor.moveToNext();
        }
        cursor.close();
        Database.close();
        return tablelist;
    }

    @SuppressLint("Range")
    public String GetNameById(int maban)
    {
        String tenban = "";
        String query = "SELECT * FROM " + CreateDatabase.TABLE_VIEW + " WHERE " + CreateDatabase.TABLE_ID + " = '" +maban+ "' ";
        Cursor cursor = Database.rawQuery(query,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_NAME));
            cursor.moveToNext();
        }

        return tenban;

    }
}
