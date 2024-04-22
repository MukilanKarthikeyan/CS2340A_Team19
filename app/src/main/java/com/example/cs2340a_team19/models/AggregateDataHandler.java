package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AggregateDataHandler<T extends Aggregatable> extends DataHandler<List<T>> {
    private Class<T> elementDataClass;
    private DataMutator<T> elementCleaner;
    public AggregateDataHandler(DatabaseReference db, String userID, Class<T> elementDataClass,
                                DataMutator<T> elementCleaner) {
        super(db, userID, null, (inList) -> {
            List<T> copy = new ArrayList<>();
            for (T curr : inList) {
                copy.add(elementCleaner.mutate(curr));
            }
            return copy;
        });
        // Yes the above looks jank af, but Java genuinely will not allow you to make it cleaner :(
        this.elementDataClass = elementDataClass;
        this.elementCleaner = elementCleaner;
        super.data = new ArrayList<>();
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
        return dataSnapshot.getValue(this.elementDataClass);
    }

    public void update(T element) {
        super.getDbReference().child(element.getId()).setValue(element);
    }
    public void append(T element) {
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
