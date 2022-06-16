package no.hypernett.studentwebflux

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.r2dbc.core.DatabaseClient


@SpringBootApplication
class StudentWebfluxApplication {
    @Bean
    fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
    }

    @Bean
    fun databaseClient(connectionFactory: ConnectionFactory): DatabaseClient {
        return DatabaseClient
            .builder()
            .connectionFactory(connectionFactory)
            .namedParameters(true)
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<StudentWebfluxApplication>(*args)
}
