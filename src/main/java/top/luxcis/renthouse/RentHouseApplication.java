package top.luxcis.renthouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.luxcis.renthouse.mapper")
public class RentHouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentHouseApplication.class, args);
    }
}
