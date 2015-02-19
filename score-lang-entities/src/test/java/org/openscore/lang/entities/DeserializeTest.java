package org.openscore.lang.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.openscore.lang.entities.bindings.Input;
import org.openscore.lang.entities.bindings.Output;
import org.openscore.lang.entities.bindings.Result;

import java.io.IOException;

import static org.junit.Assert.*;

public class DeserializeTest {

    private ObjectMapper mapper = new ObjectMapper();


    private <T> void testToAndFromJson(Object objToTest, Class<T> type) throws IOException {
        byte[] objAsBytes = mapper.writeValueAsBytes(objToTest);
        T objAfterDeserialize = mapper.readValue(objAsBytes, type);
        assertEquals(objToTest, objAfterDeserialize);

        String objAsString = mapper.writeValueAsString(objToTest);
        objAfterDeserialize = mapper.readValue(objAsString, type);
        assertEquals(objToTest, objAfterDeserialize);
    }

    @Test
    public void testDeserializeInput() throws IOException {
        Input input = new Input(
                "new_input",
                "some_expression",
                true,
                true,
                true,
                "system_property_ok_a_kind");
        testToAndFromJson(input, Input.class);
    }

    @Test
    public void testDeserializeOutput() throws IOException {
        Output output = new Output(
                "new_output",
                "some_expression");
        testToAndFromJson(output, Output.class);
    }

    @Test
    public void testDeserializeResult() throws IOException {
        Result result = new Result(
                "new_result",
                "some_expression");
        testToAndFromJson(result, Result.class);
    }

    @Test
    public void testDeserializeResultNavigation() throws IOException {
        ResultNavigation resultNavigation = new ResultNavigation(
                1L,
                "a preset result");
        testToAndFromJson(resultNavigation, ResultNavigation.class);
    }

}