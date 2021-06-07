package com.example.myadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myadmin.adapter.CarItemListener;
import com.example.myadmin.adapter.InvoiceAdapter;
import com.example.myadmin.adapter.ItemClickListener;
import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetDataOrders;
import com.example.myadmin.model.Orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceActivity extends AppCompatActivity implements ItemClickListener, CarItemListener {
    CardView invoice;
    TextView detailInvoice;
    RecyclerView invoiceRecycler;
    InvoiceAdapter invoiceAdapter;
    boolean ret = false;
    List<com.example.myadmin.model.Orders> invoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
//        List<com.example.myadmin.model.Orders> invoiceList = new ArrayList<>();
//        invoiceList.add(new com.example.myadmin.model.Orders(1, 1, "1/1/2021", "Invoice 1", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 1));
//        invoiceList.add(new com.example.myadmin.model.Orders(2, 2, "1/2/2021", "Invoice 2", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 1));
//        invoiceList.add(new com.example.myadmin.model.Orders(3, 3, "1/3/2021", "Invoice 3", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 0));
//        invoiceList.add(new com.example.myadmin.model.Orders(4, 4, "1/4/2021", "Invoice 4", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 0));
//        invoiceList.add(new com.example.myadmin.model.Orders(5, 5, "1/4/2021", "Invoice 5", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 1));
//        invoiceList.add(new com.example.myadmin.model.Orders(6, 6, "1/4/2021", "Invoice 6", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 1));
//        invoiceList.add(new com.example.myadmin.model.Orders(7, 7, "1/4/2021", "Invoice 7", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 0));
//        invoiceList.add(new com.example.myadmin.model.Orders(8, 8, "1/4/2021", "Invoice 8", "0987654321", "tuanbui0509@gmail.com", "97 Man Thiện", "No way", 1));
//        setInvoiceRecycler(invoiceList);
        reqestDonHang();
    }

    private void setInvoiceRecycler(List<com.example.myadmin.model.Orders> invoiceList) {


        invoiceRecycler = findViewById(R.id.invoice_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        invoiceRecycler.setLayoutManager(layoutManager);
        invoiceAdapter = new InvoiceAdapter(this, invoiceList,this,this);
        invoiceRecycler.setAdapter(invoiceAdapter);

    }

    private void reqestDonHang() {
        GetDataOrders service = CallAPI.getRetrofitInstance().create(GetDataOrders.class);
        Call<ArrayList<Orders>> call = service.getallOrders();
        call.enqueue(new Callback<ArrayList<Orders>>() {
            @Override
            public void onResponse(Call<ArrayList<Orders>> call, Response<ArrayList<Orders>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                //showSanPham(response.body());
                invoiceList = response.body();
                setInvoiceRecycler(invoiceList);
            }

            @Override
            public void onFailure(Call<ArrayList<Orders>> call, Throwable t) {
                Log.d("Thất bại", t.toString());
            }

        });
    }

    @Override
    public void ClickItem(int idSP) {
        Orders oders = findOrder(invoiceList,idSP);
        if(oders!=null){
            //oders.setStatusId(1);
            cfDonHang(oders.getId());
            oders.setStatusId(oders.getStatusId()+1);
            invoiceAdapter.notifyDataSetChanged();
        }
    }
    private Orders findOrder(List<com.example.myadmin.model.Orders> invoiceList,int id){
        for (Orders orders : invoiceList){
            if(orders.getId()==id){
                return orders;
            }
        }
        return null;
    }

    private boolean cfDonHang(int id) {
        GetDataOrders service = CallAPI.getRetrofitInstance().create(GetDataOrders.class);
        Call<Integer> call = service.updateOrder(id);
        ret = false;
        call.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("arrmovie", response.toString());
                int res = response.body();
                if(res==0){
                    ret = true;
                }else{

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("Thất bại", t.toString());
                Toast.makeText(InvoiceActivity.this,"Có lỗi trong quá trình tải mạng!!",Toast.LENGTH_LONG).show();
            }
        });
        return ret;
    }

    @Override
    public void onDeleteCartItem(int idKH, int position) {

    }

    @Override
    public void ClickOnItem(int idSP) {
        Orders o = findOrder(invoiceList,idSP);
        GetDataOrders service = CallAPI.getRetrofitInstance().create(GetDataOrders.class);
        Call<Integer> call=service.CancelAnOrder(o.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                assert response.body() != null;
                Log.d("suss",response.body().toString());
                int number = Integer.parseInt(response.body().toString());
                if(number==0){
                    o.setStatusId(6);
                    invoiceAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("abc",t.toString());
            }
        });
    }
}