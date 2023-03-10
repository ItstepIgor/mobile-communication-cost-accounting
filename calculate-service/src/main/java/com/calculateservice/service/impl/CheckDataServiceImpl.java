package com.calculateservice.service.impl;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.entity.MonthlyCallService;
import com.calculateservice.entity.MonthlyCallServiceCost;
import com.calculateservice.repository.MonthlyCallServiceCostRepository;
import com.calculateservice.repository.MonthlyCallServiceRepository;
import com.calculateservice.service.CheckDataService;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckDataServiceImpl implements CheckDataService {


    private final ImportFeignClients importFeignClients;

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    private final MonthlyCallServiceCostRepository monthlyCallServiceCostRepository;

    @Override
    public List<AllCallServiceDTO> checkSumMonthlyCallService(LocalDate date) {
        List<AllCallServiceDTO> allCallServiceDTOS = findAllCommonCallService(date);
        List<MonthlyCallService> monthlyCallServices = findAllMonthlyCallService();

        return checkSumMonthlyCallServiceCost(allCallServiceDTOS, monthlyCallServices);
    }

    private List<AllCallServiceDTO> checkSumMonthlyCallServiceCost(List<AllCallServiceDTO> allCallServiceDTOS,
                                                                   List<MonthlyCallService> monthlyCallServices) {
        List<AllCallServiceDTO> requestCheck;
        requestCheck = new ArrayList<>();

        for (MonthlyCallService monthlyCallService : monthlyCallServices) {

            MonthlyCallServiceCost monthlyCallServiceCost = findByIdMonthlyCallServiceCost(monthlyCallService.getId());

            AllCallServiceDTO tempAllCallServiceDTO = allCallServiceDTOS
                    .stream()
                    .filter(allCallServiceDTO1 ->
                            allCallServiceDTO1.getCallServiceName()
                                    .equals(monthlyCallService
                                            .getMonthlyCallServiceName()))
                    .max(Comparator.comparing(AllCallServiceDTO::getSum)).orElse(null);
            if ((tempAllCallServiceDTO != null) && (monthlyCallServiceCost != null) &&
                    !(tempAllCallServiceDTO.getSum().equals(monthlyCallServiceCost.getSum()))) {

                requestCheck.add(tempAllCallServiceDTO);
            }

        }
        return requestCheck;
    }


    private List<AllCallServiceDTO> findAllCommonCallService(LocalDate date) {
        return importFeignClients.findAllCommonCallService(date).getBody();
    }

    private List<MonthlyCallService> findAllMonthlyCallService() {

        return monthlyCallServiceRepository.findAll();
    }

    private MonthlyCallServiceCost findByIdMonthlyCallServiceCost(Long id) {
        return monthlyCallServiceCostRepository.findMonthlyCallServiceCosts(id);
    }
}
