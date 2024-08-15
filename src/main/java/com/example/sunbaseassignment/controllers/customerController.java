package com.example.sunbaseassignment.controllers;

import com.example.sunbaseassignment.Dto.Request.customerRequestDto;
import com.example.sunbaseassignment.Dto.Responce.customerResponseDto;
import com.example.sunbaseassignment.Exceptions.GlobalExceptionHandler.customerAlreadyExists;
import com.example.sunbaseassignment.Exceptions.GlobalExceptionHandler.customerNotFound;
import com.example.sunbaseassignment.Security.JwtHelperClass;
import com.example.sunbaseassignment.Service.RemoteApiCall;
import com.example.sunbaseassignment.Service.customerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/customer")
public class customerController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtHelperClass helperClass;

    @Autowired
    customerService customerService;

    @Autowired
    RemoteApiCall apiService;  // Inject ApiService using @Autowired

    @PostMapping("/create")
    public ResponseEntity<customerResponseDto> createCustomer(@RequestBody customerRequestDto customerRequestDto, @RequestParam boolean SyncDb){
        try {
            customerResponseDto customerResponseDto = customerService.createCustomer(customerRequestDto, SyncDb);
            log.info("Customer created successfully: {}", customerResponseDto);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
        } catch (customerAlreadyExists e) {
            log.error("Customer creation failed: {}", e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<customerResponseDto> updateCustomer(@PathVariable String emailId, @RequestBody customerRequestDto customerRequestDto){
        try {
            customerResponseDto customerResponseDto = customerService.updateCustomer(emailId, customerRequestDto);
            log.info("Customer updated successfully: {}", customerResponseDto);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.ACCEPTED);
        } catch(customerNotFound e) {
            log.error("Customer not found: {}", e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<Page<customerResponseDto>> getAllCustomers(@RequestParam int pageNo, @RequestParam int rowsCount, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String searchBy){
        log.info("Fetching all customers with pageNo: {}, rowsCount: {}, sortBy: {}, searchBy: {}", pageNo, rowsCount, sortBy, searchBy);
        Page<customerResponseDto> customerList = customerService.getAllCustomers(pageNo, rowsCount, sortBy, searchBy);
        log.info("Fetched {} customers", customerList.getContent().size());
        return new ResponseEntity<>(customerList, HttpStatus.FOUND);
    }

    @GetMapping("/searchBy")
    public ResponseEntity<List<customerResponseDto>> searchByCol(@RequestParam String searchBy, @RequestParam String searchQuery){
        log.info("Searching for customers by {} with query: {}", searchBy, searchQuery);
        List<customerResponseDto> result = customerService.searchByCol(searchBy, searchQuery);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<customerResponseDto> getCustomerWithId(@PathVariable String email){
        try {
            customerResponseDto customerResponseDto = customerService.getCustomerWithId(email);
            log.info("Fetched customer: {}", customerResponseDto);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.FOUND);
        } catch (customerNotFound e) {
            log.error("Customer not found: {}", e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam String email){
        try {
            String result = customerService.deleteCustomer(email);
            log.info("Customer deleted successfully: {}", result);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (customerNotFound e) {
            log.error("Customer not found: {}", e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/syncDB")
    public Object[] getToken() {
        Object[] customers = apiService.getToken();  // Using the injected ApiService
        return customers;
    }
}
