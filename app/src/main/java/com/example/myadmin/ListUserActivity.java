package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myadmin.adapter.UserAdapter;
import com.example.myadmin.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {
//    CardView user;
    RecyclerView userRecycler;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        List<User> userList = new ArrayList<>();
        // public User(int idUser, String userName, String password, String name, String email, String phone, String address, String avatar)
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://image.thanhnien.vn/1024/uploaded/toloan/2021_03_18/tien-dao-zlatan-ibrahimovic-acmilan-afp_ofcb.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://cdn.vox-cdn.com/thumbor/TfeV6ZLSmsgcua6vixQ0939BwQs=/0x0:2265x3000/1200x800/filters:focal(1171x553:1533x915)/cdn.vox-cdn.com/uploads/chorus_image/image/68998847/1305283950.0.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://blvvinhtoan.com/wp-content/uploads/2020/12/doi-hinh-ac-milan-2020-21-2.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://www.vir.com.vn/stores/news_dataimages/hung/122020/24/10/hernandez-strikes-late-to-keep-ac-milan-ahead-of-inter-in-serie-a.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://image.thanhnien.vn/1024/uploaded/toloan/2021_03_18/tien-dao-zlatan-ibrahimovic-acmilan-afp_ofcb.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://cdn.vox-cdn.com/thumbor/TfeV6ZLSmsgcua6vixQ0939BwQs=/0x0:2265x3000/1200x800/filters:focal(1171x553:1533x915)/cdn.vox-cdn.com/uploads/chorus_image/image/68998847/1305283950.0.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://blvvinhtoan.com/wp-content/uploads/2020/12/doi-hinh-ac-milan-2020-21-2.jpg"));
        userList.add(new com.example.myadmin.model.User(1,"tuanbui","123","Bùi Ngọc Tuấn","tuanbui0509@gmail.com",
                "0909000","97 Man Thiện","https://www.vir.com.vn/stores/news_dataimages/hung/122020/24/10/hernandez-strikes-late-to-keep-ac-milan-ahead-of-inter-in-serie-a.jpg"));
        setUserRecycler(userList);
    }
    private void setUserRecycler(List<User> userList) {

        userRecycler = findViewById(R.id.user_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        userRecycler.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter (this, userList);
        userRecycler.setAdapter(userAdapter);

    }
}