package by.inventain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;


public class CompanyCustomSerializer extends JsonSerializer<Company> {
    @Override
    public void serialize(Company input, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeObjectField("name", input.getName());
        jgen.writeObjectField("openTime", input.getOpenTime());
        jgen.writeObjectField("closeTime", input.getCloseTime());
        if (input.getMeetings() == null) {
            jgen.writeEndObject();
            return;
        }
        for (int i = 0; i < input.getMeetings().size(); ) {
            LocalDate currDate = input.getMeetings().get(i).getStartTime().toLocalDate();
            jgen.writeObjectField("meetigsDay", currDate);
            jgen.writeArrayFieldStart("listOfMeetings");
            while (i < input.getMeetings().size()) {
                if (currDate.compareTo(input.getMeetings().get(i).getStartTime().toLocalDate()) == 0) {
                    jgen.writeObject(input.getMeetings().get(i));
                    i++;
                } else break;
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();

    }

    @Override
    public Class<Company> handledType() {
        return Company.class;
    }
}