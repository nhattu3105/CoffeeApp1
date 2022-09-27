package oltest.bai12.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DTO.BillDetail;
import oltest.bai12.myapp.DTO.Menu;

public class MenuitemAdapter extends RecyclerView.Adapter<MenuitemAdapter.MenuitemViewHolder> {
    private Context context;
    private List<Menu> menus1;
    private MenuItemClick menuItemClick;

    public MenuitemAdapter(Context context, List<Menu> menus1,MenuItemClick menuItemClick) {
        this.context = context;
        this.menus1 = menus1;
        this.menuItemClick=menuItemClick;
    }

    @NonNull
    @Override
    public MenuitemAdapter.MenuitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu,parent,false);
        return new MenuitemAdapter.MenuitemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuitemAdapter.MenuitemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Menu menu = menus1.get(position);

        holder.name1.setText(menus1.get(position).getFoodName());
        holder.cost1.setText(menus1.get(position).getFoodCost());
        byte[] menuimage = menu.getFoodImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);

        holder.imageView.setImageBitmap(bitmap);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemClick.onMenuItemClick(menus1.get(position),position);
            }
        });



        /*
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menus1.get(position);
                menu.setSl(1);
                menulistClick.onAddtoCart(menu);
                holder.sl.setText(String.valueOf(menu.getSl()));
                holder.add.setVisibility(View.GONE);
                holder.cong.setVisibility(View.VISIBLE);
                holder.tru.setVisibility(View.VISIBLE);
                holder.sl.setVisibility(View.VISIBLE);

            }
        });

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menus1.get(position);
                int total = menu.getSl();
                total++;
                if (total<10)
                {
                    menu.setSl(total);
                    menulistClick.onUpdatetoCart(menu);
                    holder.sl.setText(String.valueOf(menu.getSl()));
                    holder.tru.setVisibility(View.VISIBLE);
                }
                else {
                    menu.setSl(total);
                    holder.sl.setText(String.valueOf(menu.getSl()));
                    holder.add.setVisibility(View.GONE);
                    holder.cong.setVisibility(View.GONE);
                    holder.tru.setVisibility(View.VISIBLE);
                    holder.sl.setVisibility(View.VISIBLE);
                }



            }
        });

        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = menus1.get(position);
                int total = menu.getSl();
                total--;
                if (total>0)
                {
                    menu.setSl(total);
                    menulistClick.onUpdatetoCart(menu);
                    holder.sl.setText(String.valueOf(menu.getSl()));
                    holder.cong.setVisibility(View.VISIBLE);

                }else {
                    menu.setSl(total);
                    holder.sl.setText(String.valueOf(menu.getSl()));
                    holder.add.setVisibility(View.VISIBLE);
                    holder.cong.setVisibility(View.GONE);
                    holder.tru.setVisibility(View.GONE);
                    holder.sl.setVisibility(View.GONE);
                    menulistClick.onRemoveCart(menu);
                }



            }
        });

         */



    }

    @Override
    public int getItemCount() {
        return menus1.size();
    }

    static class MenuitemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name1;
        TextView cost1;
        TextView sl;
        Button add,cong,tru;
        ImageView imageView;

        ConstraintLayout constraintLayout;
        public MenuitemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagemenu);
            name1 = itemView.findViewById(R.id.name1);
            cost1 = itemView.findViewById(R.id.cost1);
            constraintLayout = itemView.findViewById(R.id.layoutitem);




        }
    }

}
