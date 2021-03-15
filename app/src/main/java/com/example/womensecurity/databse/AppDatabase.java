package com.example.womensecurity.databse;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.womensecurity.models.AdharCard;

@Database(entities = {AdharCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AdharcardDao adharcardDao();
}
