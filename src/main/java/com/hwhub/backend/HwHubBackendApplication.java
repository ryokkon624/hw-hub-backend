package com.hwhub.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({
  "com.hwhub.backend.infrastructure.mybatis.generated.mapper",
  "com.hwhub.backend.infrastructure.mybatis.custom.mapper"
})
public class HwHubBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(HwHubBackendApplication.class, args);
  }
}
