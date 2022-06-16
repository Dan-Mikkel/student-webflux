package no.hypernett.studentwebflux

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EmployeeRepository: ReactiveCrudRepository<Employee, EmployeeId> {
    fun countByRegion(region: String): Mono<Int>
    fun findByRegion(region: String): Flux<Employee>
}