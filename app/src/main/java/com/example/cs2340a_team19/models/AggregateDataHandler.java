package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AggregateDataHandler<T extends Aggregatable> extends DataHandler<List<T>> {
    private Class<T> elementDataClass;
    public AggregateDataHandler(DatabaseReference db, String userID, Class<T> elementDataClass) {
        super(db, userID, null);
        this.elementDataClass = elementDataClass;
    }

    @Override
    public List<T> parseData(DataSnapshot dataSnapshot) {
        List<T> data = new ArrayList<>();
        for (DataSnapshot subSnapshot: dataSnapshot.getChildren()) {
            T element = parseElementData(subSnapshot);
            element.setId(subSnapshot.getKey());
            data.add(element);
        }
        return data;
    }

    public T parseElementData(DataSnapshot dataSnapshot) {
        // TODO: Could Error!
        return dataSnapshot.getValue(this.elementDataClass);
    }

    public void update(T element) {
        super.getDbReference().child(element.getId()).setValue(element);
    }
    public void append(T element) {
        // TODO: COULD ERROR
        super.getDbReference().push().setValue(element);
    }

    public void remove(T element) {
        this.getDbReference().child(element.getId()).removeValue().addOnCompleteListener(
                task -> {
                    if (!task.isSuccessful()) {
                        Log.d("FBRTDB_ERROR", "Tried to remove data of type "
                                + element.getClass().toString() + "but was unsuccessful");
                    }
                });
    }

    public void remove(int index) {
        this.remove(super.getData().get(index));
    }
}
