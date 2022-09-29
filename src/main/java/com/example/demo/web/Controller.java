package com.example.demo.web;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeePayDto;
import com.example.demo.dto.EmployeePlanDto;
import com.example.demo.dto.EmployeeReadDto;
import com.example.demo.service.Service;
import com.example.demo.util.config.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final Service service;

    private final UserMapper userMapper;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto employeedto) {
        Employee employee = userMapper.toEmployee(employeedto);
        return userMapper.toDto(service.create(employee));
    }

    //  Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        Employee employee = service.getById(id);
        return userMapper.toReadDto(employee);
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {

        return service.updateById(id, employee);
    }

    @PutMapping("/users/pay/{id}/{sum}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeePayDto payEmployee(@PathVariable("id") Integer id, @PathVariable("sum") String sum) {
        return userMapper.payDto(service.pay(id, sum));
    }

    @GetMapping("/users/plan/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeePlanDto planEmployee(@PathVariable("id") Integer id) {
        return userMapper.getplanDto(service.getById(id));
    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        service.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }

    @GetMapping(value = "/users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    public Employee findByEmail(@RequestParam(value = "email") String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/users/plan")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByCountryAndPlan(@RequestParam(value = "country") String country, @RequestParam(value = "plan") Integer plan) {
        return service.findAllByPlanAndCountry(country, plan);
    }

}
