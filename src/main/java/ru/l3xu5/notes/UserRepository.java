package ru.l3xu5.notes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    default void addNote(long id, Date dateTime, String note) {
        this.getReferenceById(id).addNote(dateTime, note);
    }

    @Transactional
    @Modifying
    default void removeNote(long id, Date dateTime) {
        this.getReferenceById(id).removeNote(dateTime);
    }

    @Transactional
    default String getNotes(long id) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Date, String> entry : this.getReferenceById(id).getNotes().entrySet())  {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
}
