package com.example.myadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {
    EditText name,quality,price,info,size;
    ImageView imgView;
    Button buttonSubmit,btnChoseImage;
    Uri imageData;
    String imageString;
    void setControl(){
        name = findViewById(R.id.editTextName);
        quality = findViewById(R.id.editTextQuality);
        price = findViewById(R.id.editTextPrice);
        info = findViewById(R.id.editTextInfo);
        size = findViewById(R.id.editTextSize);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        btnChoseImage = (Button) findViewById(R.id.buttonAnh);
        imgView = findViewById(R.id.imageShow);
    }
    void setEvent(){
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String qualityStr = quality.getText().toString();
                String priceStr = price.getText().toString();
                String sizeStr = size.getText().toString();
                String infoStr = info.getText().toString();
                if (nameStr.length() == 0) {
                    name.setError("Chưa nhập Họ và tên");
                    name.requestFocus();
                    return;
                }
                if (qualityStr.length() == 0) {
                    quality.setError("Chưa nhập số lượng");
                    quality.requestFocus();
                    return;
                }
                if (priceStr.length() == 0) {
                    price.setError("Chưa nhập Giá");
                    price.requestFocus();
                    return;
                }
                if (sizeStr.length() == 0) {
                    size.setError("Chưa nhập kích thước");
                    size.requestFocus();
                    return;
                }
                if (infoStr.length() == 0) {
                    info.setError("Chưa nhập thông tin");
                    info.requestFocus();
                    return;
                }
                Product product = new Product();
                product.setProGroupId(1);
                product.setColor("null");
                product.setName(name.getText().toString());
                product.setStock(Integer.parseInt(quality.getText().toString()));
                product.setPrice(Float.parseFloat(price.getText().toString()));
                product.setInformation(info.getText().toString());
                product.setSize(size.getText().toString());
                product.setImage1(imageString);
                GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
                Call<Integer> call = service.addProduct(product);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("arrmovie", response.toString());
                        //show san pham
                        int res = response.body();
                        if(res == 1){
                            Toast.makeText(AddProduct.this,"Thêm sản phẩm thành công !!",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("arrmovie", t.toString());
                    }
                });
            }
        });
        btnChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //initializing view objects
        setControl();
        setEvent();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode ==  RESULT_OK  && data !=null){
            imageData =  data.getData();
            imgView.setImageURI(imageData);
            InputStream iStream = null;
            try {
                iStream = getContentResolver().openInputStream(imageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                byte[] imageBytes = getBytes(iStream);
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}