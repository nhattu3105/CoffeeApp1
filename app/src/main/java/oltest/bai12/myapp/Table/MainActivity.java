package oltest.bai12.myapp.Table;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.AddtableLayout;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.MainMenuItem;
import oltest.bai12.myapp.Menu.MainMenu;
import oltest.bai12.myapp.Menu.MenuAdapter;
import oltest.bai12.myapp.R;


public class MainActivity extends AppCompatActivity {


    ImageButton menuButton,addTable;
    private AlertDialog dialogAddTable;

    private int tableClickedPostion = -1;
    private  int i = 10;


    private RecyclerView tablesRecyclerView;
    private List<Table> tableList;
    private TableAdapter tableAdapter;

    private TableDAO tableDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuButton = findViewById(R.id.choose);
        addTable = findViewById(R.id.imageAddTableMain);






        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
               //intent.putExtra("showlist",i);
                startActivity(intent);
            }
        });

        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddtableLayout.class);
                startActivity(intent);
            }
        });

        tablesRecyclerView = findViewById(R.id.RycecycleView);
        tablesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //showlist();
    }

    /*
    @Override
    public void onTableClicked(Table table, int position) {
        tableClickedPostion = position;
        Intent intent = new Intent(getApplicationContext(), MainTableView.class);
        intent.putExtra("isViewOrUpdate",true);
        intent.putExtra("table",table);
        startActivityForResult(intent,REQUEST_CODE_UPDATE_TABLE);
    }*/

    public void showlist()
    {

        tableList = tableDAO.getAllTable();
        tableAdapter = new TableAdapter(this,tableList);
        tablesRecyclerView.setAdapter(tableAdapter);
        tableAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        tableDAO = new TableDAO(this);
        showlist();
    }
}