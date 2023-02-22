package com.calculateservice.service.impl;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.entity.MonthlyCallService;
import com.calculateservice.entity.MonthlyCallServiceCost;
import com.calculateservice.entity.OneTimeCallService;
import com.calculateservice.repository.MonthlyCallServiceCostRepository;
import com.calculateservice.repository.MonthlyCallServiceRepository;
import com.calculateservice.repository.OneTimeCallServiceRepository;
import com.calculateservice.service.CalculateService;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    private final ImportFeignClients importFeignClients;

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    private final MonthlyCallServiceCostRepository monthlyCallServiceCostRepository;


    public void createAllCallService() {
        List<AllCallServiceDTO> allCallServiceDTOS = findAllCommonCallService();
        createOneTimeCallServices(allCallServiceDTOS);
        List<MonthlyCallService> monthlyCallServices = createMonthlyCallService(allCallServiceDTOS);
        createMonthlyCallServiceCost(allCallServiceDTOS, monthlyCallServices);
    }


    private void createMonthlyCallServiceCost(List<AllCallServiceDTO> allCallServiceDTOS,
                                              List<MonthlyCallService> monthlyCallServices) {
        List<MonthlyCallServiceCost> monthlyCallServiceCosts = new ArrayList<>();
        for (MonthlyCallService monthlyCallService : monthlyCallServices) {
            String monthlyCallServiceName = monthlyCallService.getMonthlyCallServiceName();
            MonthlyCallServiceCost monthlyCallServiceCost = new MonthlyCallServiceCost();
            for (AllCallServiceDTO allCallServiceDTO : allCallServiceDTOS) {
                if (allCallServiceDTO.getCallServiceName().equals(monthlyCallServiceName)) {
                    monthlyCallServiceCost.setMonthlyCallService(monthlyCallService);
                    monthlyCallServiceCost.setSum(allCallServiceDTO.getSum());
                    monthlyCallServiceCost.setDateSumStart(LocalDate.now());
                }
            }
            monthlyCallServiceCosts.add(monthlyCallServiceCost);
        }
        monthlyCallServiceCostRepository.saveAll(monthlyCallServiceCosts);
    }

    private List<MonthlyCallService> createMonthlyCallService(List<AllCallServiceDTO> allCallServiceDTOS) {
        Set<MonthlyCallService> monthlyCallServices = new HashSet<>();

        List<MonthlyCallService> allMonthlyCallService = findAllMonthlyCallService();

        Set<String> monthlyCallServiceName = allMonthlyCallService.stream()
                .map(MonthlyCallService::getMonthlyCallServiceName)
                .collect(Collectors.toSet());

        allCallServiceDTOS.stream()
                .filter(allCallServiceDTO -> !allCallServiceDTO.getOneTimeCallService())
                .filter(allCallServiceDTO -> !monthlyCallServiceName.contains(allCallServiceDTO.getCallServiceName()))
                .forEach(allCallServiceDTO -> {
                    MonthlyCallService monthlyCallService = new MonthlyCallService();
                    monthlyCallService.setMonthlyCallServiceName(allCallServiceDTO.getCallServiceName());
                    monthlyCallServices.add(monthlyCallService);
                });
        return monthlyCallServiceRepository.saveAll(monthlyCallServices);
    }

    private List<AllCallServiceDTO> findAllCommonCallService() {
        return importFeignClients.findAllCommonCallService().getBody();
    }

    private void createOneTimeCallServices(List<AllCallServiceDTO> allCallServiceDTOS) {
        Set<OneTimeCallService> oneTimeCallServices = new HashSet<>();
        List<OneTimeCallService> allOneTimeCallService = findAllOneTimeCallService();

        Set<String> oneTimeCallServiceName = allOneTimeCallService.stream()
                .map(OneTimeCallService::getOneTimeCallServiceName)
                .collect(Collectors.toSet());

        allCallServiceDTOS.stream()
                .filter(AllCallServiceDTO::getOneTimeCallService)
                .filter(allCallServiceDTO -> !oneTimeCallServiceName.contains(allCallServiceDTO.getCallServiceName()))
                .forEach(allCallServiceDTO -> {
                    OneTimeCallService oneTimeCallService = new OneTimeCallService();
                    oneTimeCallService.setOneTimeCallServiceName(allCallServiceDTO.getCallServiceName());
                    oneTimeCallServices.add(oneTimeCallService);
                });

        oneTimeCallServiceRepository.saveAll(oneTimeCallServices);
    }

    public List<OneTimeCallService> findAllOneTimeCallService() {

        return oneTimeCallServiceRepository.findAll();
    }

    public List<MonthlyCallService> findAllMonthlyCallService() {

        return monthlyCallServiceRepository.findAll();
    }
}
