package no.hypernett.studentwebflux

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

@Component
class SeedDb(private val client: DatabaseClient, private val repository: EmployeeRepository) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        client.sql(
            "create table employee (" +
                    "id int auto_increment primary key, " +
                    "name varchar, " +
                    "salary number, " +
                    "region varchar);"
        )
            .then()
            .subscribe { log.info("Created") }
        val emps = listOf(
            Employee(null, "James", 25000, "London"),
            Employee(null, "James", 21000, "London"),
            Employee(null, "Marie", 22000, "Edinburgh"),
            Employee(null, "Peter", 23000, "Belfast"),
            Employee(null, "Sally", 24000, "Cardiff"),
            Employee(null, "Peter", 51000, "London"),
            Employee(null, "Berty", 52000, "Edinburgh"),
            Employee(null, "Milly", 53000, "Belfast"),
            Employee(null, "Jayne", 54000, "Cardiff"),
            Employee(null, "Wally", 91000, "London"),
            Employee(null, "Emily", 92000, "Edinburgh"),
            Employee(null, "Tommy", 93000, "Belfast"),
            Employee(null, "Colin", 94000, "Cardiff"),
            Employee(null, "Sarah", 121000, "London"),
            Employee(null, "Darel", 122000, "Edinburgh"),
            Employee(null, "Benji", 123000, "Belfast"),
            Employee(null, "Carys", 124000, "Cardiff")
        )
        repository.deleteAll()
            .thenMany(Flux.fromIterable(emps))
            .flatMap { emp -> repository.save(emp) }
            .thenMany(repository.findAll())
            .subscribe { emp -> log.info("Employee successfully inserted: $emp") }
    }
}