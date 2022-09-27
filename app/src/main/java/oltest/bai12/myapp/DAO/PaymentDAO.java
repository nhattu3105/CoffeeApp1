package oltest.bai12.myapp.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DTO.Payment;
import oltest.bai12.myapp.Database.CreateDatabase;

public class PaymentDAO {
    SQLiteDatabase database;
    public  PaymentDAO(Context context)
    {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    @SuppressLint("Range")
    public List<Payment> laydsmontheomadon(int madon)
    {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM "+CreateDatabase.BILL_DETAIL_TABLE+" ctdd,"+CreateDatabase.FOOD_TABLE+" mon WHERE "
                +"ctdd."+CreateDatabase.FOOD_ID+" = mon."+CreateDatabase.TABLE_FOODID+" AND "
                +CreateDatabase.BILL_ID1+" = '"+madon+"'";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Payment payment = new Payment();
            payment.setMadon(cursor.getString(cursor.getColumnIndex(CreateDatabase.BILL_ID1)));
            payment.setMamon(cursor.getString(cursor.getColumnIndex(CreateDatabase.FOOD_ID)));
            payment.setSoluong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.AMOUT)));
            payment.setGiatien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_FOODCOST)));
            payment.setTenmon(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_FOODNAME)));
            payment.setHinhanh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TABLE_FOODIMAGE)));
            payments.add(payment);

            cursor.moveToNext();
        }
        return payments;
    }
}
