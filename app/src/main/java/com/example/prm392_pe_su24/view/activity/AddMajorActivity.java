package com.example.prm392_pe_su24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.utils.APIService;
import com.example.prm392_pe_su24.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMajorActivity extends AppCompatActivity {

    private EditText etMajorName;
    private Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_major);

        etMajorName = findViewById(R.id.etMajorName);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMajor();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMajorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addMajor() {
        String name = etMajorName.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Major name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Major major = new Major(name);

        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Major> call = apiService.addMajor(major);

        call.enqueue(new Callback<Major>() {
            @Override
            public void onResponse(Call<Major> call, Response<Major> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddMajorActivity.this, "Major added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddMajorActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddMajorActivity.this, "Failed to add major", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Major> call, Throwable t) {
                Toast.makeText(AddMajorActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
