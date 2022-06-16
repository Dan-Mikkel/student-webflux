package no.hypernett.studentwebflux

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class EmployeeService(private val repo: EmployeeRepository) {
    fun getNum(): Mono<Long> {
        return repo.count()
    }

    fun getAll(): Flux<Employee> {
        return repo.findAll()
    }

    fun getById(id: Long): Mono<Employee> {
        return repo.findById(id)
    }

    fun create(name: String, salary: Double, region: String): Mono<Employee> {
        return repo.save(Employee(null, name, salary, region))
    }

    fun update(id: Long, name: String, salary: Double, region: String): Mono<Employee> {
        return repo.findById(id)
            .map { e -> Employee(e.id, name, salary, region) }
            .flatMap { t -> repo.save(t).thenReturn(t) }
    }

    fun delete(id: Long): Mono<Employee> {
        return repo.findById(id)
            .flatMap { e -> repo.deleteById(e.id!!).thenReturn(e) }
    }
}