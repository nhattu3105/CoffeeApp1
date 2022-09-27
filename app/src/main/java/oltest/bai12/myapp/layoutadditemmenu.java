package oltest.bai12.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import oltest.bai12.myapp.DAO.BillDAO;
import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Bill;
import oltest.bai12.myapp.DTO.BillDetail;
import oltest.bai12.myapp.DTO.Payment;

public class layoutadditemmenu extends AppCompatActivity {
    private TextView foodname;
    private EditText sl;
    private Button them;
    //TableDAO tableDAO;
    BillDetailDAO billDetailDAO;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutadditemmenu);
        int maban = getIntent().getIntExtra("maban",0);
        String tenmon = getIntent().getStringExtra("tenmon");
        int idmon = getIntent().getIntExtra("idmon",0);
        billDetailDAO = new BillDetailDAO(this);
        BillDAO billDAO = new BillDAO(this);

        foodname = findViewById(R.id.name1);
        sl=findViewById(R.id.num);
        them=findViewById(R.id.additem);

        foodname.setText(tenmon);


        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(sl.getText().toString());
                //String Maban = String.valueOf(maban);
                // kiem tra mon da ton tai trong billdetail hay chua

                boolean check = billDetailDAO.KiemTraMonTonTai(maban,idmon);
                if (check)
                {
                    int slitem = billDetailDAO.LaySLMonTheoMaDon(maban,idmon);
                    Payment payment = new Payment();
                    payment.setMadon(String.valueOf(maban));
                    payment.setMamon(String.valueOf(idmon));
                    payment.setSoluong(amount+slitem);
                    billDetailDAO.CapNhatSL(payment);
                    Toast.makeText(layoutadditemmenu.this,"mon da ton tai",Toast.LENGTH_SHORT).show();
                }
                else {
                    BillDetail billDetail = new BillDetail();
                    billDetail.setBillID(maban);
                    billDetail.setFoodID(idmon);
                    billDetail.setSl(amount);
                    boolean a = billDetailDAO.ThemChiTietDonDat(billDetail);
                    if (a)
                    {
                        Toast.makeText(layoutadditemmenu.this,"them thanh cong",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}