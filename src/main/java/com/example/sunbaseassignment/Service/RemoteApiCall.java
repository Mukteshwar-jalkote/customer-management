package com.example.sunbaseassignment.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoteApiCall {

    @Autowired
    private SunbaseApiClient sunbaseApiClient;

    public String callApi(String requestBody) {
        return sunbaseApiClient.authenticate(requestBody);
    }

    public List<Object> getCustomers(String token) {
        Object[] customers = sunbaseApiClient.getCustomers("Bearer " + token);
        return List.of(customers);
    }

    public Object[] getToken() {
        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";
        String token = callApi(requestBody);
        String accessToken = token.substring(19, token.length() - 3);
        List<Object> customers = getCustomers(accessToken);

        return customers.toArray();
    }
}

