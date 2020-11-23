package org.danekja.discussment.core.dao;

import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@TestPropertySource(properties = {
        "JDBC_DRIVER=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "JDBC_URL=jdbc:tc:mariadb:///test_db?TC_REUSABLE=true",
        "JDBC_USER=",
        "JDBC_PWD=",
})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RunWithMysqlContainer {
}
