package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myadmin.adapter.listProductInvoiceAdapter;
import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listProductInvoice extends AppCompatActivity {
    ImageView img, back;
    TextView dayBuying, address, id, UserId, Email, Phone, Status, name;
    String dayBuyingStr, addressStr, UserIdStr, EmailStr, PhoneStr, image, nameStr;
    int idO;
    RecyclerView listProductInvoiceRecycler;
    listProductInvoiceAdapter productInvoiceAdapter;
    List<SanPham> sanphamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product_invoice);
        Intent i = getIntent();

        dayBuyingStr = i.getStringExtra("dayBuying");
        addressStr = i.getStringExtra("address");
        UserIdStr = i.getStringExtra("UserId");
        EmailStr = i.getStringExtra("Email");
        PhoneStr = i.getStringExtra("Phone");
        nameStr = i.getStringExtra("name");
        idO = i.getIntExtra("id",0);
        address = findViewById(R.id.addressDeail);
        UserId = findViewById(R.id.idUserDeail);
        Phone = findViewById(R.id.phoneDeail);
        Email = findViewById(R.id.emailDeail);
        dayBuying = findViewById(R.id.dayBuyingDeail);
        name = findViewById(R.id.textView);

        name.setText("Chi tiết hóa đơn " + nameStr);
        address.setText("Địa chỉ: " + addressStr);
        UserId.setText("Tên khách hàng: " + nameStr);
        Phone.setText("Số điện thoại: " + PhoneStr);
        Email.setText("Email: " + EmailStr);
        dayBuying.setText("Ngày mua: " + dayBuyingStr);

//        sanphamList = new ArrayList<>();
//        sanphamList.add(new com.example.myadmin.model.SanPham(1, "red", "", 1.5f, "https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "", "", "day chuyen vang", "Đoàn Ngọc Trí", "15", 5.6f, 4, 5, 6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(2, "red", "", 1.5f, "https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "", "", "day chuyen vang", "Bùi Ngọc Tuấn", "15", 5.6f, 4, 5, 6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(3, "red", "", 1.5f, "https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "", "", "day chuyen vang", "Nguyễn Đình Khiêm", "15", 5.6f, 4, 5, 6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(4, "red", "", 1.5f, "https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "", "", "day chuyen vang", "Nguyễn Văn A", "15", 5.6f, 4, 5, 6));
        //setlistProductInvoiceRecycler(sanphamList);

        requestSanPham(idO);

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(ProductDetails.this, MainActivity.class);
//                startActivity(i);
//                finish();
//
//            }
//        });
    }

    private void setlistProductInvoiceRecycler(List<SanPham> sanphamList) {

        listProductInvoiceRecycler = findViewById(R.id.listProductInvoice_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listProductInvoiceRecycler.setLayoutManager(layoutManager);
        productInvoiceAdapter = new listProductInvoiceAdapter(this, sanphamList);
        listProductInvoiceRecycler.setAdapter(productInvoiceAdapter);

    }

    private void requestSanPham(int order) {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getDetailOrder(order);
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                sanphamList = response.body();
                setlistProductInvoiceRecycler(sanphamList);
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }
        });
    }
}