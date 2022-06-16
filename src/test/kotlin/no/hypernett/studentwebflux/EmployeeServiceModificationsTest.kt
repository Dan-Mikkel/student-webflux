package no.hypernett.studentwebflux

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier

@DataR2dbcTest
@Import(EmployeeService::class)
class EmployeeServiceModificationsTest {

    @Autowired
    lateinit var service: EmployeeService

    @Test
    fun `create returns the created Employee`() {
        val createdEmployee = service.create("Karsten Beate Larsen", 1000.0, "Oslo")

        StepVerifier
            .create(createdEmployee)
            .expectNextMatches { it.id != null }
            .verifyComplete()
    }

}