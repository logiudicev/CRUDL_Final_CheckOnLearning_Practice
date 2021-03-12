package Mil.SoftwareFactory.CRUDL.Controller;
import Mil.SoftwareFactory.CRUDL.Model.*;

import Mil.SoftwareFactory.CRUDL.Model.EmployeeEntity;
import Mil.SoftwareFactory.CRUDL.DAO.EmployeeRepository;
import org.springframework.cache.support.NullValue;
import org.springframework.web.bind.annotation.*;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    //Create
    @PostMapping("/employees")
    public EmployeeEntity createEmployee (@RequestBody EmployeeEntity createEmployee){
        return this.repository.save(createEmployee);
    }
    //Read
    @GetMapping("/employees/{id}")
    public Optional<EmployeeEntity> readEmployeeById(@PathVariable long id){
        return this.repository.findById(id);
    }
    //Update
    @PatchMapping("/employees/{id}")
    public EmployeeEntity updateEmployeeEntity (@RequestBody EmployeeEntity employeeEntity,
                                                @PathVariable Long id){
        if (this.repository.existsById(id)){
            EmployeeEntity updateEmployeeEntity = this.repository.findById(id).get();
            updateEmployeeEntity.setName(employeeEntity.getName());
            this.repository.save(updateEmployeeEntity);
        } else {
            this.repository.save(employeeEntity);
        }
        return employeeEntity;
    }
    //Delete
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee (@PathVariable Long id){
        this.repository.deleteById(id);
    }


    //List
    @GetMapping("/employees")
    public Iterable<EmployeeEntity> allEmployeesById(){
        return this.repository.findAll();
    }

}
