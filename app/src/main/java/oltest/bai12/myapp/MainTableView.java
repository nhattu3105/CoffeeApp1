package oltest.bai12.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DAO.BillDAO;
import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DAO.MenuDAO;
import oltest.bai12.myapp.DAO.PaymentDAO;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.DTO.Payment;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.Menu.MainAddItem;

public class MainTableView extends AppCompatActivity implements PaymentAdapter.Delclick {

    private EditText numtable;
    private TextView sum;
    private ImageView save,back;
    private Button deteleTable,checkout;

    private AlertDialog dialogDeleteItem;
    private Table table;
    private List<Menu> item;
    private List<Payment> paymentList;
    private String a;
    TableDAO tableDAO;
    MenuDAO menuDAO;
    PaymentDAO paymentDAO;
    BillDAO billDAO;
    BillDetailDAO billDetailDAO;
    int maban;
    int tongtien;

    private RecyclerView recyclerView;
    private  PaymentAdapter paymentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table_view);
        maban = getIntent().getIntExtra("maban",0);
        tableDAO = new TableDAO(this);
        menuDAO = new MenuDAO(this);
        billDAO = new BillDAO(this);
        billDetailDAO = new BillDetailDAO(this);
        paymentDAO = new PaymentDAO(this);




        numtable = findViewById(R.id.TableNum);
        save = findViewById(R.id.imageSave);
        back = findViewById(R.id.imageBack);
        deteleTable = findViewById(R.id.deleteALL1);
        recyclerView = findViewById(R.id.list1);
        sum = findViewById(R.id.TongTien);
        checkout = findViewById(R.id.CheckOut);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        String Name = tableDAO.GetNameById(maban);
        Toast.makeText(this,"maban" + maban,Toast.LENGTH_SHORT).show();

        numtable.setText(Name);

        if (maban!=0)
        {
            Showlist();

        }

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTongTien();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void Showlist() {


        paymentList = paymentDAO.laydsmontheomadon(maban);
        if (paymentList.size()!=0)
        {
            paymentAdapter = new PaymentAdapter(this,maban,this);
            paymentAdapter.setData(paymentList);
            recyclerView.setAdapter(paymentAdapter);
        }
        else Toast.makeText(this,"ko hien thi dc",Toast.LENGTH_SHORT).show();



    }
    private void setTongTien() {
        tongtien = 0;
        for (int i=0;i<paymentList.size();i++){
            int soluong = paymentList.get(i).getSoluong();
            int giatien = paymentList.get(i).getGiatien();

            tongtien += (soluong * giatien);
        }
        sum.setText(String.valueOf(tongtien) +" VNĐ");
    }

    @Override
    public void delitem(Payment payment) {
        if (dialogDeleteItem==null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainTableView.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layoutdeleteitem,(ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteItem = builder.create();
            if (dialogDeleteItem.getWindow()!=null)
            {
                dialogDeleteItem.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    billDetailDAO.deleteMonAn(payment.getMadon(),payment.getMamon());
                    paymentList.remove(payment);
                    paymentAdapter.notifyDataSetChanged();
                    dialogDeleteItem.dismiss();
                    Toast.makeText(MainTableView.this,"Xóa thành công",Toast.LENGTH_SHORT).show();

                }
            });
            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDeleteItem.dismiss();
                }
            });
        }
        dialogDeleteItem.show();
    }
}