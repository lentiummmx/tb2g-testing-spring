package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.impl.LaurelWordProducerImpl;
import org.springframework.samples.petclinic.sfg.impl.YannyWordProducerImpl;

@Profile("base-test")
@Configuration
public class YannyConfig {

    @Bean
    YannyWordProducerImpl yannyWordProducerImpl() {
        return new YannyWordProducerImpl();
    }
}
