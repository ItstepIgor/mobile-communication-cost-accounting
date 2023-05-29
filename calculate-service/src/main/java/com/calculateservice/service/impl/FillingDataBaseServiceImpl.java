package com.calculateservice.service.impl;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.dto.AllExpensesByPhoneNumberDTO;
import com.calculateservice.entity.*;
import com.calculateservice.repository.*;
import com.calculateservice.service.FillingDataBaseService;
import com.calculateservice.service.MobileOperatorService;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final MonthlyCallServiceListRepository monthlyCallServiceListRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final GroupNumberRepository groupNumberRepository;

    private final MobileOperatorService mobileOperatorService;


    public void fillingDataBase(LocalDate date) {
        List<AllCallServiceDTO> allCallServiceDTOS = findAllCommonCallService(date);

        createPhoneNumber(allCallServiceDTOS);

        createOneTimeCallServices(allCallServiceDTOS);

        createMonthlyCallService(allCallServiceDTOS);

        createMonthlyCallServiceList(allCallServiceDTOS);

    }

    private void createMonthlyCallServiceList(List<AllCallServiceDTO> allCallServiceDTOS) {

        Set<MonthlyCallServiceList> monthlyCallServicesList = new HashSet<>();
        List<MonthlyCallServiceList> allMonthlyCallServiceList = findAllMonthlyCallServiceList();

        Set<String> monthlyCallServiceName = allMonthlyCallServiceList.stream()
                .map(MonthlyCallServiceList::getMonthlyCallServiceName)
                .collect(Collectors.toSet());

        allCallServiceDTOS.stream()
                .filter(allCallServiceDTO -> !allCallServiceDTO.getOneTimeCallService())
                .filter(allCallServiceDTO -> !monthlyCallServiceName.contains(allCallServiceDTO.getCallServiceName()))
                .forEach(allCallServiceDTO -> {
                    MonthlyCallServiceList monthlyCallService = MonthlyCallServiceList.builder()
                            .monthlyCallServiceName(allCallServiceDTO.getCallServiceName())
                            .build();
                    monthlyCallServicesList.add(monthlyCallService);
                });
        monthlyCallServiceListRepository.saveAll(monthlyCallServicesList);
    }

    public List<MonthlyCallServiceList> findAllMonthlyCallServiceList() {

        return monthlyCallServiceListRepository.findAll();
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
                    OneTimeCallService oneTimeCallService = OneTimeCallService.builder()
                            .oneTimeCallServiceName(allCallServiceDTO.getCallServiceName())
                            .build();
                    oneTimeCallServices.add(oneTimeCallService);
                });

        oneTimeCallServiceRepository.saveAll(oneTimeCallServices);
    }

    public List<OneTimeCallService> findAllOneTimeCallService() {

        return oneTimeCallServiceRepository.findAll();
    }

    private void createMonthlyCallService(List<AllCallServiceDTO> allCallServiceDTOS) {
        List<MonthlyCallService> monthlyCallServices = new ArrayList<>();

        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();

        allCallServiceDTOS.stream()
                .filter(allCallServiceDTO -> !allCallServiceDTO.getOneTimeCallService())
                .forEach(allCallServiceDTO -> {
                    MonthlyCallService monthlyCallService = MonthlyCallService.builder()
                            .monthlyCallServiceName(allCallServiceDTO.getCallServiceName())
                            .vatTax(allCallServiceDTO.getVatTax())
                            .invoiceDate(allCallServiceDTO.getInvoiceDate())
                            .phoneNumber(phoneNumbers.stream()
                                    .filter(phoneNumber -> phoneNumber.getNumber() == allCallServiceDTO.getNumber())
                                    .findFirst().orElse(null))
                            .sum(allCallServiceDTO.getSum())
                            .sumWithNDS(allCallServiceDTO.getSumWithNDS())
                            .build();
                    //TODO написать и обработать свою ошибку (orElseThrow()) в случае отсутствия номера
                    monthlyCallServices.add(monthlyCallService);
                });
        monthlyCallServiceRepository.saveAll(monthlyCallServices);
    }

    public List<MonthlyCallService> findAllMonthlyCallService() {

        return monthlyCallServiceRepository.findAll();
    }

    public static <T> Predicate<T> distinctByField(
            Function<? super T, ?> sumExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(sumExtractor.apply(t), Boolean.TRUE) == null;
    }

    private void createPhoneNumber(List<AllCallServiceDTO> allCallServiceDTOS) {
        List<PhoneNumber> allPhoneNumber = findAllPhoneNumber();

        Set<Long> phoneNumberName = allPhoneNumber.stream()
                .map(PhoneNumber::getNumber)
                .collect(Collectors.toSet());

        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        allCallServiceDTOS.stream()
                .filter(distinctByField(AllCallServiceDTO::getNumber))
                .filter(allCallServiceDTO -> !phoneNumberName.contains(allCallServiceDTO.getNumber()))
                .forEach(allCallServiceDTO -> {
                    PhoneNumber phoneNumber = PhoneNumber.builder()
                            .number(allCallServiceDTO.getNumber())
                            .groupNumber(groupNumberRepository.findById(9L).orElse(null))
                            .mobileOperator(mobileOperatorService
                                    .findById(allCallServiceDTO.getMobileOperator()))
                            .build();
                    phoneNumbers.add(phoneNumber);
                });

        phoneNumberRepository.saveAll(phoneNumbers);
    }

    public List<PhoneNumber> findAllPhoneNumber() {

        return phoneNumberRepository.findAll();
    }
}
