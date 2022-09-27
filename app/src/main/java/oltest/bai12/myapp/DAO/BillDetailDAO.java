package oltest.bai12.myapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import oltest.bai12.myapp.DTO.BillDetail;
import oltest.bai12.myapp.DTO.Payment;
import oltest.bai12.myapp.Database.CreateDatabase;

public class BillDetailDAO {
    SQLiteDatabase database;
    public BillDetailDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database=createDatabase.open();
    }

    public boolean ThemChiTietDonDat(BillDetail billDetail){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.AMOUT,billDetail.getSl());
        contentValues.put(CreateDatabase.BILL_ID1,billDetail.getBillID());
        contentValues.put(CreateDatabase.FOOD_ID,billDetail.getFoodID());

        long ktra = database.insert(CreateDatabase.BILL_DETAIL_TABLE,null,contentValues);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean KiemTraMonTonTai(int madondat, int mamon){
        String query = "SELECT * FROM " +CreateDatabase.BILL_DETAIL_TABLE+ " WHERE " + CreateDatabase.FOOD_ID +
                " = " +mamon+ " AND " + CreateDatabase.BILL_ID1 + " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        return cursor.getCount() != 0;
    }

    public  boolean deleteMonAn(String madonhang, String mamonan){
        return database.delete(CreateDatabase.BILL_DETAIL_TABLE,CreateDatabase.BILL_ID1 +"=" +madonhang
                + " AND " + CreateDatabase.FOOD_ID +"=" +mamonan ,null) > 0;
    }

    @SuppressLint("Range")
    public int LaySLMonTheoMaDon(int madondat, int mamon){
        int soluong = 0;
        String query = "SELECT * FROM " +CreateDatabase.BILL_DETAIL_TABLE+ " WHERE " + CreateDatabase.FOOD_ID+
                " = " +mamon+ " AND " + CreateDatabase.BILL_ID1+ " = "+madondat;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.AMOUT));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSL(Payment payment){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.AMOUT, payment.getSoluong());

        long ktra = database.update(CreateDatabase.BILL_DETAIL_TABLE,contentValues, CreateDatabase.BILL_ID1+ " = "
                +payment.getMadon()+ " AND " + CreateDatabase.FOOD_ID+ " = "
                +payment.getMamon(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}
