package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.samples.petclinic.sfg.WordProducer;
import org.springframework.stereotype.Component;

@Component
public class LaurelWordProducerImpl implements WordProducer {
    @Override
    public String getWord() {
        return "Laurel";
    }
}
