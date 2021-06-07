package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.Product;
import com.example.myadmin.model.SanPham;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProduct extends AppCompatActivity {

    EditText name,quality,price,info,size;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent i = getIntent();
        SanPham sp = (SanPham) i.getSerializableExtra("product");

        name = findViewById(R.id.editTextName);
        quality = findViewById(R.id.editTextQuality);
        price = findViewById(R.id.editTextPrice);
        info = findViewById(R.id.editTextInfo);
        size = findViewById(R.id.editTextSize);
        btnUpdate = findViewById(R.id.btnUpdateProduct);

        name.setText(sp.getName());
        quality.setText(Integer.toString(sp.getStock()));
        price.setText(Integer.toString((int) sp.getPrice()) );
        info.setText(sp.getInformation());
        size.setText(sp.getSize());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update product
                sp.setName(name.getText().toString());
                sp.setStock(Integer.parseInt(quality.getText().toString()));
                sp.setPrice(Float.parseFloat(price.getText().toString()));
                sp.setInformation(info.getText().toString());
                sp.setSize(size.getText().toString());
                Product product = new Product(sp);
                GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
                Call<Integer> call = service.putProduct(product);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("arrmovie", response.toString());
                        //show san pham
                        int res = response.body();
                        if(res == 1){
                            Toast.makeText(EditProduct.this,"Cập nhật thành công !!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(EditProduct.this,"Cập nhật thất bại !!",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("arrmovie", t.toString());
                    }
                });
            }
        });
    }
}