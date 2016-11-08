package by.inventain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class MeetingCustomSerializer extends JsonSerializer<Meeting> {
    @Override
    public void serialize(Meeting meeting, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeObjectField("startTime",meeting.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        jgen.writeObjectField("endTime",meeting.getStartTime().plusHours(meeting.getDuration()).format(DateTimeFormatter.ofPattern("HH:mm")));
        jgen.writeObjectField("empID",meeting.getSubmittedBy());
        jgen.writeEndObject();
    }
    @Override
    public Class<Meeting> handledType() {
        return Meeting.class;
    }
}
