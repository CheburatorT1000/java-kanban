package ru.yandex.practicum.tracker.HTTP;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;

class InstantAdapter extends TypeAdapter<Instant> {

    @Override
    public void write(final JsonWriter jsonWriter, final Instant instant) throws IOException {
        jsonWriter.value(instant.toEpochMilli());
    }

    @Override
    public Instant read(final JsonReader jsonReader) throws IOException {

        String timeAsString = jsonReader.nextString();
        long timeAsLong = Long.parseLong(timeAsString);

        return Instant.ofEpochMilli(timeAsLong);
    }
}