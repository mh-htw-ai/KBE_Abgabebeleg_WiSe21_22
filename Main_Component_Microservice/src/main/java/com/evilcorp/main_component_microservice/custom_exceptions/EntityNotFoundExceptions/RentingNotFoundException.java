package com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions;

import java.util.UUID;

public class RentingNotFoundException extends EntityNotFoundException{
    public RentingNotFoundException(UUID rentingId) {super("Renting with id: " + rentingId.toString() + " could not be found!");}
}
