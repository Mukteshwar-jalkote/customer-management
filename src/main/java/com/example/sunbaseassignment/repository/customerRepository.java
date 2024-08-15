package com.example.sunbaseassignment.repository;

import com.example.sunbaseassignment.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface customerRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);

    public void deleteByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:searchTerm%")
    List<Customer> findByFirstNameLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.phone LIKE %:searchTerm%")
    List<Customer> findByPhoneLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.city LIKE %:searchTerm%")
    List<Customer> findByCityLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:searchTerm%")
    List<Customer> findByEmailLike(String searchTerm);

}
