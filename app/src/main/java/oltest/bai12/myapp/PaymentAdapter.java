package oltest.bai12.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import oltest.bai12.myapp.DAO.BillDetailDAO;
import oltest.bai12.myapp.DTO.Payment;
import oltest.bai12.myapp.Menu.MainAddItem;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MenuitemViewHolder> {
    private Context context;
    private List<Payment> paymentList;
    private int maban;
    private Delclick delclick;
    AlertDialog alertDialog;
    BillDetailDAO billDetailDAO;

    public PaymentAdapter(Context context,int maban, Delclick delclick) {
        this.context = context;
        this.maban = maban;
        this.delclick =delclick;
    }
    public void setData(List<Payment> paymentList)
    {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentAdapter.MenuitemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemmenu,parent,false);
        return new PaymentAdapter.MenuitemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.MenuitemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Payment payment = paymentList.get(position);
        billDetailDAO = new BillDetailDAO(context);
        holder.name1.setText(payment.getTenmon());

        holder.sl.setText(String.valueOf(payment.getSoluong()));
        holder.cost1.setText(payment.getGiatien() +" đ");
        byte[] menuimage = payment.getHinhanh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
        holder.imageView.setImageBitmap(bitmap);

        holder.delitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //billDetailDAO.deleteMonAn(payment.getMadon(),payment.getMamon());
                //paymentList.remove(payment);

                delclick.delitem(payment);
                //notifyDataSetChanged();


            }
        });


        if (payment.getSoluong()==1)
        {   holder.cong.setVisibility(View.VISIBLE);
            holder.tru.setVisibility(View.GONE);}

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int total = payment.getSoluong();
                total++;
                if (total<10)
                {
                    payment.setSoluong(total);
                    holder.sl.setText(String.valueOf(payment.getSoluong()));
                    holder.tru.setVisibility(View.VISIBLE);
                }
                else {
                    payment.setSoluong(total);
                    holder.sl.setText(String.valueOf(payment.getSoluong()));
                    holder.cong.setVisibility(View.GONE);
                    holder.tru.setVisibility(View.VISIBLE);

                }
                billDetailDAO.CapNhatSL(payment);



            }
        });

        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total = payment.getSoluong();
                total--;
                if (total>1)
                {
                    payment.setSoluong(total);
                    holder.sl.setText(String.valueOf(payment.getSoluong()));
                    holder.cong.setVisibility(View.VISIBLE);
                    holder.tru.setVisibility(View.VISIBLE);

                }else {
                    payment.setSoluong(total);
                    holder.sl.setText(String.valueOf(payment.getSoluong()));
                    holder.cong.setVisibility(View.VISIBLE);
                    holder.tru.setVisibility(View.GONE);
                }
                billDetailDAO.CapNhatSL(payment);



            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    static class MenuitemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name1;
        TextView cost1;
        TextView sl;
        ImageView imageView;
        ImageButton cong , tru,delitem;


        //RelativeLayout relativeLayout;
        public MenuitemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name1 = itemView.findViewById(R.id.namemenu);
            cost1 = itemView.findViewById(R.id.costmenu);
            imageView = itemView.findViewById(R.id.imagemenu);
            //add = itemView.findViewById(R.id.them);
            cong = itemView.findViewById(R.id.plus);
            tru = itemView.findViewById(R.id.minus);
            sl = itemView.findViewById(R.id.soluongitem);
            delitem =itemView.findViewById(R.id.deleteitempayment);
            //relativeLayout = itemView.findViewById(R.id.layoutmenu);

        }
    }
    public interface Delclick
    {
        public void delitem(Payment payment);
    }


}
