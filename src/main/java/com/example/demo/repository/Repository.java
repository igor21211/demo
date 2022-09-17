package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    Employee findByEmail(String email);
    @Query(value = "select * from users where country =:country and plan =:plan", nativeQuery = true)
    List<Employee> findAllByPlanAndCountry(@Param("country")String country, @Param("plan") Integer plan);

}
