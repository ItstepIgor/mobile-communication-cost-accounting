package com.importservice.service.mapper;

import com.importservice.dto.AllExpensesByPhoneNumberDTO;
import com.importservice.entity.AllExpensesByPhoneNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AllExpensesByPhoneNumberMapper {

    AllExpensesByPhoneNumberDTO allExpensesByPhoneNumberoAllExpensesByPhoneNumberDto
            (AllExpensesByPhoneNumber allExpensesByPhoneNumber);

    AllExpensesByPhoneNumber allExpensesByPhoneNumberDtoToAllExpensesByPhoneNumber
            (AllExpensesByPhoneNumberDTO allExpensesByPhoneNumberDTO);
}
