package oltest.bai12.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DAO.BillDAO;
import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DAO.MenuDAO;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.Menu.MainAddItem;
import oltest.bai12.myapp.Table.MainActivity;

public class MainMenuItem extends AppCompatActivity implements MenuItemClick{
    //private static final int REQUST_CODE_SHOW_NOTES = 3;

    private RecyclerView menuItemRecyclerView;
    private List<Menu> menuListItem;
    private List<Menu> itemincart;
    private MenuitemAdapter menuitemAdapter;
    private int totalincart = 0;
    private TextView them;
    private EditText tenban;
    private Table table;
    TableDAO tableDAO;
    MenuDAO menuDAO;
    int maban;
    BillDAO billDAO;
    BillDetailDAO billDetailDAO;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_item);
        maban = getIntent().getIntExtra("maban",0);
        tableDAO = new TableDAO(this);
        menuDAO = new MenuDAO(this);
        billDAO = new BillDAO(this);
        billDetailDAO = new BillDetailDAO(this);

        // lấy mã bill theo mã bàn
        int madon = (int)billDAO.LayMaDonTheoMaBan(maban);
        Toast.makeText(this,"madon" + madon,Toast.LENGTH_SHORT).show();

        String Name = tableDAO.GetNameById(maban);
        Toast.makeText(this,"maban" + maban,Toast.LENGTH_SHORT).show();

        menuItemRecyclerView = findViewById(R.id.menu);
        tenban = findViewById(R.id.tenban);
        menuItemRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        menuListItem = menuDAO.getAllMenu();
        menuitemAdapter = new MenuitemAdapter(this,menuListItem,this);
        menuItemRecyclerView.setAdapter(menuitemAdapter);

        tenban.setText(Name);


        /*
        them = findViewById(R.id.additemincart);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemincart != null && itemincart.size()<=0)
                {
                    Toast.makeText(MainMenuItem.this,"Please add something", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                }

            }
        });

         */







    }

    @Override
    public void onMenuItemClick(Menu menu, int position) {
        Intent intent = new Intent(getApplicationContext(), layoutadditemmenu.class);
        intent.putExtra("maban",maban);
        intent.putExtra("tenmon",menu.getFoodName());
        intent.putExtra("idmon",menu.getFoodId());
        startActivity(intent);
    }

    /*
    @Override
    public void onAddtoCart(Menu menu) {

        if(itemincart ==null)
        {
            itemincart = new ArrayList<>();
        }
        itemincart.add(menu);
        totalincart = 0;

        for (Menu m: itemincart)
        {
            totalincart = totalincart + menu.getSl();

        }
        them.setText("Checkout ("+ totalincart+") item");
    }

    @Override
    public void onUpdatetoCart(Menu menu) {
        if (itemincart.contains(menu))
        {
            int index = itemincart.indexOf(menu);
            itemincart.remove(index);
            itemincart.add(index,menu);

            totalincart = 0;
            for (Menu m: itemincart)
            {
                totalincart = totalincart + menu.getSl();
            }
            them.setText("Checkout ("+ totalincart+") item");
        }
    }

    @Override
    public void onRemoveCart(Menu menu) {
        if (itemincart.contains(menu))
        {
            itemincart.remove(menu);
            totalincart = 0;
            for (Menu m: itemincart)
            {
                totalincart = totalincart + menu.getSl();
            }
            them.setText("Checkout ("+ totalincart+") item");
        }
    }

     */


}