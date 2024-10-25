package com.example.prm392_pe_su24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.utils.APIService;
import com.example.prm392_pe_su24.utils.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMajorActivity extends AppCompatActivity {
    private EditText etMajorName;
    private Button btnUpdate, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_major);

        etMajorName = findViewById(R.id.etMajorName);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        String majorId = intent.getStringExtra("majorId");
        etMajorName.setText(intent.getStringExtra("majorName"));

        btnUpdate.setOnClickListener(v -> updateMajor(majorId));
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(EditMajorActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void updateMajor(String majorId) {
        String name = etMajorName.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Major name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Major major = new Major(majorId, name);

        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Major> call = apiService.updateMajor(majorId, major);

        call.enqueue(new Callback<Major>() {
            @Override
            public void onResponse(Call<Major> call, Response<Major> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditMajorActivity.this, "Major updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditMajorActivity.this, "Failed to update major", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Major> call, Throwable t) {
                Toast.makeText(EditMajorActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
