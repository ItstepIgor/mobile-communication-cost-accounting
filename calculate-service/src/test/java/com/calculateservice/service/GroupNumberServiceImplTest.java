package com.calculateservice.service;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.entity.GroupNumber;
import com.calculateservice.entity.OneTimeCallService;
import com.calculateservice.entity.RuleOneTimeCallService;
import com.calculateservice.repository.GroupNumberRepository;
import com.calculateservice.repository.RuleOneTimeCallServiceRepository;
import com.calculateservice.service.impl.GroupNumberServiceImpl;
import com.calculateservice.service.mapper.GroupNumberListMapper;
import com.calculateservice.service.mapper.GroupNumberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GroupNumberServiceImplTest {

    @Mock
    private GroupNumberRepository groupNumberRepository;

    @Mock
    private GroupNumberMapper groupNumberMapper;
    //Mapper можно заменить @Spy и использовать GroupNumberMapperImpl
    @Mock
    private GroupNumberListMapper groupNumberListMapper;

    @Mock
    private RuleOneTimeCallServiceRepository ruleOneTimeCallServiceRepository;

    @InjectMocks
    private GroupNumberServiceImpl groupNumberService;

    private GroupNumber groupNumberOne;
    private GroupNumberDTO groupNumberDTOOne;
    private GroupNumber groupNumberTwo;
    private GroupNumberDTO groupNumberDTOTwo;

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
        List<GroupNumber> groupNumbers = List.of(groupNumberOne, groupNumberTwo);

        Mockito.when(groupNumberRepository.findAll()).thenReturn(groupNumbers);
        Mockito.when(groupNumberListMapper.toListDTO(groupNumbers)).thenReturn(groupNumberDTOS);

        List<GroupNumberDTO> groupNumbersDTO = groupNumberService.findAll();

        Assertions.assertEquals(2, groupNumbersDTO.size());
        Assertions.assertEquals(groupNumberDTOOne, groupNumbersDTO.get(0));
        Assertions.assertEquals(groupNumberDTOTwo, groupNumbersDTO.get(1));
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

        Mockito.doReturn(groupNumberOne).when(groupNumberRepository).save(Mockito.any());
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberMapper).toDTO(groupNumberOne);

        GroupNumberDTO actualResult = groupNumberService.create(groupNumberDTOOne);

        Assertions.assertEquals(groupNumberDTOOne, actualResult);

    }

    @Test
    void update() {
        Mockito.doReturn(groupNumberOne).when(groupNumberRepository).save(Mockito.any());
        Mockito.doReturn(groupNumberDTOOne).when(groupNumberMapper).toDTO(groupNumberOne);

        groupNumberDTOOne.setGroupNumberName("Test");
        GroupNumberDTO actualResult = groupNumberService.update(groupNumberDTOOne);
        //перехватываем значение переданное в метод и сравнивааем его со значением с которым вызвали метод mapper
        ArgumentCaptor<GroupNumber> argumentCaptor = ArgumentCaptor.forClass(GroupNumber.class);
        Mockito.verify(groupNumberMapper).toDTO(argumentCaptor.capture());
        Mockito.verify(groupNumberRepository, Mockito.times(1)).save(Mockito.any());

        //Сравниваем значением с которым вызвали метод mapper
        Assertions.assertEquals(groupNumberOne, argumentCaptor.getValue());
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals("Test", actualResult.getGroupNumberName());
    }

    @Test
    void delete() {
        Mockito.doNothing().when(groupNumberRepository).deleteById(ID);

        groupNumberService.delete(ID);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(groupNumberRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(ID, argumentCaptor.getValue());
        Mockito.verify(groupNumberRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void addRuleToGroup() {
        OneTimeCallService oneTimeCallService = OneTimeCallService.builder()
                .id(13)
                .oneTimeCallServiceName("Исходящие внутри сети")
                .build();

        RuleOneTimeCallService ruleOneTimeCallService = RuleOneTimeCallService.builder()
                .id(1)
                .ruleName("Звонки внутри сети с 8-00 до 18-00")
                .oneTimeCallService(oneTimeCallService)
                .startPayment(LocalTime.parse("07:59:59"))
                .endPayment(LocalTime.parse("18:00:00"))
                .build();


        Mockito.doReturn(Optional.of(groupNumberOne)).when(groupNumberRepository).findById(ID);

        Mockito.doReturn(Optional.of(ruleOneTimeCallService)).when(ruleOneTimeCallServiceRepository).findById(Mockito.anyLong());

        groupNumberService.addRuleToGroup(groupNumberOne.getId(), ruleOneTimeCallService.getId());

        ArgumentCaptor<GroupNumber> argumentCaptor = ArgumentCaptor.forClass(GroupNumber.class);

        Mockito.verify(groupNumberRepository).save(argumentCaptor.capture());

        Assertions.assertEquals("Test", argumentCaptor.getValue());

    }
}