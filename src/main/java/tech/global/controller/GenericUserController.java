package tech.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tech.global.model.IGenericBaseModel;
import tech.global.service.IUsersService;

public class GenericUserController<S extends IUsersService<E, T>, E extends IGenericBaseModel, T> {

    @Autowired
    protected S service;

}
