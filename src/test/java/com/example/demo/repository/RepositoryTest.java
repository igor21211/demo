package com.example.demo.repository;

import com.example.demo.domain.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepositoryTest {

    @Autowired
    private Repository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest() {

        Employee employee = Employee.builder().name("Mark").country("England").build();

        repository.save(employee);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void saveEmployeeTestNegativ() {

        Employee employee = Employee.builder().name("Mark").country("England").build();

        repository.save(employee);

        Assertions.assertThat(employee.getId()).isLessThan(3);
    }

    @Test
    @Order(3)
    public void getEmployeeTest() {

        Employee employee = repository.findById(1).orElseThrow();;

        Assertions.assertThat(employee.getId()).isEqualTo(1);

    }

    @Test
    @Order(4)
    public void getEmployeeTestNegativ() {

        Employee employee = repository.findById(1).orElseThrow();;

        Assertions.assertThat(employee.getId()).isLessThan(2);

    }

    @Test
    @Order(5)
    public void getFindByEmailTest(){
        Employee employee2 = Employee.builder()
                .name("Nadya")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .build();
        repository.save(employee2);
        Employee employee = repository.findByEmail("nadin@serpstat.com");

        Assertions.assertThat(employee.getEmail()).isEqualTo("nadin@serpstat.com");
        Assertions.assertThat(employee.getName()).isEqualTo("Nadya");
        Assertions.assertThat(employee.getCountry()).isEqualTo("Ukraine");
    }

    @Test
    @Order(6)
    public void getFindByEmailTestNegativ(){
        Employee employee2 = Employee.builder()
                .name("Nadya")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .build();
        repository.save(employee2);
        Employee employee = repository.findByEmail("nadin@serpstat.com");

        Assertions.assertThat(employee.getEmail()).isNotEqualTo("igor@serpstat.com");
        Assertions.assertThat(employee.getName()).isNotEqualTo("Igor");
        Assertions.assertThat(employee.getCountry()).isNotEqualTo("Poland");
    }

    @Test
    @Order(7)
    public void getListOfEmployeeTest() {

        List<Employee> employeesList = repository.findAll();

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);

    }

    @Test
    @Order(8)
    public void getListOfEmployeeTestNegative() {

        List<Employee> employeesList = repository.findAll();

        Assertions.assertThat(employeesList.size()).isLessThan(3);

    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void updateEmployeeTest() {

        Employee employee = repository.findById(1).get();

        employee.setName("Igor");
        Employee employeeUpdated = repository.save(employee);

        Assertions.assertThat(employeeUpdated.getName()).isEqualTo("Igor");

    }

    @Test
    @Order(10)
    @Rollback(value = false)
    public void updateEmployeeTestNegative() {

        Employee employee = repository.findById(1).get();

        employee.setName("Igor");
        Employee employeeUpdated = repository.save(employee);

        Assertions.assertThat(employeeUpdated.getName()).isNotEqualTo("Nadya");

    }

    @Test
    @Order(11)
    @Rollback(value = false)
    public void deleteEmployeeTest() {

        Employee employee = repository.findById(1).get();

        repository.delete(employee);


        Employee employee1 = null;

        Optional<Employee> optionalAuthor = Optional.ofNullable(repository.findByName("Igor"));

        if (optionalAuthor.isPresent()) {
            employee1 = optionalAuthor.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

    @Test
    @Order(12)
    @Rollback(value = false)
    public void findAllByPlanAndCountryTest(){
        Employee employee2 = Employee.builder()
                .name("Nadya")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(3)
                .build();

        Employee employee3 = Employee.builder()
                .name("Igor")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(3)
                .build();

        Employee employee4 = Employee.builder()
                .name("Igor")
                .country("Italy")
                .email("nadin@serpstat.com")
                .plan(2)
                .build();
        repository.save(employee2);
        repository.save(employee3);
        repository.save(employee4);
        List<Employee> employee =  repository.findAllByPlanAndCountry("Ukraine",3);

        employee.forEach(employee1 -> Assertions.assertThat(employee1.getPlan()).isEqualTo(3));
        employee.forEach(employee1 -> Assertions.assertThat(employee1.getCountry()).isEqualTo("Ukraine"));
        Assertions.assertThat(employee.size()).isGreaterThan(0);

    }

    @Test
    @Order(13)
    @Rollback(value = false)
    public void findAllByPlanAndCountryTestNegative(){
        Employee employee2 = Employee.builder()
                .name("Nadya")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(3)
                .build();

        Employee employee3 = Employee.builder()
                .name("Igor")
                .country("Ukraine")
                .email("nadin@serpstat.com")
                .plan(3)
                .build();
        repository.save(employee2);
        List<Employee> employee =  repository.findAllByPlanAndCountry("Ukraine",3);

        employee.forEach(employee1 -> Assertions.assertThat(employee1.getPlan()).isNotEqualTo(1));
        employee.forEach(employee1 -> Assertions.assertThat(employee1.getCountry()).isNotEqualTo("Poland"));

    }

}