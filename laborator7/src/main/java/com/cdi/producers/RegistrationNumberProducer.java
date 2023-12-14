package com.cdi.producers;

import jakarta.enterprise.inject.Produces;
import java.util.UUID;

public class RegistrationNumberProducer {

    @Produces
    public UUID produceRegistrationNumber() {
        return UUID.randomUUID();
    }
}
