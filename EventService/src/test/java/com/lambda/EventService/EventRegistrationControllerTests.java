package com.lambda.EventService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambda.EventService.Controllers.CommentsController;
import com.lambda.EventService.Controllers.EventRegistrationController;
import com.lambda.EventService.ExceptionHandling.CustomEventException;
import com.lambda.EventService.Models.EventComments;
import com.lambda.EventService.Models.UserEventRegistration;
import org.codehaus.jettison.json.JSONStringer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventRegistrationControllerTests {
    private final String URL = "/eventRegistration";

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRegistrationController eventRegistrationController;

    @Test
    void contextLoads() {
        assertThat(eventRegistrationController).isNotNull();
    }

    @Test
    void assertEventRegistrationExists() throws Exception {
        //EventRegistration with ID = 0 does not exist
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL + "/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL+"/7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void assertEventRegistrationTypeExists() throws Exception {
        //EventRegistrationType with ID = 0 does not exist
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL + "/type/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL+"/type/7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void assertEventRegistrationWithUserExists() throws Exception {
        //EventRegistration with User ID = 0 does not exist, but should be empty list
        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL + "/user/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URL+"/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void assertCanPostNewComments() throws Exception{
        String content = objectMapper.writeValueAsString(new UserEventRegistration());
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(URL+"/registerUser/123")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(URL+"/registerUser")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}