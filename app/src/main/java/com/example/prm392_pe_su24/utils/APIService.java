package com.example.prm392_pe_su24.utils;

import com.example.prm392_pe_su24.model.Major;
import com.example.prm392_pe_su24.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface APIService {

    @GET("students")
    Call<List<Student>> getAllStudents();

    @POST("students")
    Call<Student> addStudent(@Body Student student);

    @PUT("students/{id}")
    Call<Student> updateStudent(@Path("id") String id, @Body Student student);

    @DELETE("students/{id}")
    Call<Void> deleteStudent(@Path("id") String id);

    @GET("majors")
    Call<List<Major>> getAllMajors();

    @POST("majors")
    Call<Major> addMajor(@Body Major major);

    @PUT("majors/{id}")
    Call<Major> updateMajor(@Path("id") String id, @Body Major major);

    @DELETE("majors/{id}")
    Call<Void> deleteMajor(@Path("id") String id);
}
