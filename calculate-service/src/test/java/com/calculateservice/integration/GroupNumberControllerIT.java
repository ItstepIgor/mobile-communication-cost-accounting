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
    void findGroupById() throws Exception {

        mockMvc.perform(
                        get("/group/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                //библиотека org.hamcrest.Matchers
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.groupNumberName", is("Руководители")))
                //библиотека org.springframework.test.web.servlet.result
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.groupNumberName").value("Руководители"));
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
    void updateGroupNumber() throws Exception {

        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(1, "Проверка");

        mockMvc.perform(
                        put("/group")
                                .content(objectMapper.writeValueAsString(groupNumberDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.groupNumberName").value("Проверка"));

    }

    @Test
    void deleteGroupNumber() throws Exception {

        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(10, "Проверка");

        mockMvc.perform(
                post("/group")
                        .content(objectMapper.writeValueAsString(groupNumberDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                        delete("/group/{id}", 10))
                .andExpect(status().is2xxSuccessful());

    }
}