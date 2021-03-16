package com.example.womensecurity.databse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Register;

import java.util.List;

@Dao
public interface RegisterDao {

    @Query("SELECT * FROM register")
    List<Register> getAll();

    @Insert
    void insert(Register register);

    @Delete
    void delete(Register register);

    @Update
    void update(Register register);
}
