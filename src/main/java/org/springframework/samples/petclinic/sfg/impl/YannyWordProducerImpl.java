package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.WordProducer;
import org.springframework.stereotype.Component;

@Profile("yanny")
@Primary
@Component
public class YannyWordProducerImpl implements WordProducer {
    @Override
    public String getWord() {
        return "Yanny";
    }
}
