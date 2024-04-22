package com.example.cs2340a_team19.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataHandler<T> {
    private final String userID;
    private final DatabaseReference dbReference;
    private final Class<T> dataClass;
    protected T data;
    private List<DataUpdateListener<T>> listeners;
    private DataMutator<T> cleaner;
    public DataHandler(DatabaseReference db, String userID, Class<T> dataClass,
                       DataMutator<T> cleaner) {
        this.dbReference = db;
        this.userID = userID;
        this.dataClass = dataClass;
        this.cleaner = cleaner;

        this.listeners = new ArrayList<DataUpdateListener<T>>();
        this.listenToData();
    }

    public void addDataUpdateListener(DataUpdateListener<T> d) {
        this.listeners.add(d);
        if (this.data != null) {
            d.update(cleaner.mutate(this.data));
        }
    }

    public void removeDataUpdateListener(DataUpdateListener<T> d) {
        this.listeners.remove(d);
    }

    public void clearDataUpdateListeners() {
        this.listeners.clear();
    }

    public void setData(T data) {
        this.dbReference.setValue(data);
    }

    public void removeData() {
        this.dbReference.removeValue().addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Log.d("FBRTDB_ERROR", "Tried to remove data of type "
                                + data.getClass().toString() + "but was unsuccessful");
                    }
                });
    }

    public <K> void setChild(String location, K childData) {
        this.dbReference.child(location).setValue(childData);
    }

    private void listenToData() {
        this.dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = parseData(snapshot);
                updateListeners();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FBRTDB_ERROR", "DataHandler of type "
                        + data.getClass().toString() + " was cancelled");
            }
        });
    }

    protected void updateListeners() {
        if (this.data != null) {
            for (DataUpdateListener<T> listener : this.listeners) {
                listener.update(cleaner.mutate(this.data));
            }
        }
    }

    public String getUserID() {
        return userID;
    }

    public DatabaseReference getDbReference() {
        return dbReference;
    }

    public T getData() {
        return cleaner.mutate(this.data);
    }

    public T parseData(@NonNull DataSnapshot snapshot) {
        return snapshot.getValue(this.dataClass);
    }
}
