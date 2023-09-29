package com.calculateservice.integration;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.integration.annotation.IT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class GroupNumberControllerIT {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Test
    void findAllGroup() throws Exception {
        mockMvc.perform(
                        get("/group")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[1].groupNumberName", is("Специалисты")))
                .andExpect(jsonPath("$", hasSize(9)));
    }

    @Test
    void findGroupById() {
    }

    @Test
    void createGroupNumber() throws Exception {

        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(10, "Проверка");

        mockMvc.perform(
                        post("/group")
                                .content(objectMapper.writeValueAsString(groupNumberDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.groupNumberName").value("Проверка"));

    }

    @Test
    void updateGroupNumber() {
    }

    @Test
    void deleteGroupNumber() {
    }
}