package com.calculateservice.integration;

import com.calculateservice.entity.GroupNumber;
import com.calculateservice.integration.annotation.IT;
import com.calculateservice.service.GroupNumberService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class GroupNumberServiceImplIT {

    private final GroupNumberService groupNumberService;

    private static final Long ID = 1L;

    @Test
    void findAll() {

        List<GroupNumber> actualResult = groupNumberService.findAll();

        assertEquals(9, actualResult.size());
    }

    @Test
    void findById() {

        GroupNumber actualResult = groupNumberService.findById(ID);

        assertEquals("Руководители", actualResult.getGroupNumberName());
    }
}
