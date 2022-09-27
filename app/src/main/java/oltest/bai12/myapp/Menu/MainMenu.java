package oltest.bai12.myapp.Menu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.AddtableLayout;
import oltest.bai12.myapp.DAO.MenuDAO;
import oltest.bai12.myapp.DAO.TableDAO;
import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.R;
import oltest.bai12.myapp.Table.MainActivity;


public class MainMenu extends AppCompatActivity implements MenuClick{

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    private static final int REQUST_CODE_SHOW_NOTES = 3;

    private int menuClickedPostion = -1;
    MenuDAO menuDAO;
    //TableDAO tableDAO;


    private RecyclerView menusRecyclerView;
    private List<Menu> menuList;
    private MenuAdapter menuAdapter;

    private int i = 0 ;

    ImageView addItemView;
    ImageView fix;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //menuDAO = new MenuDAO(this);
        //tableDAO = new TableDAO(this);
        init();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        menusRecyclerView.setLayoutManager(linearLayoutManager);

        addItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this,MainAddItem.class);
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                menuAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (menuList.size() != 0){
                    menuAdapter.searchMenu(editable.toString());
                }
            }
        });


    }

    public void init ()
    {
        addItemView = findViewById(R.id.addItemMenu);
        menusRecyclerView = findViewById(R.id.list);
        search = findViewById(R.id.InputSearch);
    }



    @Override
    public void onMenuItemClick(Menu menu, int position) {
        int id = menu.getFoodId();
        Intent intent = new Intent(getApplicationContext(),MainAddItem.class);
        intent.putExtra("id",id);
        startActivity(intent);
        //resultLauncherMenu.launch(intent);
    }
    public void showlist()
    {


        menuList = menuDAO.getAllMenu();
        menuAdapter = new MenuAdapter(this,menuList,this);
        menusRecyclerView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();



    }

    @Override
    protected void onResume() {
        super.onResume();
        menuDAO = new MenuDAO(this);
        showlist();
    }

}
