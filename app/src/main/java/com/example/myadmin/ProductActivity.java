package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myadmin.adapter.InvoiceAdapter;
import com.example.myadmin.adapter.ItemClickListener;
import com.example.myadmin.adapter.SanPhamAdapter;
import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.Invoice;
import com.example.myadmin.model.Product;
import com.example.myadmin.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements ItemClickListener {

//    CardView sanpham;
    SearchView svR;
    RecyclerView sanphamRecycler;
    SanPhamAdapter sanPhamAdapter;
    List<SanPham> sanphamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

//        List<SanPham> sanphamList = new ArrayList<>();
//        sanphamList.add(new com.example.myadmin.model.SanPham(1,"red","",1.5f,"https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "","","day chuyen vang","Đoàn Ngọc Trí","15",5.6f,4,5,6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(2,"red","",1.5f,"https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "","","day chuyen vang","Bùi Ngọc Tuấn","15",5.6f,4,5,6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(3,"red","",1.5f,"https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "","","day chuyen vang","Nguyễn Đình Khiêm","15",5.6f,4,5,6));
//        sanphamList.add(new com.example.myadmin.model.SanPham(4,"red","",1.5f,"https://sencowebfiles.s3.ap-south-1.amazonaws.com/products/JKyylW1Hu9hpHN1cErmSije5cIW0PMzPOqEO4OOG.jpeg",
//                "","","day chuyen vang","Nguyễn Văn A","15",5.6f,4,5,6));
        //setSanPhamRecycler(sanphamList);
        requestSanPham();
        FloatingActionButton fab = findViewById(R.id.btnAddSanPham);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, AddProduct.class);
                startActivity(i);
            }
        });

        svR = findViewById(R.id.editText);
        svR.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamAdapter.getFilter().filter(newText);
                return false;
            }
        });
        sanphamRecycler = findViewById(R.id.sanpham_recycler);
        sanphamRecycler.setOnTouchListener(new TranslateAnimationUtil(this,fab));
    }
    private void setSanPhamRecycler(List<com.example.myadmin.model.SanPham> sanphamList) {


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        sanphamRecycler.setLayoutManager(layoutManager);
        sanPhamAdapter = new SanPhamAdapter (this, sanphamList,this);
        sanphamRecycler.setAdapter(sanPhamAdapter);
    }

    private void requestSanPham() {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getAllSanPham();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                sanphamList = response.body();
                setSanPhamRecycler(sanphamList);
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
    }
    @Override
    public void ClickItem(int idSP) {
        SanPham sp = findPro(sanphamList,idSP);
        if(sp!=null){
            sp.setDisplay(!sp.getDisplay());
            Product pro = new Product(sp);
            GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
            Call<Integer> call = service.putProduct(pro);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.d("arrmovie", response.toString());
                    //show san pham
                    int res = response.body();
                    if(res == 1){
                        Toast.makeText(ProductActivity.this,"Đã thay đổi trạng thái!!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProductActivity.this,"Không tìm thấy trạng thái!!",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d("arrmovie", t.toString());
                }

            });
        }
        setSanPhamRecycler(sanphamList);
    }

    private SanPham findPro(List<SanPham> sanphamList,int id){
        for(SanPham sp : sanphamList){
            if(sp.getId()==id)
                return sp;
        }
        return null;
    }
}