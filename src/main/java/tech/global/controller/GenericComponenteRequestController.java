package tech.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tech.global.model.IGenericBaseModel;
import tech.global.service.IComponenteService;

public class GenericComponenteRequestController<S extends IComponenteService<E, T>, E extends IGenericBaseModel, T> {

    @Autowired
    protected S service;

}
