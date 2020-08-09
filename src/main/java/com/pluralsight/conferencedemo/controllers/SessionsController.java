package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions") //class request mapping
public class SessionsController {
    @Autowired //spring will autowire when sessioncontroller is built, and create an instance of sessionRepository and put it onto our class
    private SessionRepository sessionRepository;

    //creating list endpoint. Return all of sessions when called
    // telling http to use get keyword for this endpoint
    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll(); //will query all sessions in database and return them as list of Session object
    }

    //we are using the http verb get when we call method
    @GetMapping
    @RequestMapping("{id}") //add additional id to url. Specific session and return id to that
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    //requiring the http verb post
    //did not put RequestMapping because posting to the base part of class (/api/v1/sessions) endpoint
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) // by default spring returns response:200. Now created makes response:201
    //spring mvc taking in all attributes in json payload, marshalling/put in to session object
    public Session create(@RequestBody Session session){
        return sessionRepository.saveAndFlush(session); //jpa data repository method to save to database
    }

    //there is no @DeleteMapping. Spring only provide @GetMapping,@PostMapping. If use other verb, must specify in RequestMapping attributes
    //delete that particular id so must pass in id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //Also need to check for children records before deleting
        //will only delete session repository without any children
        sessionRepository.deleteById(id);
    }

    //requiring id on url same as delete and get endpoint
    //patch (allow some/a portion of attributes to updated) and put (replace all attributes on the record you're updating )
    //takes all attributes on request body for that session and replace all for database record
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //because this is a PUT, we expect all attributes to be passed in (if never pass all attributes, then those will be null). if only need to update certain attributes of JPA entity, should use PATCH
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload

        //find existing session. Session Repository to find record by id, which is coming off of the url parameter
        Session existingSession = sessionRepository.getOne(id);
        //BeanUtils object takes existing session and copies the incoming session data onto it.
        //Third param is to ignore copy of session_id attribute as it is primary key and we dont want to replace it. will copy null to database if don't ignore
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
