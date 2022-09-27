package oltest.bai12.myapp.Menu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import oltest.bai12.myapp.DAO.MenuDAO;
import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.R;

public class MainAddItem extends AppCompatActivity {

    EditText name,cost;
    ImageView addimage;
    Button add,delete,edit;
    MenuDAO menuDAO;

    AlertDialog dialogDeleteMenu;
    Bitmap bitmapold;

    int mamon = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            addimage.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_item);

        mamon = getIntent().getIntExtra("id",0);
        menuDAO = new MenuDAO(this);

        name = findViewById(R.id.inputAddNameItem);
        cost = findViewById(R.id.inputAddCostItem);
        add = findViewById(R.id.AddItem);
        delete = findViewById(R.id.DeleteItem);
        addimage = findViewById(R.id.AddImgameItem);
        addimage.setTag(R.drawable.drinkfood);
        edit = findViewById(R.id.UpdateItem);






        if (mamon!=0)
        {
            Menu menu1 = menuDAO.getFoodbyID(mamon);
            Toast.makeText(this,"id = "+ mamon,Toast.LENGTH_SHORT).show();
            byte[] menuimage = menu1.getFoodImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);

            name.setText(menu1.getFoodName());
            cost.setText(menu1.getFoodCost());
            addimage.setImageBitmap(bitmap);

            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.GONE);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeletMenuDialog();

                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatefood();
                }
            });

        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
                recreate();
                name.setText("");
                cost.setText("");


            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
            }
        });


    }

    private void updatefood() {
        Menu menuedit = new Menu();
        menuedit.setFoodId(mamon);
        menuedit.setFoodName(name.getText().toString());
        menuedit.setFoodCost(cost.getText().toString());
        menuedit.setFoodImage(imageViewtoByte(addimage));

            Boolean ktra1 = menuDAO.UpdateFood(menuedit,mamon);
            Toast.makeText(MainAddItem.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
            onBackPressed();
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)addimage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


    private void saveItem()
    {

        Menu menusave = new Menu();
        menusave.setFoodId(-1);
        menusave.setFoodName(name.getText().toString());
        menusave.setFoodCost(cost.getText().toString());
        menusave.setFoodImage(imageViewtoByte(addimage));

        Boolean ktra = menuDAO.AddFood(menusave);
        Toast.makeText(MainAddItem.this,"Thêm thành công",Toast.LENGTH_SHORT).show();


    }

    private void showDeletMenuDialog()
    {
        if (dialogDeleteMenu==null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainAddItem.this);
            View view = LayoutInflater.from(this).inflate(R.layout.layoutdeleteitem,(ViewGroup) findViewById(R.id.layoutDeleteNoteContainer));
            builder.setView(view);
            dialogDeleteMenu = builder.create();
            if (dialogDeleteMenu.getWindow()!=null)
            {
                dialogDeleteMenu.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean check = menuDAO.DeleteFood(mamon);

                    dialogDeleteMenu.dismiss();
                    Toast.makeText(MainAddItem.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                    onBackPressed();


                }
            });
            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDeleteMenu.dismiss();
                }
            });
        }
            dialogDeleteMenu.show();
    }



}