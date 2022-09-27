package oltest.bai12.myapp.Table;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import oltest.bai12.myapp.AddtableLayout;
import oltest.bai12.myapp.DTO.Table;
import oltest.bai12.myapp.MainMenuItem;
import oltest.bai12.myapp.MainTableView;
import oltest.bai12.myapp.R;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private Context context;
    private List<Table> tables;
    //TablesListener tablesListener;

    public TableAdapter(Context context, List<Table> tables) {
        this.context = context;
        this.tables = tables;

    }

    @NonNull
    @Override
    public TableAdapter.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tableview,parent,false);
        return new TableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Table table = tables.get(position);
        holder.numtable.setText(table.getTableName());

        int maban = tables.get(position).getTableID();

        /*holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tablesListener.onTableClicked(tables.get(position),position);
            }
        });

         */

        //holder.numtable.setText(table.getNumber());
        holder.a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainMenuItem.class);
                intent.putExtra("maban",maban);
                context.startActivity(intent);
            }
        });
        holder.a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainTableView.class);
                intent.putExtra("maban",maban);
                context.startActivity(intent);
            }
        });
        holder.a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddtableLayout.class);
                intent.putExtra("maban",maban);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    static class TableViewHolder extends RecyclerView.ViewHolder
    {
        TextView numtable;
        ImageButton a1,a2,a3;
        ConstraintLayout constraintLayout;
        public TableViewHolder(@NonNull View itemView)
        {
            super(itemView);
            numtable = itemView.findViewById(R.id.TableNum);
            constraintLayout = itemView.findViewById(R.id.layouttable);
            a1 = itemView.findViewById(R.id.listchoose1);
            a2= itemView.findViewById(R.id.listchoose2);
            a3 = itemView.findViewById(R.id.listchoose3);
        }

    }
}
