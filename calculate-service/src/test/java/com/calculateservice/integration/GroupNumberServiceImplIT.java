package com.calculateservice.integration;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.integration.annotation.IT;
import com.calculateservice.service.GroupNumberService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupNumberServiceImplIT {

    private final GroupNumberService groupNumberService;

    private static final Long GROPE_ID_1 = 1L;
    private static final Long GROPE_ID_10 = 10L;

    @Test
    @Order(1)
    void findAll() {

        List<GroupNumberDTO> actualResult = groupNumberService.findAll();

        assertEquals(9, actualResult.size());
    }

    @Test
    @Order(2)
    void findById() {

        GroupNumberDTO actualResult = groupNumberService.findById(GROPE_ID_1);

        assertEquals("Руководители", actualResult.getGroupNumberName());
    }

    @Test
    @Order(3)
    void create() {
        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(GROPE_ID_10, "TestGroup");

        GroupNumberDTO actualResult = groupNumberService.create(groupNumberDTO);

        assertEquals(groupNumberDTO.getGroupNumberName(), actualResult.getGroupNumberName());

    }

    @Test
    @Order(4)
    void update() {
        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(GROPE_ID_1, "TestGroup");

        GroupNumberDTO groupNumber = groupNumberService.update(groupNumberDTO);

        assertEquals(groupNumberDTO.getGroupNumberName(), groupNumber.getGroupNumberName());

    }


    @Test
    @Order(5)
    void delete() {

        GroupNumberDTO groupNumberDTO = new GroupNumberDTO(GROPE_ID_10, "TestGroup");

        GroupNumberDTO groupNumberActul = groupNumberService.create(groupNumberDTO);

        List<GroupNumberDTO> actualResult = groupNumberService.findAll();

        assertEquals(10, actualResult.size());

        groupNumberService.delete(groupNumberActul.getId());

        assertNull(groupNumberService.findById(groupNumberActul.getId()));

        assertEquals(9, groupNumberService.findAll().size());

    }

}
