package com.market.purchase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class JpaAuditingConfig {
    //TODO when spring security is active, use the bellow provider on the EnableMongoAuditing annotation to persist the user who edited / saved the document
//    @Bean
//    public AuditorAware<String> auditorProvider() {
//
//        /*
//          if you are using spring security, you can get the currently logged username with following code segment.
//          SecurityContextHolder.getContext().getAuthentication().getName()
//         */
//        return () -> Optional.ofNullable("chathuranga");
//    }
}
