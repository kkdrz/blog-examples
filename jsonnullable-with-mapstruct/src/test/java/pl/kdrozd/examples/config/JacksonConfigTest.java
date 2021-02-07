package pl.kdrozd.examples.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kdrozd.examples.dto.ProductDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class JacksonConfigTest {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void should_use_json_nullable_module() throws JsonProcessingException {
        assertEquals(JsonNullable.of("some description"), mapper.readValue("{\"description\":\"some description\"}", ProductDTO.class).getDescription());
        assertEquals(JsonNullable.of(null), mapper.readValue("{\"description\":null}", ProductDTO.class).getDescription());
        assertNull(mapper.readValue("{}", ProductDTO.class).getDescription());
    }
}