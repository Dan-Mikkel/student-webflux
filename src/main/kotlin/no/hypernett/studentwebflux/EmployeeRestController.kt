package no.hypernett.studentwebflux

import mu.KotlinLogging
import org.reactivestreams.Publisher
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping(value = ["/employees"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Profile("controller-style-endpoint")
class EmployeeRestController(private val employeeService: EmployeeService) {

    @GetMapping
    fun getAllEmployees(): Publisher<Employee> = employeeService.getAll().also { log.info("controller-style-endpoint, getAll()") }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: EmployeeId): Publisher<Employee> = employeeService.getById(id).also { log.info("controller-style-endpoint, getById()") }
    
    @PostMapping
    fun createEmployee(@RequestBody employee: Employee): Publisher<ResponseEntity<Employee>> = this.employeeService
        .create(employee.name, employee.salary, employee.region)
        .also { log.info("controller-style-endpoint, create()") }
        .map {
            ResponseEntity.created(URI.create("/employees/" + it.id))
                .contentType(MediaType.APPLICATION_JSON)
                .build()
        }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: EmployeeId, @RequestBody employee: Employee): Publisher<ResponseEntity<Employee>> = this.employeeService
        .update(id, employee.name, employee.salary, employee.region)
        .also { log.info("controller-style-endpoint, update()") }
        .map {
            ResponseEntity.created(URI.create("/employees/" + it.id))
                .contentType(MediaType.APPLICATION_JSON)
                .build()
        }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: EmployeeId): Publisher<Employee> = this.employeeService.delete(id).also { log.info("controller-style-endpoint, delete()") }
}