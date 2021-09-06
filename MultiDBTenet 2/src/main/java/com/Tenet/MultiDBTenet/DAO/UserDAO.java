package com.Tenet.MultiDBTenet.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.Tenet.MultiDBTenet.model.User;

public class UserDAO {

    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public List<User> getAllUsers(String tenant_id) {
        Query theQuery = (Query) em.createQuery("from User");
        List<User> users = theQuery.getResultList();

        return users;
    }

    public User saveUser(User theUser, String tenant_id) {
        User dbUser = em.merge(theUser);
        theUser.setId(dbUser.getId());
        em.getTransaction().commit();
        return theUser;
    }

    public User findUserById(int theId, String tenant_id) {
        User theUser = em.find(User.class, theId);
        return theUser;
    }

    public void deleteUserById(int theId, String tenant_id) {
        Query theQuery = (Query)em.createQuery("delete from User where id=:userId");
        theQuery.setParameter("userId", theId);
        theQuery.executeUpdate();
        em.getTransaction().commit();
   
    }

    
}
