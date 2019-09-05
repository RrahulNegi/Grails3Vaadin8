package hello.world

import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

import java.time.LocalDate
@CompileStatic
@Service(Customer)
interface CustomerService {

    @Where({
        if(name) {
            firstName =~ "%${name}%" || lastName =~ "%${name}%"
        }
    })
    List<Customer> findAllByNames(String name)
    List<Customer> findAll()
    Customer save(Customer contact)

    Customer save(String firstName, String lastName, String phone, String email, Customer.CustomerStatus status, LocalDate birthDate)
}
