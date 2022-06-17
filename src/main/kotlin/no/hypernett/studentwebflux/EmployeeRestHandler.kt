package no.hypernett.studentwebflux

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToFlux
import reactor.core.publisher.Mono
import java.net.URI

typealias SingleResponse = Mono<ServerResponse>

private val log = KotlinLogging.logger {}

@Component
@Profile("handler-style-endpoint")
class EmployeeRestHandler(private val service: EmployeeService) {

    fun getAll(req: ServerRequest): SingleResponse = ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getAll(), Employee::class.java)
            .also { log.info("handler-style-endpoint, getAll()") }

    fun getById(req: ServerRequest): SingleResponse {
        log.info("handler-style-endpoint, getById()")
        val id: EmployeeId = req.pathVariable("id").toLong()
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getById(id), Employee::class.java)
    }

    fun create(req: ServerRequest): SingleResponse {
        log.info("handler-style-endpoint, create()")

        val created = req.bodyToFlux(Employee::class.java)
            .flatMap { service.create(it.name, it.salary, it.region) }

        return Mono.from(created)
            .flatMap {
                ServerResponse.created(URI.create("/employees/$it.id"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .build()
            }
    }

    fun update(req: ServerRequest): SingleResponse {
        log.info("handler-style-endpoint, update()")

        val id: EmployeeId = req.pathVariable("id").toLong()

        val updated = req.bodyToFlux<Employee>()
            .flatMap { service.update(id, it.name, it.salary, it.region) }

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(updated, Employee::class.java)
    }

    fun delete(req: ServerRequest): SingleResponse {
        log.info("handler-style-endpoint, delete()")
        val id: EmployeeId = req.pathVariable("id").toLong()

        val deleted = service.delete(id)

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(deleted, Employee::class.java)
    }

}