package com.calculateservice.service;

import com.calculateservice.CalculateServiceApplication;
import com.calculateservice.entity.GroupNumber;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.service.impl.GroupNumberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import java.util.List;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CalculateServiceApplication.class)
@ActiveProfiles("test")
class GroupNumberServiceImplTest {

    @Mock
    private GroupNumberRepository groupNumberRepository;

    @InjectMocks
    private GroupNumberServiceImpl groupNumberService;

    private GroupNumber groupNumberOne;
    private GroupNumber groupNumberTwo;

    private static final Long ID = 1L;

    @BeforeEach
    void init() {
        groupNumberOne = GroupNumber.builder()
                .id(1)
                .groupNumberName("Руководители")
                .build();

        groupNumberTwo = GroupNumber.builder()
                .id(2)
                .groupNumberName("Специалисты")
                .build();
    }

    @Test
    void findAll() {
        Mockito.when(groupNumberService.findAll()).thenReturn(List.of(groupNumberOne, groupNumberTwo));

        List<GroupNumber> groupNumbers = groupNumberService.findAll();

        Assertions.assertEquals(2, groupNumbers.size());
        Assertions.assertEquals(groupNumberOne, groupNumbers.get(0));
        Assertions.assertEquals(groupNumberTwo, groupNumbers.get(1));
    }

    @Test
    void findById() {
//        Mockito.when(groupNumberRepository.findById(ID)).thenReturn(Optional.ofNullable(groupNumberOne));
        Mockito.doReturn(Optional.of(groupNumberOne)).when(groupNumberRepository).findById(ID);

        GroupNumber groupNumber = groupNumberService.findById(ID);


        Assertions.assertEquals("Руководители", groupNumber.getGroupNumberName());

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}