package com.nixesea.pushovertestapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM historyunit")
    List<HistoryUnit> getAll();

    @Query("SELECT * FROM historyunit WHERE id = :id")
    HistoryUnit getById(long id);

    @Insert
    void insert(HistoryUnit historyUnit);

    @Update
    void update(HistoryUnit historyUnit);

    @Delete
    void delete(HistoryUnit historyUnit);
}
