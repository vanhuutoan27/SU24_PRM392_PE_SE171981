package com.example.prm392_pe_su24.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_pe_su24.view.adapter.MajorAdapter;
import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.view.adapter.StudentAdapter;
import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.model.Student;
import com.example.prm392_pe_su24.utils.RetrofitClient;
import com.example.prm392_pe_su24.utils.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lvMajorList, lvStudentList;
    private StudentAdapter studentAdapter;
    private MajorAdapter majorAdapter;
    private List<Student> studentList;
    private List<Major> majorList;
    private Button btnAddStudent, btnAddMajor, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMajorList = findViewById(R.id.lvMajorList);
        lvStudentList = findViewById(R.id.lvStudentList);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddMajor = findViewById(R.id.btnAddMajor);
        btnShowMap = findViewById(R.id.btnShowMap);

        getAllMajors();

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        btnAddMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMajorActivity.class);
                startActivity(intent);
            }
        });

        btnShowMap.setOnClickListener(v -> {
            if (studentList == null || studentList.isEmpty()) {
                Toast.makeText(MainActivity.this, "Student list is empty or not loaded yet", Toast.LENGTH_SHORT).show();
                return;
            }

            Student studentWithId1 = null;
            for (Student student : studentList) {
                if (student.getId().equals("1")) {
                    studentWithId1 = student;
                    break;
                }
            }

            if (studentWithId1 != null) {
                String studentAddress = studentWithId1.getAddress(); // Lấy địa chỉ của sinh viên
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra("studentAddress", studentAddress); // Gửi địa chỉ sang MapActivity
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Student with ID 1 not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMajors();
    }

    private void getAllStudents(final List<Major> majorList) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<List<Student>> call = apiService.getAllStudents();

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    studentList = response.body();
                    studentAdapter = new StudentAdapter(MainActivity.this, studentList, majorList);
                    lvStudentList.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve students", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllMajors() {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<List<Major>> call = apiService.getAllMajors();

        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, Response<List<Major>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    majorList = response.body();
                    majorAdapter = new MajorAdapter(MainActivity.this, majorList);
                    lvMajorList.setAdapter(majorAdapter);
                    majorAdapter.notifyDataSetChanged();

                    getAllStudents(majorList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve majors", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
