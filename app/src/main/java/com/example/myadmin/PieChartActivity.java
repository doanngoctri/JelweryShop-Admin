package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.SanPham;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart mChart;
    List<SanPham> sanphamList;
    final Calendar myCalendar = Calendar.getInstance();
    EditText editTextDateFrom;
    EditText editTextDateTo;
    String from = "";
    String to = "";
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        mChart = (com.github.mikephil.charting.charts.PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("PieChart");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        editTextDateFrom = (EditText) findViewById(R.id.editTextDateFrom);
        editTextDateTo = (EditText) findViewById(R.id.editTextDateTo);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        requestSanPham();
        setEvent();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Số lượng: "
                + e.getY()
               , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 25, 40, 70 };
        String[] xData = { "January", "February", "January" };

        for (int i = 0; i < sanphamList.size();i++){
            yEntrys.add(new PieEntry(sanphamList.get(i).getQuantity(),sanphamList.get(i).getName()));
        }
        for (int i = 0; i < sanphamList.size();i++){
            xEntrys.add(sanphamList.get(i).getName());
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        String[] colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
        ArrayList<Integer> colors= new ArrayList<Integer>();
        for (int i = 0; i < sanphamList.size(); i++) {
            int newColor = Color.parseColor(colorsTxt[i]);
            colors.add(newColor);
        }

//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.MAGENTA);
//        colors.add(Color.)
//        colors.add(Color.GREEN);
//        colors.add(Color.YELLOW);

        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(2f);
        pieChart.setDrawSliceText(false);
        pieDataSet.setValueTextColor(Color.WHITE);
        //pieDataSet.setValueTextSize(10f);
        //pieDataSet.setSliceSpace(5f);
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setYEntrySpace(20f);
        //legend.setYOffset(1);
        legend.setWordWrapEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private void requestSanPham() {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getStatic();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                sanphamList = response.body();
                addDataSet(mChart);
                mChart.setOnChartValueSelectedListener(PieChartActivity.this);
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
    }
    private void setEvent() {
        DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFrom();
            }

        };
        DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelTo();
            }

        };
        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PieChartActivity.this, dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PieChartActivity.this, dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDateFrom.getText().toString().equals("") || editTextDateTo.getText().toString().equals("")) {
                    Toast.makeText(PieChartActivity.this, "Không được để trống", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        Date now = new Date();
                        Date From = new SimpleDateFormat("yyyy/MM/dd").parse(editTextDateFrom.getText().toString());
                        Date To = new SimpleDateFormat("yyyy/MM/dd").parse(editTextDateTo.getText().toString());
                        if (From.after(now)) {
                            Toast.makeText(PieChartActivity.this, " Lỗi : Ngày bắt đầu phải trước ngày hiện tại ", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (To.after(now)) {
                            Toast.makeText(PieChartActivity.this, " Lỗi : Ngày kết thúc phải trước ngày hiện tại ", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (From.after(To)) {
                            Toast.makeText(PieChartActivity.this, " Lỗi : Ngày bắt đầu phải trước ngày kết thúc ", Toast.LENGTH_LONG).show();
                            return;
                        }
                        requestSanPham(from,to);
                    } catch (Exception e) {

                    }

                }
            }
        });
    }
    private void updateLabelFrom() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        from = sdf.format(myCalendar.getTime());
        editTextDateFrom.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabelTo() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        to = sdf.format(myCalendar.getTime());
        editTextDateTo.setText(sdf.format(myCalendar.getTime()));
    }
    private void requestSanPham(String from,String to) {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getStaticByTime(from,to);
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                sanphamList = response.body();
                addDataSet(mChart);
                mChart.setOnChartValueSelectedListener(PieChartActivity.this);
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }
        });
    }
}