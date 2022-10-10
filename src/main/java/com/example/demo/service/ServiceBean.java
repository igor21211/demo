package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.Repository;
import com.example.demo.util.Exeptions.ResourceNotFoundException;
import com.example.demo.util.Exeptions.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Date.from;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;

    @Override
    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee1 = repository.findByEmail(email);
        return employee1;
    }

    @Override
    public Page<Employee> getAllWithPagination(int page, int size, List<String> sortList, String sortOrder) {
        log.debug("getAllWithPagination() - start: pageable = {}");
        Pageable pageable1 = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        Page<Employee> list = repository.findAll(pageable1);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }


    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Employee> findAllByPlanAndCountry(String country, Integer plan) {
        return repository.findAllByPlanAndCountry(country, plan);
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = repository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
         /*if (employee.getIsDeleted()) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
        }*/
        return employee;
    }


    @Override
    public Employee updateById(Integer id, Employee employee) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public Employee pay(Integer id, String sum) {
        Integer plan = generatePlan(Integer.parseInt(sum));
        return repository.findById(id).map(entity->{
            entity.setPlan(plan);
            entity.setDateActivation(from(Instant.now()));
            return repository.save(entity);
        }).orElseThrow(()-> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = repository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);
        //employee.setIsDeleted(true);
        repository.delete(employee);
        //repository.save(employee);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();

    }

    @Override
    public List<Employee> getAllUsersCountry(String country) {
        var employee = repository.findAll();
        return employee.stream()
                .filter(employee1 -> country.equals(employee1.getCountry()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getAllUserPlan(Integer plan) {
        return repository.findAll().stream()
                .filter(employee -> plan.equals(employee.getPlan()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getFilters(String country, String name, String email) {
        return repository.findAll().stream()
                .filter(employee -> country.equals(employee.getCountry()))
                .filter(employee -> name.equals(employee.getName()))
                .filter(employee -> email.equals(employee.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getFiltersForPlanAndCountry(String country, Integer plan) {
        return repository.findAll().stream()
                .filter(employee -> country.equals(employee.getCountry()))
                .filter(employee -> plan.equals(employee.getPlan()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getListWithLimit(Integer limit) {
        return repository.findAll().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Integer generatePlan(Integer sum) {
        Integer plan_id = 0;
        if (sum <= 200) {
            plan_id = 3;
        } else if (sum <= 400) {
            plan_id = 2;
        } else if (sum <= 600) {
            plan_id = 1;
        } else {
            System.out.println("this sum its not validate: we have plans cost plan Lite(3) = 200" +
                    "plan LiteMore(2) = 400" +
                    "plan Enterprise(1) = 600");
        }
        return plan_id;
    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return repository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}