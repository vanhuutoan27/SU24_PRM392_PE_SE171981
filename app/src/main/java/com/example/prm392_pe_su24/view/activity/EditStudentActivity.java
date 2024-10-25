package com.example.prm392_pe_su24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class EditStudentActivity extends AppCompatActivity {
    private EditText etStudentName, etStudentEmail, etStudentDate, etStudentGender, etStudentAddress, etStudentMajor;
    private Button btnUpdate, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        etStudentName = findViewById(R.id.etStudentName);
        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentDate = findViewById(R.id.etStudentDate);
        etStudentGender = findViewById(R.id.etStudentGender);
        etStudentAddress = findViewById(R.id.etStudentAddress);
        etStudentMajor = findViewById(R.id.etStudentMajor);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String studentId = intent.getStringExtra("studentId");
        etStudentName.setText(intent.getStringExtra("studentName"));
        etStudentEmail.setText(intent.getStringExtra("studentEmail"));
        etStudentDate.setText(intent.getStringExtra("studentDate"));
        etStudentGender.setText(intent.getStringExtra("studentGender"));
        etStudentAddress.setText(intent.getStringExtra("studentAddress"));
        etStudentMajor.setText(intent.getStringExtra("studentMajor"));

        btnUpdate.setOnClickListener(v -> updateStudent(studentId));

        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(EditStudentActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void updateStudent(String studentId) {
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

        Student student = new Student(studentId, name, email, date, gender, address, major);

        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Student> call = apiService.updateStudent(studentId, student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditStudentActivity.this, "Student updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditStudentActivity.this, "Failed to update student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(EditStudentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
