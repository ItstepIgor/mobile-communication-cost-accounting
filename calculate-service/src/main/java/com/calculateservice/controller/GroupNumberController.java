package com.calculateservice.controller;

import com.calculateservice.dto.GroupNumberDTO;
import com.calculateservice.service.GroupNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
@Tag(name = "Редактирование и добавление групп абонентов")
@CrossOrigin("http://localhost:8765")
public class GroupNumberController {

    private final GroupNumberService groupNumberService;

    @Operation(
            summary = "Для получения информации по группам",
            description = "Получение информации по наименоваю групп пользователей "
    )
    @GetMapping
    List<GroupNumberDTO> findAllGroup() {
        return groupNumberService.findAll();
    }

    @Operation(
            summary = "Для получения информации по группе",
            description = "Получение информации по одной группе "
    )
    @GetMapping("/{id}")
    GroupNumberDTO findGroupById(@PathVariable long id) {
        return groupNumberService.findById(id);
    }


    @Operation(
            summary = "Создание груп ",
            description = "Добавление новой группы"
    )
    @PostMapping
    public void createGroupNumber(@RequestBody GroupNumberDTO groupNumberDTO) {
        groupNumberService.create(groupNumberDTO);
    }

    @Operation(
            summary = "Редактирование груп ",
            description = "Изменение группы"
    )
    @PutMapping
    public void updateGroupNumber(@RequestBody GroupNumberDTO groupNumberDTO) {
        groupNumberService.update(groupNumberDTO);
    }

    @Operation(
            summary = "Удаление груп ",
            description = "Удаление группы"
    )
    @DeleteMapping("/{id}")
    public void deleteGroupNumber (@PathVariable long id){
        groupNumberService.delete(id);
    }

}
