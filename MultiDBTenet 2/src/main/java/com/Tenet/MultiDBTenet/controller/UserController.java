package com.Tenet.MultiDBTenet.controller;

import java.util.List;

import javax.persistence.EntityManager;

import com.Tenet.MultiDBTenet.DAO.UserDAO;
import com.Tenet.MultiDBTenet.config.DBConfig;
import com.Tenet.MultiDBTenet.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    DBConfig dbConfig = new DBConfig();

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUser(@RequestHeader (value = "tenant") String tenant_id){
        
        EntityManager em = dbConfig.getEntity(tenant_id);

        UserDAO userDAO = new UserDAO(em);

        return new ResponseEntity<List<User>>(userDAO.getAllUsers(tenant_id), HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User addUser(@RequestBody User theUser, @RequestHeader (value = "tenant") String tenant_id) {
        EntityManager em = dbConfig.getEntity(tenant_id);

        UserDAO userDAO = new UserDAO(em);
        return(userDAO.saveUser(theUser, tenant_id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public User updatUser(@RequestBody User theUser, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        UserDAO userDAO = new UserDAO(em);

        User user = userDAO.findUserById(theUser.getId(), tenant_id);

        if(user == null) {
            throw new RuntimeException("User to update doesn't exist");
        }

        return (userDAO.saveUser(theUser, tenant_id));
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable int userId, @RequestHeader (value = "tenant") String tenant_id){
        EntityManager em = dbConfig.getEntity(tenant_id);
        UserDAO userDAO = new UserDAO(em);

        User tempUser = userDAO.findUserById(userId, tenant_id);

        if(tempUser == null){
            throw new RuntimeException("User Id not found");
        }
        userDAO.deleteUserById(userId, tenant_id);

        return "Deleted user id " + userId;
    }
}
