package com.calculateservice.controller;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.service.GroupNumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@WebMvcTest(GroupNumberController.class)
@RequiredArgsConstructor
class GroupNumberControllerTest {


    @MockBean
    private GroupNumberService groupNumberService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private GroupNumberDTO groupNumberDTOOne;

    private GroupNumberDTO groupNumberDTOTwo;

    @BeforeEach
    void init() {
        groupNumberDTOOne = new GroupNumberDTO(1, "Руководители");

        groupNumberDTOTwo = new GroupNumberDTO(2, "Специалисты");

    }

    @Test
    void findAllGroup() throws Exception {


        Mockito.when(groupNumberService.findAll()).thenReturn(Arrays.asList(groupNumberDTOOne, groupNumberDTOTwo));

        mockMvc.perform(
                        get("/group"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(groupNumberDTOOne, groupNumberDTOTwo))));

        Mockito.verify(groupNumberService, Mockito.times(1)).findAll();
    }

    @Test
    void findGroupById() throws Exception {

//        Mockito.when(groupNumberService.findById(Mockito.anyLong())).thenReturn(groupNumberDTOOne);
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberService).findById(Mockito.anyLong());

        mockMvc.perform(
                        get("/group/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.groupNumberName").value("Руководители"));

        Mockito.verify(groupNumberService, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void createGroupNumber() throws Exception {

//        Mockito.when(groupNumberService.create(Mockito.any())).thenReturn(groupNumberDTOOne);
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberService).create(Mockito.any());

        mockMvc.perform(
                        post("/group")
                                .content(objectMapper.writeValueAsString(groupNumberDTOOne))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(groupNumberDTOOne)));

        Mockito.verify(groupNumberService, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void updateGroupNumber() throws Exception {

//        Mockito.when(groupNumberService.update(groupNumberDTOOne)).thenReturn(groupNumberDTOOne);
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberService).update(Mockito.any());


        mockMvc.perform(
                        put("/group")
                                .content(objectMapper.writeValueAsString(groupNumberDTOOne))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.groupNumberName").value("Руководители"));

        Mockito.verify(groupNumberService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    void deleteGroupNumber() throws Exception {

        mockMvc.perform(
                        delete("/group/{id}", 1))
                .andExpect(status().isOk());
    }
}