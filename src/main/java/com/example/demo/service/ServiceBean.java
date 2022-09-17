package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.Repository;
import com.example.demo.util.ResourceNotFoundException;
import com.example.demo.util.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Integer generatePlan(Integer sum){
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
}