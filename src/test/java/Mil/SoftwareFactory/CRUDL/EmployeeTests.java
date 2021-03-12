package Mil.SoftwareFactory.CRUDL;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Mil.SoftwareFactory.CRUDL.DAO.EmployeeRepository;
import Mil.SoftwareFactory.CRUDL.Model.EmployeeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.is;
import java.sql.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import javax.transaction.Transactional;

//import static java.util.Collections.get;

//add repo here
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    EmployeeRepository repository;




    @Test //Test a get request
    @Transactional
    @Rollback
    public void testGet() throws Exception {
        //setup and declare variables
        //add information I want to retrieve
        EmployeeEntity newEmployee = new EmployeeEntity();
        newEmployee.setName("George Castanza"); //method needs to be a setter in employeeEntity
        newEmployee.setPublishDate(Date.valueOf("2021-03-11"));


        //Save it
        this.repository.save(newEmployee);

        //build my get request with mvc mock builder
        MockHttpServletRequestBuilder getRequest = get("/employees");
                //if it's a post
                //MockHttpServletRequestBuilder getRequest = post("/employees")
                    //.contentType(MediaType.APPLICATION_JSON)
                    //.content("{\"title\" :

        //execution to do what you want to test
        //Send my request with perform ; you could also use the exact path "/employees"
        this.mvc.perform(getRequest)
        //assertions (does the behavior match what is expected
        //andExpect (status code 200) and (have a list of information)
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].name", is("George Castanza")))
                .andExpect(jsonPath("$[0].publishDate", is("2021-03-11")));
    }

    @Test
    @Transactional
    @Rollback
    public void postTest() throws Exception {
        //setup a copy of our class as a new object
        EmployeeEntity newEmployee = new EmployeeEntity();
        //fill with info, add objnect to request body
        newEmployee.setPublishDate(Date.valueOf("2021-03-11"));
        newEmployee.setName("Cosmo Kramer");
        // set content type
        //add the obj to req body

        //Turn to string
        ObjectMapper jsonCreator = new ObjectMapper();
        String json = jsonCreator.writeValueAsString(newEmployee);


        //create the request
        MockHttpServletRequestBuilder postRequest = post ("/employees")

        //set content type
        .contentType(MediaType.APPLICATION_JSON)

        //add to request body
        .content("{\"name\":\"Cosmo Kramer\". \"publishDate\":\"2021-03-11\"}")
        .content(json);
        //execution

        //assertion
        this.mvc.perform(postRequest)

                //assertions
        .andExpect(status().isOk())

        .andExpect(jsonPath("$.name", is("Cosmo Kramer")))
        .andExpect(jsonPath("$.publishDate", is("2021-03-11")))
        .andExpect(jsonPath("$.id").isNumber());
    }

}
