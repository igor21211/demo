package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.Repository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ServiceTest {


    @Mock
    private Repository repository;

    @InjectMocks
    private ServiceBean service;


    @Test
    void create() {
        Employee employee = new Employee();
        employee.setName("Mark");

        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        Employee created = service.create(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(repository).save(employee);
    }

    @Test
    void findByEmail() {
        Employee employee = new Employee();
        employee.setName("Igor");
        employee.setEmail("shpura@serpstat.com");
        employee.setCountry("Ukraine");

        when(repository.findByEmail(employee.getEmail())).thenReturn(employee);

        Employee expected = service.findByEmail(employee.getEmail());

        assertThat(expected.getEmail()).isEqualTo(employee.getEmail());
        verify(repository).findByEmail(employee.getEmail());

    }

    @Test
    void getAll() {
        List<Employee> l = new ArrayList<>();
        Employee employee = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        employee.setName("igor");
        employee2.setName("Nadya");
        employee3.setName("Jeka");
        l.add(employee);
        l.add(employee2);
        l.add(employee3);


        when(repository.findAll()).thenReturn(l);

        List<Employee> expected = service.getAll();
        assertThat(expected).isSameAs(l);
        verify(repository).findAll();
    }

    @Test
    void findAllByPlanAndCountry() {
        List<Employee> actual = new ArrayList<>();
        Employee employee1 = Employee.builder()
                .name("Nadya")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(2)
                .build();
        Employee employee2 = Employee.builder()
                .name("Igor")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(2)
                .build();

        repository.save(employee1);
        repository.save(employee2);
        actual.add(employee1);
        actual.add(employee2);


        when(repository.findAllByPlanAndCountry("Ukraine",2)).thenReturn(actual);

        List<Employee> expected = service.findAllByPlanAndCountry("Ukraine",2);

        assertThat(expected).isSameAs(actual);
        verify(repository).findAllByPlanAndCountry(employee1.getCountry(),employee1.getPlan());
    }

    @Test
    void getById() {
        Employee actual = new Employee();
        actual.setId(101);

        when(repository.findById(actual.getId())).thenReturn(Optional.of(actual));

        Employee expected = service.getById(actual.getId());

        assertThat(expected).isSameAs(actual);
        verify(repository).findById(actual.getId());
    }

    @Test
    public void EmptyBodyAnswer() {
        Employee employee = new Employee();
        employee.setId(88);

        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee expected = service.getById(employee.getId());

        assertThat(expected).isSameAs(employee);
        verify(repository).findById(employee.getId());
    }

}