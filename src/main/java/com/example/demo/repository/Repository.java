package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    Employee findByEmail(String email);

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    @Query(value = "select * from users where country =:country and plan =:plan", nativeQuery = true)
    List<Employee> findAllByPlanAndCountry(@Param("country") String country, @Param("plan") Integer plan);

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, Pageable pageable);


}
