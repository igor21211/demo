package com.example.demo.web;

import com.example.demo.domain.Employee;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeePayDto;
import com.example.demo.dto.EmployeePlanDto;
import com.example.demo.service.Service;
import com.example.demo.util.config.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class Controller {

    private final Service service;

    private final UserMapper userMapper;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description =
            "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto employeedto) {
        Employee employee = userMapper.toEmployee(employeedto);
        return userMapper.toDto(service.create(employee));
    }

    //  Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<Employee> getAllUsers() {
        return service.getAll();
        // return employee.stream().map(EmployeeDto::map).collect(Collectors.toList());
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned employee by his id.", description =
            "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public Employee getEmployeeById(@PathVariable Integer id) {
        return service.getById(id);
        // return userMapper.toReadDto(employee);
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint find employee by his id and update information for this employee as you wish", description =
            "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {

        return service.updateById(id, employee);
    }

    @PutMapping("/users/pay/{id}/{sum}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "In this endpoint employee can buy subscribe for app and in answer he can see data activation.", description =
            "600 - 1 plan, 400 - 2 plan , 200 - 3 plan", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeePayDto payEmployee(@PathVariable("id") Integer id, @PathVariable("sum") String sum) {
        return userMapper.payDto(service.pay(id, sum));
    }

    @GetMapping("/users/plan/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find by id employee and show plan and date activation Employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeePlanDto planEmployee(@PathVariable("id") Integer id) {
        return userMapper.getplanDto(service.getById(id));
    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public void removeEmployeeById(@PathVariable Integer id) {
        service.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all users", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public void removeAllUsers() {
        service.removeAll();
    }

    @GetMapping(value = "/users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return Employee who have same email how in request", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public Employee findByEmail(@RequestParam(value = "email") String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/users/plan")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return Employee who have same email and country how in request", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<Employee> findByCountryAndPlan(@RequestParam(value = "country") String country, @RequestParam(value = "plan") Integer plan) {
        return service.findAllByPlanAndCountry(country, plan);
    }

    @GetMapping("/users/info")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(defaultValue = "") List<String> sortList,
                                  @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder
    ) {

        return service.getAllWithPagination(page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        return service.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
    }

    @GetMapping(value = "/users/stream/api/country", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<EmployeeDto> getallUsersWithCountry(@RequestParam(value = "country") String country) {
        var employee = service.getAllUsersCountry(country);
        return employee.stream().map(EmployeeDto::map).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/stream/api/plan", params = {"plan"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee plan ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<Employee> getAllUsersForPlan(@RequestParam(value = "plan") Integer plan) {
        return service.getAllUserPlan(plan);
    }

    @GetMapping(value = "/users/stream/api/filters", params = {"country", "name", "email"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee plan ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<EmployeeDto> getAllUsersFilters(@RequestParam(value = "country") String country,
                                                @RequestParam(value = "name") String name,
                                                @RequestParam(value = "email") String email) {
        return service.getFilters(country, name, email)
                .stream().map(EmployeeDto::map)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/users/stream/api/plan-country", params = {"country", "plan"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee plan and country ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<Employee> getAllUsersFiltersForPlanAndCountry(@RequestParam(value = "country") String country,
                                                              @RequestParam(value = "plan") Integer plan) {
        return service.findAllByPlanAndCountry(country, plan);
    }

    @GetMapping(value = "/users/stream/api/limit", params = {"limit"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return all Employee plan and country ", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Information was get successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public List<EmployeeDto> getAllUsersFiltersForPlanAndCountry(@RequestParam(value = "limit") Integer limit) {
        return service.getListWithLimit(limit).stream()
                .map(EmployeeDto::map)
                .collect(Collectors.toList());
    }
}
