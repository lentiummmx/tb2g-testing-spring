package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.impl.LaurelWordProducerImpl;

@Profile("base-test")
@Configuration
public class LaurelConfig {

    @Bean
    LaurelWordProducerImpl laurelWordProducerImpl() {
        return new LaurelWordProducerImpl();
    }
}
