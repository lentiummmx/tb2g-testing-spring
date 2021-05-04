package org.springframework.samples.petclinic.sfg.junit4;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.impl.LaurelWordProducerImpl;

import static org.junit.Assert.*;

public class HearingInterpreterTest {

    HearingInterpreter hearingInterpreter;

    @Before
    public void setUp() throws Exception {
        hearingInterpreter = new HearingInterpreter(new LaurelWordProducerImpl());
    }

    @Test
    public void whatIHeard() {
        assertEquals("Laurel", hearingInterpreter.whatIHeard());
    }
}