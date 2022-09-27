package oltest.bai12.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.DTO.Menu;

public class MenuTable2Adapter extends RecyclerView.Adapter<MenuTable2Adapter.MenuViewHolder> {

    private Context context;
    private List<Menu> menus;

    public MenuTable2Adapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;

    }

    @NonNull
    @Override
    public MenuTable2Adapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_douong,parent,false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder,@SuppressLint("RecyclerView") int position) {
        final Menu menu = menus.get(position);

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
        RelativeLayout relativeLayout;

        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.ten);
            cost = itemView.findViewById(R.id.gia);
            relativeLayout = itemView.findViewById(R.id.layoutmenu);
        }


    }


}
