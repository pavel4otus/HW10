package ru.pavel2107.otus.hw10.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String strDate = parser.readValueAs(String.class);
        System.out.println( "value=" + strDate);
        LocalDate curDate = strDate == "" ? null:  LocalDate.parse( strDate, DateTimeFormatter.ISO_LOCAL_DATE);
        return curDate;
    }
}