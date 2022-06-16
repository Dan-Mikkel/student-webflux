package no.hypernett.studentwebflux

import org.springframework.data.annotation.Id

typealias EmployeeId = Long

data class Employee(
    @Id val id: EmployeeId,
    val name: String,
    val salary: Number,
    val region: String
)
