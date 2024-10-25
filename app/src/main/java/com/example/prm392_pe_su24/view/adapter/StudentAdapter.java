package com.example.prm392_pe_su24.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.model.Student;
import com.example.prm392_pe_su24.utils.APIService;
import com.example.prm392_pe_su24.utils.RetrofitClient;
import com.example.prm392_pe_su24.view.activity.EditStudentActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private List<Student> studentList;
    private List<Major> majorList;
    private LayoutInflater inflater;

    public StudentAdapter(Context context, List<Student> studentList, List<Major> majorList) {
        this.context = context;
        this.studentList = studentList;
        this.majorList = majorList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
        }

        Student student = studentList.get(position);

        TextView tvStudentName = convertView.findViewById(R.id.tvStudentName);
        TextView tvStudentMajor = convertView.findViewById(R.id.tvStudentMajor);
        TextView tvStudentEmail = convertView.findViewById(R.id.tvStudentEmail);
        TextView tvStudentAddress = convertView.findViewById(R.id.tvStudentAddress);
        TextView tvStudentGender = convertView.findViewById(R.id.tvStudentGender);
        TextView tvStudentDate = convertView.findViewById(R.id.tvStudentDate);

        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvStudentName.setText(student.getName());

        String majorName = getMajorNameById(student.getMajorId());
        tvStudentMajor.setText(majorName);

        tvStudentEmail.setText(student.getEmail());
        tvStudentAddress.setText(student.getAddress());
        tvStudentGender.setText(student.getGender());
        tvStudentDate.setText(student.getDate());

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra("studentId", student.getId());
            intent.putExtra("studentName", student.getName());
            intent.putExtra("studentEmail", student.getEmail());
            intent.putExtra("studentDate", student.getDate());
            intent.putExtra("studentGender", student.getGender());
            intent.putExtra("studentAddress", student.getAddress());
            intent.putExtra("studentMajor", student.getMajorId());
            context.startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            confirmDialog(student, position);
        });

        return convertView;
    }

    private String getMajorNameById(String majorId) {
        for (Major major : majorList) {
            if (major.getId().equals(majorId)) {
                return major.getMajorName();
            }
        }
        return "Unknown Major";
    }

    private void deleteStudent(Student student, int position) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Void> call = apiService.deleteStudent(student.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                    studentList.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Failed to delete student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmDialog(Student student, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this student?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteStudent(student, position));
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
