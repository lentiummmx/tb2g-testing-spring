package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.WordProducer;
import org.springframework.stereotype.Component;

@Component
@Profile({"external-props", "laurel-properties"})
@Primary
public class PropertiesWordProducerImpl implements WordProducer {

    private String word;

    @Value("${say.word}")
    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String getWord() {
        return word;
    }
}
