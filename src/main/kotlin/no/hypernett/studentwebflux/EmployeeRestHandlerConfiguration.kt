package no.hypernett.studentwebflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
@Profile("handler-style-endpoint")
class EmployeeRestHandlerConfiguration {

    @Bean
    fun routes(handler: EmployeeRestHandler) = router {
        GET("/employees", handler::getAll)
        GET("/employees/{id}", handler::getById)
        POST("/employees", handler::create)
        PUT("/employees/{id}", handler::update)
        DELETE("/employees/{id}", handler::delete)
    }

}