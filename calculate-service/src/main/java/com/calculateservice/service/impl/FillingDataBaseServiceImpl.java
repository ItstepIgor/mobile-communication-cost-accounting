package com.calculateservice.service.impl;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.dto.AllExpensesByPhoneNumberDTO;
import com.calculateservice.entity.*;
import com.calculateservice.repository.*;
import com.calculateservice.service.FillingDataBaseService;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillingDataBaseServiceImpl implements FillingDataBaseService {

    private final ImportFeignClients importFeignClients;

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    private final MonthlyCallServiceCostRepository monthlyCallServiceCostRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final GroupNumberRepository groupNumberRepository;

    private final MobileOperatorRepository mobileOperatorRepository;


    public void fillingDataBase(LocalDate date) {
        List<AllCallServiceDTO> allCallServiceDTOS = findAllCommonCallService(date);
        createOneTimeCallServices(allCallServiceDTOS);
        List<MonthlyCallService> monthlyCallServices = createMonthlyCallService(allCallServiceDTOS);
        createMonthlyCallServiceCost(allCallServiceDTOS, monthlyCallServices);

        Set<Long> phoneNumberFromAllCallService = allCallServiceDTOS.stream()
                .map(AllCallServiceDTO::getNumber)
                .collect(Collectors.toSet());
        createPhoneNumber(phoneNumberFromAllCallService, mobileOperatorRepository.findById(1L).orElse(null));

        List<AllExpensesByPhoneNumberDTO> allExpensesByPhoneNumber = findAllExpensesByPhoneNumber(date);
        Set<Long> phoneNumberFromExpensesByPhoneNumber = allExpensesByPhoneNumber.stream()
                .map(allExpensesByPhoneNumberDTO -> Long.valueOf(allExpensesByPhoneNumberDTO.getNumber().substring(3, 12)))
                .collect(Collectors.toSet());
        createPhoneNumber(phoneNumberFromExpensesByPhoneNumber, mobileOperatorRepository.findById(2L).orElse(null));
    }


    private List<AllCallServiceDTO> findAllCommonCallService(LocalDate date) {
        return importFeignClients.findAllCommonCallService(date).getBody();
    }

    private List<AllExpensesByPhoneNumberDTO> findAllExpensesByPhoneNumber(LocalDate date) {
        return importFeignClients.findAllExpensesByPhoneNumberMTS(date).getBody();
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
                    oneTimeCallService.setCreationDate(LocalDateTime.now());
                    oneTimeCallServices.add(oneTimeCallService);
                });

        oneTimeCallServiceRepository.saveAll(oneTimeCallServices);
    }

    public List<OneTimeCallService> findAllOneTimeCallService() {

        return oneTimeCallServiceRepository.findAll();
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
                    monthlyCallService.setCreationDate(LocalDateTime.now());
                    monthlyCallServices.add(monthlyCallService);
                });
        return monthlyCallServiceRepository.saveAll(monthlyCallServices);
    }

    public List<MonthlyCallService> findAllMonthlyCallService() {

        return monthlyCallServiceRepository.findAll();
    }

    private void createMonthlyCallServiceCost(List<AllCallServiceDTO> allCallServiceDTOS,
                                              List<MonthlyCallService> monthlyCallServices) {
        List<MonthlyCallServiceCost> monthlyCallServiceCosts = new ArrayList<>();
        for (MonthlyCallService monthlyCallService : monthlyCallServices) {
            String monthlyCallServiceName = monthlyCallService.getMonthlyCallServiceName();

            List<AllCallServiceDTO> tempCallServiceDTOS = allCallServiceDTOS
                    .stream()
                    .filter(allCallServiceDTO -> allCallServiceDTO
                            .getCallServiceName().equals(monthlyCallServiceName))
                    .filter(distinctBySum(AllCallServiceDTO::getSum))
                    .toList();

            for (AllCallServiceDTO allCallServiceDTO : tempCallServiceDTOS) {
                MonthlyCallServiceCost monthlyCallServiceCost = new MonthlyCallServiceCost();
                monthlyCallServiceCost.setMonthlyCallService(monthlyCallService);
                monthlyCallServiceCost.setSum(allCallServiceDTO.getSum());
                monthlyCallServiceCost.setDateSumStart(LocalDate.now());
                monthlyCallServiceCosts.add(monthlyCallServiceCost);
            }
        }
        monthlyCallServiceCostRepository.saveAll(monthlyCallServiceCosts);
    }

    public static <T> Predicate<T> distinctBySum(
            Function<? super T, ?> sumExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(sumExtractor.apply(t), Boolean.TRUE) == null;
    }

    private void createPhoneNumber(Set<Long> phoneNumberFromAllCallService, MobileOperator mobileOperator) {
        List<PhoneNumber> allPhoneNumber = findAllPhoneNumber();

        Set<Long> phoneNumberName = allPhoneNumber.stream()
                .map(PhoneNumber::getNumber)
                .collect(Collectors.toSet());

        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        phoneNumberFromAllCallService.stream()
                .filter(number -> !phoneNumberName.contains(number))
                .forEach(number -> {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setNumber(number);
                    phoneNumber.setCreationDate(LocalDateTime.now());
                    phoneNumber.setGroupNumber(groupNumberRepository.findById(7L).orElse(null));
                    phoneNumber.setMobileOperator(mobileOperator);
                    phoneNumbers.add(phoneNumber);
                });

        phoneNumberRepository.saveAll(phoneNumbers);
    }

    public List<PhoneNumber> findAllPhoneNumber() {

        return phoneNumberRepository.findAll();
    }
}
