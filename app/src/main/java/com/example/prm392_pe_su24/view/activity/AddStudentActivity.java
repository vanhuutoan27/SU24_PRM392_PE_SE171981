package com.example.prm392_pe_su24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.model.Student;
import com.example.prm392_pe_su24.utils.APIService;
import com.example.prm392_pe_su24.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentActivity extends AppCompatActivity {

    private EditText etStudentName, etStudentEmail, etStudentDate, etStudentGender, etStudentAddress, etStudentMajor;
    private Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etStudentName = findViewById(R.id.etStudentName);
        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentDate = findViewById(R.id.etStudentDate);
        etStudentGender = findViewById(R.id.etStudentGender);
        etStudentAddress = findViewById(R.id.etStudentAddress);
        etStudentMajor = findViewById(R.id.etStudentMajor);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addStudent() {
        String name = etStudentName.getText().toString();
        String email = etStudentEmail.getText().toString();
        String date = etStudentDate.getText().toString();
        String gender = etStudentGender.getText().toString();
        String address = etStudentAddress.getText().toString();
        String major = etStudentMajor.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Student name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Valid email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Date of birth is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            Toast.makeText(this, "Gender must be either 'Male' or 'Female'", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Address is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(major)) {
            Toast.makeText(this, "Major is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(name, email, date, gender, address, major);

        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Student> call = apiService.addStudent(student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddStudentActivity.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed to add student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(AddStudentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
