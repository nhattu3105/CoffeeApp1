package oltest.bai12.myapp.Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import oltest.bai12.myapp.DTO.Menu;
import oltest.bai12.myapp.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<Menu> menus;
    private Timer timer;
    private List<Menu> menusource;
    private MenuClick menuClick;


    public MenuAdapter(Context context,List<Menu> menus,MenuClick menuClick) {
        this.context = context;
        this.menus = menus;
        this.menusource = menus;
        this.menuClick=menuClick;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_douong,parent,false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder,@SuppressLint("RecyclerView") int position) {
        Menu menu = menus.get(position);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClick.onMenuItemClick(menus.get(position),position);
            }
        });

        byte[] menuimage = menu.getFoodImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);

        holder.imageView.setImageBitmap(bitmap);
        holder.name.setText(menu.getFoodName());
        holder.cost.setText(menu.getFoodCost());
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,cost;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.ten);
            cost = itemView.findViewById(R.id.gia);
            imageView = itemView.findViewById(R.id.hinh);
            relativeLayout = itemView.findViewById(R.id.layoutmenu);
        }
    }

    public void searchMenu(final  String searchKeyword) {


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()) {
                    menus = menusource;
                } else {
                    List<Menu> temp = new ArrayList<>();
                    for (Menu menu : menusource) {
                        if (menu.getFoodName().toLowerCase().contains(searchKeyword.toLowerCase())
                        ) {
                            temp.add(menu);
                        }
                    }
                    menus = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },500);
    }


    public void cancelTimer() {
        if (timer != null){
            timer.cancel();
        }
    }



}
