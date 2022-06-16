package no.hypernett.studentwebflux

import org.springframework.data.annotation.Id

data class Employee(
    @Id val id: Long,
    val name: String,
    val salary: Number,
    val region: String
)
