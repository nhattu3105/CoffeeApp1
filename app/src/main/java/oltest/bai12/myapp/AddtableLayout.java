package oltest.bai12.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import oltest.bai12.myapp.DAO.BillDAO;
import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Bill;
import oltest.bai12.myapp.DTO.BillDetail;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.Menu.MainAddItem;
import oltest.bai12.myapp.Table.MainActivity;

public class AddtableLayout extends AppCompatActivity {

    EditText name;
    Button ok,edit,delete;
    TableDAO tableDAO;
    BillDAO billDAO;
    AlertDialog dialogDeleteMenu;
    int maban = 0;
    String tenban = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtablelayout);
        maban = getIntent().getIntExtra("maban",0);
        tableDAO = new TableDAO(this);
        billDAO = new BillDAO(this);

        name=findViewById(R.id.txtl_addtable_tenban);
        ok=findViewById(R.id.btn_addtable_TaoBan);
        edit=findViewById(R.id.btn_edittable_TaoBan);
        delete=findViewById(R.id.btn_deletetable_TaoBan);

        if (maban == 0)
        {
            ok.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sTenBanAn =name.getText().toString();
                    if(sTenBanAn != null || sTenBanAn.equals("")){
                        Table table = new Table();
                        Bill bill = new Bill();
                        bill.setBillID(-1);
                        table.setTableID(-1);
                        table.setTableName(sTenBanAn);
                        bill.setTableID(table.getTableID());
                        bill.setSum(0);
                        Boolean check = tableDAO.AddTable(table);
                        long a = billDAO.ThemDonDat(bill);
                        Toast.makeText(AddtableLayout.this,"ma bill = " + a,Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else
        {
            ok.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

            tenban = tableDAO.GetNameById(maban);
            name.setText(tenban);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkedit();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeletMenuDialog();

                }
            });


        }

    }

    private void checkedit()
    {
        Table newtable = new Table();
        newtable.setTableID(maban);
        newtable.setTableName(name.getText().toString());
        boolean check = tableDAO.UpdateTable(newtable,maban);
        if (check)
        {
            Toast.makeText(AddtableLayout.this,"edit thanh cong",Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    private void showDeletMenuDialog()
    {
        if (dialogDeleteMenu==null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddtableLayout.this);
            View view1 = LayoutInflater.from(this).inflate(R.layout.layoutdeleteitem,(ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view1);
            dialogDeleteMenu = builder.create();
            if (dialogDeleteMenu.getWindow()!=null)
            {
                dialogDeleteMenu.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view1.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean check = tableDAO.DeleteTable(maban);
                    dialogDeleteMenu.dismiss();
                    if (check)
                    {
                        Toast.makeText(AddtableLayout.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();

                }
            });
            view1.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDeleteMenu.dismiss();
                }
            });
        }
        dialogDeleteMenu.show();
    }
}