package com.calculateservice.service;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.service.impl.GroupNumberServiceImpl;
import com.calculateservice.service.mapper.GroupNumberListMapper;
import com.calculateservice.service.mapper.GroupNumberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import java.util.List;

//@IT
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class GroupNumberServiceImplTest {

    @Mock
    private GroupNumberRepository groupNumberRepository;

    @Mock
    private GroupNumberMapper groupNumberMapper;

    @Mock
    private GroupNumberListMapper groupNumberListMapper;

    @InjectMocks
    private GroupNumberServiceImpl groupNumberService;

    private GroupNumber groupNumberOne;
    private GroupNumberDTO groupNumberDTOOne;
    private GroupNumber groupNumberTwo;
    private GroupNumberDTO groupNumberDTOTwo;

    private List<GroupNumber> groupNumbers;
    private List<GroupNumberDTO> groupNumberDTOS;

    private static final Long ID = 1L;

    @BeforeEach
    void init() {
        groupNumberOne = GroupNumber.builder()
                .id(1)
                .groupNumberName("Руководители")
                .build();

        groupNumberDTOOne = new GroupNumberDTO(1, "Руководители");

        groupNumberTwo = GroupNumber.builder()
                .id(2)
                .groupNumberName("Специалисты")
                .build();

        groupNumberDTOTwo = new GroupNumberDTO(2, "Специалисты");

        groupNumberDTOS = List.of(groupNumberDTOOne, groupNumberDTOTwo);
    }

    @Test
    void findAll() {
        groupNumberDTOS = List.of(groupNumberDTOOne, groupNumberDTOTwo);
        groupNumbers = List.of(groupNumberOne, groupNumberTwo);

        Mockito.when(groupNumberRepository.findAll()).thenReturn(groupNumbers);
        Mockito.when(groupNumberListMapper.toListDTO(groupNumbers)).thenReturn(groupNumberDTOS);

        List<GroupNumberDTO> groupNumbers = groupNumberService.findAll();

        Assertions.assertEquals(2, groupNumbers.size());
        Assertions.assertEquals(groupNumberDTOOne, groupNumbers.get(0));
        Assertions.assertEquals(groupNumberDTOTwo, groupNumbers.get(1));
    }

    @Test
    void findById() {
//        Mockito.when(groupNumberRepository.findById(ID)).thenReturn(Optional.ofNullable(groupNumberOne));
        Mockito.doReturn(Optional.of(groupNumberOne)).when(groupNumberRepository).findById(ID);
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberMapper).toDTO(groupNumberOne);

        GroupNumberDTO groupNumber = groupNumberService.findById(ID);


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