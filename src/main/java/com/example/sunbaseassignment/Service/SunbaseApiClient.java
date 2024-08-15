package com.example.sunbaseassignment.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "sunbase-api", url = "https://qa.sunbasedata.com")
public interface SunbaseApiClient {

    @PostMapping("/sunbase/portal/api/assignment_auth.jsp")
    String authenticate(@RequestBody String requestBody);

    @GetMapping("/sunbase/portal/api/assignment.jsp?cmd=get_customer_list")
    Object[] getCustomers(@RequestHeader("Authorization") String authorizationHeader);
}
