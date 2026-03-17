package com.hwhub.backend.integration

import tools.jackson.databind.ObjectMapper
import com.hwhub.backend.security.JwtProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.MySQLContainer
import software.amazon.awssdk.services.s3.S3Client
import spock.lang.Specification
import spock.lang.Tag
import spock.lang.Shared

// Spring Boot 3.0 + Spock + @SpringBootTest で @Autowired が null になる既知の問題の回避策
@SpringBootTest(
    classes = [com.hwhub.backend.HwHubBackendApplication],
    webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("integration")
abstract class IntegrationTestBase extends Specification {

    @Shared
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("hwhub_db")
            .withUsername("hwhub")
            .withPassword("hwhub")
            .tap { container ->
                container.start()
                // Springコンテキスト起動前にシステムプロパティで注入
                System.setProperty("spring.datasource.url", container.jdbcUrl)
                System.setProperty("spring.datasource.username", container.username)
                System.setProperty("spring.datasource.password", container.password)
            }
            
    // S3Client をモックに差し替えて実際の AWS 接続を防ぐ
    @MockitoBean
    S3Client s3Client

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    protected ObjectMapper objectMapper

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Autowired
    protected JwtProvider jwtProvider

    protected String tokenFor(Long userId) {
        return jwtProvider.generateToken(userId, "test-user")
    }
}
