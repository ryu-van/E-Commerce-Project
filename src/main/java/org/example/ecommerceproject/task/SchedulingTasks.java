package org.example.ecommerceproject.task;


import lombok.extern.slf4j.Slf4j;
import org.example.ecommerceproject.model.Product;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
@Slf4j
public class SchedulingTasks {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    @Scheduled(cron = "*/10/ * * * * ?")
//    public void scheduledTask1() {
//        log.info("Executing scheduledTask1 at: {}", dateTimeFormatter.format(LocalDateTime.now()));
//    }


//    @Scheduled(fixedRate = 2000, initialDelay = 5000)
//    public void scheduledTask2() {
//        log.info("Executing scheduledTask2 at: {}", dateTimeFormatter.format(LocalDateTime.now()));
//    }
}
