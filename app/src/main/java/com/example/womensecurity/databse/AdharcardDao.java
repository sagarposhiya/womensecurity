package com.example.womensecurity.databse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.womensecurity.models.AdharCard;

import java.util.List;

@Dao
public interface AdharcardDao {

    @Query("SELECT * FROM adharcard")
    List<AdharCard> getAll();

    @Insert
    void insert(AdharCard adharCard);

    @Delete
    void delete(AdharCard adharCard);

    @Update
    void update(AdharCard adharCard);
}
