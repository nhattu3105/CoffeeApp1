package oltest.bai12.myapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {


    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String TABLE_FOODNAME = "FOODNAME";
    public static final String TABLE_FOODCOST = "FOODCOST";
    public static final String TABLE_FOODIMAGE = "FOODIMAGE";
    public static final String TABLE_FOODID = "FOODID";

    public static final String TABLE_VIEW = "TABLE_VIEW";
    public static final String TABLE_ID = "TABLE_ID";
    public static final String TABLE_NAME = "TABLE_NAME";

    public static final String BILL_TABLE = "BILL_TABLE";
    public static final String BILL_ID = "BILL_ID";
    public static final String BILL_TABLE_ID = "BILL_TABLE_ID";
    public static final String SUM = "SUM";

    public static final String BILL_DETAIL_TABLE = "BILL_DETAIL_TABLE";
    public static final String FOOD_ID = "FOOD_ID";
    public static final String BILL_ID1 = "BILL_ID1";
    public static final String AMOUT = "AMOUT";

    public CreateDatabase(Context context) {
        super(context,"coffee", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblFOOD = " CREATE TABLE " + FOOD_TABLE + " (" + TABLE_FOODID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_FOODNAME + " TEXT, " + TABLE_FOODCOST + " TEXT, " + TABLE_FOODIMAGE + " TEXT) ";
        String tblTABLE = " CREATE TABLE " + TABLE_VIEW + " (" + TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_NAME + " TEXT)";
        String tblBILL = " CREATE TABLE " + BILL_TABLE + "( " + BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BILL_TABLE_ID + " INTEGER, " + SUM + " INTEGER)";
        String tblBILLDETAIL = "CREATE TABLE " + BILL_DETAIL_TABLE + "(" + BILL_ID1 + " INTERGER , " + FOOD_ID + " INTEGER , " + AMOUT + " INTEGER )";
        db.execSQL(tblFOOD);
        db.execSQL(tblTABLE);
        db.execSQL(tblBILLDETAIL);
        db.execSQL(tblBILL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
