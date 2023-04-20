package com.importservice.service.mapper;

import com.importservice.dto.AllExpensesByPhoneNumberDTO;
import com.importservice.entity.AllExpensesByPhoneNumber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AllExpensesByPhoneNumberMapper.class)
public interface AllExpensesByPhoneNumberListMapper {

    List<AllExpensesByPhoneNumberDTO> listExpensesByPhoneNumberToListExpensesByPhoneNumberDto
            (List<AllExpensesByPhoneNumber> allExpensesByPhoneNumbers);

    List<AllExpensesByPhoneNumber> listExpensesByPhoneNumberDtoToListExpensesByPhoneNumber
            (List<AllExpensesByPhoneNumberDTO> allExpensesByPhoneNumberDTOS);
}
