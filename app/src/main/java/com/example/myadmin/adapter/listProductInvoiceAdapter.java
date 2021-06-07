package com.example.myadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myadmin.R;
import com.example.myadmin.model.SanPham;

import java.util.List;

public class listProductInvoiceAdapter extends RecyclerView.Adapter<listProductInvoiceAdapter.listProductInvoiceHolder>{
    Context context;
    List<SanPham> listProductInvoice;

    public listProductInvoiceAdapter(Context context, List<SanPham> listProductInvoice) {
        this.context = context;
        this.listProductInvoice = listProductInvoice;
    }

    @NonNull
    @Override
    public listProductInvoiceAdapter.listProductInvoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_product_invoice_detail, parent, false);
        return new listProductInvoiceAdapter.listProductInvoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(listProductInvoiceAdapter.listProductInvoiceHolder holder, int position) {
        Glide.with(context).load(listProductInvoice.get(position).getImage1()).into(holder.sanpham_image);
        holder.name.setText(listProductInvoice.get(position).getName());
        holder.quality.setText(String.valueOf("SL: "+listProductInvoice.get(position).getQuantity()));
        holder.price.setText(String.valueOf("Giá: "+listProductInvoice.get(position).getPrice())+"$");
    }

    @Override
    public int getItemCount() {
        return listProductInvoice.size();
    }


    public static final class listProductInvoiceHolder extends RecyclerView.ViewHolder {
        ImageView sanpham_image;
        TextView price, name,quality;
        public listProductInvoiceHolder(@NonNull View itemView) {
            super(itemView);
            sanpham_image = itemView.findViewById(R.id.sanpham_image);
            quality = itemView.findViewById(R.id.quality);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.sanpham_name);
        }
    }
}
