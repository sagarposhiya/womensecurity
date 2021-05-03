package com.example.womensecurity.databse;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.womensecurity.models.AdharCard;
import com.example.womensecurity.models.Register;

@Database(entities = {AdharCard.class, Register.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
    public abstract AdharcardDao adharcardDao();
    public abstract RegisterDao registerDao();
}
