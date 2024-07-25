package ru.l3xu5.notes;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
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
    private TreeMap<Date, String> notes = new TreeMap<>();
}
