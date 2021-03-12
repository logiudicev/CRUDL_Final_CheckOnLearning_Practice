package Mil.SoftwareFactory.CRUDL.DAO;

import Mil.SoftwareFactory.CRUDL.Model.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
}
