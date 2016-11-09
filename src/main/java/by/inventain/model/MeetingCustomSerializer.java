package by.inventain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


public class MeetingCustomSerializer extends JsonSerializer<Meeting> {
    @Override
    public void serialize(Meeting meeting, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectField("startTime", meeting.getStartTime().toLocalTime());
        jgen.writeObjectField("endTime", meeting.getEndTime().toLocalTime());
        jgen.writeObjectField("empInfo", meeting.getSubmittedBy());
        jgen.writeEndObject();
    }

    @Override
    public Class<Meeting> handledType() {
        return Meeting.class;
    }
}
