package ru.l3xu5.notes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

@Entity
@Table(name = "users")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @NonNull
    private Long id;

    @ElementCollection
    @CollectionTable(name="notes",
            joinColumns=@JoinColumn(name="user_id"))
    @MapKeyColumn(name="date_time")
    @Column(name="text")
    private SortedMap<Date, String> notes = new TreeMap<>();

    void addNote(Date dateTime, String note) {
        notes.put(dateTime, note);
    }

    public void removeNote(Date dateTime) {
        notes.remove(dateTime);
    }
}
