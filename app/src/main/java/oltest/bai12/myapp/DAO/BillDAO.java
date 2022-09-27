package oltest.bai12.myapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import oltest.bai12.myapp.DTO.Bill;
import oltest.bai12.myapp.Database.CreateDatabase;

public class BillDAO {
    SQLiteDatabase database;
    public BillDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonDat(Bill bill){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.BILL_TABLE_ID,bill.getTableID());
        contentValues.put(CreateDatabase.SUM,bill.getSum());

        long madondat = database.insert(CreateDatabase.BILL_TABLE,null,contentValues);

        return madondat;
    }

    @SuppressLint("Range")
    public long LayMaDonTheoMaBan(int maban){
        long magoimon = 0;
        String query = " SELECT * FROM " +CreateDatabase.BILL_TABLE+ " WHERE " +CreateDatabase.BILL_TABLE_ID+ " = ' " +maban+ " ' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getLong(cursor.getColumnIndex(CreateDatabase.BILL_ID));
            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean UpdateTongTienDonDat(int madondat,int tongtien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.SUM,tongtien);
        long ktra  = database.update(CreateDatabase.BILL_TABLE,contentValues,
                CreateDatabase.BILL_ID+" = "+madondat,null);
        if(ktra != 0){
            return true;
        }else{
            return false;
        }
    }
}
