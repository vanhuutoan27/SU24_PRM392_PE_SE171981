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

import com.example.prm392_pe_su24.utils.APIService;
import com.example.prm392_pe_su24.utils.RetrofitClient;
import com.example.prm392_pe_su24.R;
import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.view.activity.EditMajorActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MajorAdapter extends BaseAdapter {
    private Context context;
    private List<Major> majorList;
    private LayoutInflater inflater;

    public MajorAdapter(Context context, List<Major> majorList) {
        this.context = context;
        this.majorList = majorList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return majorList.size();
    }

    @Override
    public Object getItem(int position) {
        return majorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.major_list_item, parent, false);
        }

        Major major = majorList.get(position);

        TextView tvMajorName = convertView.findViewById(R.id.tvMajorName);

        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvMajorName.setText(major.getMajorName());

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditMajorActivity.class);
            intent.putExtra("majorId", major.getId());
            intent.putExtra("majorName", major.getMajorName());
            context.startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            confirmDialog(major, position);
        });

        return convertView;
    }

    private void deleteMajor(Major major, int position) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Void> call = apiService.deleteMajor(major.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Major deleted successfully", Toast.LENGTH_SHORT).show();
                    majorList.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Failed to delete major", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmDialog(Major major, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this major?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteMajor(major, position));
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
