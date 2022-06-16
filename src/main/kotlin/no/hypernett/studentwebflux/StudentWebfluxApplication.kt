package no.hypernett.studentwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudentWebfluxApplication

fun main(args: Array<String>) {
    runApplication<StudentWebfluxApplication>(*args)
}
