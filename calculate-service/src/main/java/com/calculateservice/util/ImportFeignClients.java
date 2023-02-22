package com.calculateservice.util;

import com.calculateservice.dto.AllCallServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "import-service")
public interface ImportFeignClients {

    @PostMapping("/calls/service")
    ResponseEntity<List<AllCallServiceDTO>> findAllCommonCallService();
}
