package com.calculateservice.controller;


import com.calculateservice.dto.OneTimeCallServiceDTO;
import com.calculateservice.dto.RuleOneTimeCallServiceDTO;
import com.calculateservice.service.OneTimeCallServiceService;
import com.calculateservice.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rule")
@Tag(name = "Добавление и редактирования правил для одноразовых услуг")
@CrossOrigin("http://localhost:8765")
public class RuleAndOneTimeCallServiceController {



    private final OneTimeCallServiceService oneTimeCallServiceService;

    private final RuleService ruleService;

    @Operation(
            summary = "Получение списка одноразовых сервисов",
            description = "Получение списка одноразовых сервисов," +
                    " которые не имеют периодической абонентской платы "
    )
    @GetMapping("/getonetimeservice")
    public List<OneTimeCallServiceDTO> findAllOneTimeService() {
        return oneTimeCallServiceService.findAll();
    }



    @Operation(
            summary = "Для получения информации по правилам",
            description = "Получение информации по правилам для одноразовых услуг"
    )
    @GetMapping
    List<RuleOneTimeCallServiceDTO> findAllRule() {
        return ruleService.findAll();
    }

    @Operation(
            summary = "Для получения информации по правилам",
            description = "Получение информации по одному правилу для одноразовых услуг"
    )
    @GetMapping("/{id}")
    RuleOneTimeCallServiceDTO findRuleById(@PathVariable long id) {
        return ruleService.findById(id);
    }


    @Operation(
            summary = "Создание правила",
            description = "Добавление нового правила"
    )
    @PostMapping
    public ResponseEntity<RuleOneTimeCallServiceDTO> createRule(
            @RequestBody RuleOneTimeCallServiceDTO ruleOneTimeCallServiceDTO) {

        return ResponseEntity.status(201).body(ruleService.create(ruleOneTimeCallServiceDTO));
    }

    @Operation(
            summary = "Редактирование правила",
            description = "Изменение правила"
    )
    @PutMapping
    public ResponseEntity<RuleOneTimeCallServiceDTO> updateRule(
            @RequestBody RuleOneTimeCallServiceDTO ruleOneTimeCallServiceDTO) {

        return ResponseEntity.ok().body(ruleService.update(ruleOneTimeCallServiceDTO));
    }

    @Operation(
            summary = "Удаление правила ",
            description = "Удаление правила"
    )
    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable long id) {
        ruleService.delete(id);
    }

}
