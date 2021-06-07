package com.example.myadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myadmin.InvoiceActivity;
import com.example.myadmin.R;
import com.example.myadmin.model.Orders;
import com.example.myadmin.listProductInvoice;
import com.example.myadmin.pageLogin;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    Context context;
    List<Orders> invoiceList;
    private ItemClickListener clickItem;
    private CarItemListener carItemListener;
    public InvoiceAdapter(Context context, List<Orders> invoiceList,ItemClickListener clickItem,CarItemListener carItemListener) {
        this.context = context;
        this.invoiceList = invoiceList;
        this.clickItem = clickItem;
        this.carItemListener = carItemListener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_row_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InvoiceViewHolder holder, int position) {

        holder.nameInvoice.setText("Tên khách hàng: "+invoiceList.get(position).getName());
        holder.dayBuying.setText("Ngày mua: "+invoiceList.get(position).getBuyingDay().substring(0,10));
        holder.idInvoice.setText("Mã hóa đơn: "+invoiceList.get(position).getId());
        holder.address.setText("Địa chỉ: "+invoiceList.get(position).getAddress());
        holder.address.setText("Mã khách hàng: "+invoiceList.get(position).getUserId());
        if (invoiceList.get(position).getStatusId() == 1){
            holder.statusInvoice.setText("Chuẩn Bị Hàng");
        }else if(invoiceList.get(position).getStatusId() == 2){
            holder.statusInvoice.setText("Đi Giao Hàng");
        }else if(invoiceList.get(position).getStatusId() == 3){
            holder.statusInvoice.setText("Đã Giao Hàng");
        }else if(invoiceList.get(position).getStatusId() == 4){
            holder.statusInvoice.setText("Hủy Đơn");
        }else if(invoiceList.get(position).getStatusId() == 5){
            holder.statusInvoice.setText("Đã Nhận");
        }else if(invoiceList.get(position).getStatusId() == 6){
            holder.statusInvoice.setText("Đã Hủy");
        }

        holder.itemView.findViewById(R.id.statusInvoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invoiceList.get(position).getStatusId() > 0 && invoiceList.get(position).getStatusId() < 4){
                    clickItem.ClickItem(invoiceList.get(position).getId());
                }
                else if(invoiceList.get(position).getStatusId() == 4){
                    carItemListener.ClickOnItem(invoiceList.get(position).getId());
                }
            }
        });

        holder.itemView.findViewById(R.id.moreInvoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, listProductInvoice.class);
                i.putExtra("name", invoiceList.get(position).getName());
                i.putExtra("dayBuying", invoiceList.get(position).getBuyingDay().substring(0,10));
                i.putExtra("address",invoiceList.get(position).getAddress());
                i.putExtra("id",invoiceList.get(position).getId());
                i.putExtra("UserId",invoiceList.get(position).getUserId());
                i.putExtra("Email",invoiceList.get(position).getEmail());
                i.putExtra("Status",invoiceList.get(position).getStatusId());
                i.putExtra("Phone",invoiceList.get(position).getPhone());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }


    public static final class InvoiceViewHolder extends RecyclerView.ViewHolder {
        TextView nameInvoice,dayBuying,idInvoice,address,statusInvoice,UserId;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            UserId = itemView.findViewById(R.id.idUserDeail);
            nameInvoice = itemView.findViewById(R.id.nameInvoice);
            dayBuying = itemView.findViewById(R.id.dayBuying);
            idInvoice = itemView.findViewById(R.id.idInvoice);
            statusInvoice = itemView.findViewById(R.id.statusInvoice);
        }
    }
}
