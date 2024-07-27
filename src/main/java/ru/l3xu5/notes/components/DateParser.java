package ru.l3xu5.notes.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DateParser {
    static Date parse(Scanner scanner) throws NotEnoughDateArgumentException, InvalidDateFormatException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyyHH.mm");
        try {
            return formatter.parse(scanner.next() + scanner.next());
        } catch (NoSuchElementException e) {
            throw new NotEnoughDateArgumentException("There are too few arguments\nIt should be like this: dd.MM.yyyy HH.mm");
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Yours date format is incorrect.\nIt should be like this: 'dd.MM.yyyy HH.mm'");
        }
    }
}
