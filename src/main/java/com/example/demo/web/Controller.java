package com.example.demo.web;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final Service service;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        log.info("created employee");
        return service.create(employee);

    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable Integer id) {
        return service.getById(id);
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {

        return service.updateById(id, employee);
    }

    @PutMapping("/users/pay/{id}/{sum}")
    @ResponseStatus(HttpStatus.OK)
    public Employee payEmployee(@PathVariable("id") Integer id, @PathVariable("sum") String sum) {
        return service.pay(id, sum);
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
    public Employee findByEmail(@RequestParam(value = "email") String email){
        return service.findByEmail(email);
    }

    @GetMapping("/users/plan")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> findByCountryAndPlan(@RequestParam(value = "country") String country, @RequestParam(value = "plan") Integer plan) {
        var employee = service.findAllByPlanAndCountry(country, plan);
        return employee.stream().map(EmployeeDTO::toDto).collect(Collectors.toList());
    }

}
