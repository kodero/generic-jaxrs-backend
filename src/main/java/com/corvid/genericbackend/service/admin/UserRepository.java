package com.corvid.genericbackend.service.admin;

import com.corvid.bes.service.GService;
import com.corvid.genericbackend.model.admin.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;

@Stateless
public class UserRepository implements Serializable {

    @Inject
    private GService gService;

    public UserRepository() {

    }

    public User findUser(String userId) {
        return gService.find(userId, User.class);
    }

    public User updateAfterSuccessfulLogin(User user) {
        Query q = gService.createQuery("UPDATE User u SET u.lastLoggedInAt = :lastLoggedInAt," +
                "u.lastLoginAt =:lastLoginAt," +
                "u.currentLoginAt =:currentLoginAt," +
                "u.lastLoginIp =:lastLoginIp," +
                "u.currentLoginIp =:currentLoginIp," +
                "u.loginCount =:loginCount, " +
                "u.persistenceToken =:persistenceToken " +
                "WHERE u.id =:id");
        q.setParameter("lastLoggedInAt", user.getLastLoggedInAt());
        q.setParameter("lastLoginAt", user.getLastLoginAt());
        q.setParameter("currentLoginAt", user.getCurrentLoginAt());
        q.setParameter("lastLoginIp", user.getLastLoginIp());
        q.setParameter("currentLoginIp", user.getCurrentLoginIp());
        q.setParameter("loginCount", user.getLoginCount());
        q.setParameter("persistenceToken", user.getPersistenceToken());
        q.setParameter("id", user.getId());
        int updated = q.executeUpdate();
        return findUser(user.getId());
    }

    public User updateUser(User user) {
        //em.getEntityGraph()
        User res = gService.persist(user);
        return res;
    }

    /**
     * @param username      the fellows email
     * @param onlyActivated
     * @param caseSensitive
     * @return
     */
    public User findUser(String username, boolean onlyActivated, boolean caseSensitive) {
        StringBuilder query = new StringBuilder("select u from User u where");
        if (caseSensitive)
            query.append(" u.email = :username");
        else
            query.append(" lower(u.email) = :username");
        if (onlyActivated) query.append(" and u.active = true");

        try {
            return (User) gService
                    .createQuery(query.toString())
                    .setParameter("username", caseSensitive ? username : username.toLowerCase())
                    .getSingleResult();
        } catch (EntityNotFoundException | NoResultException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User findUserByEmail(String email) {
        StringBuilder query = new StringBuilder("select u from User u where u.email = :email");

        try {
            return (User) gService
                    .createQuery(query.toString())
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (EntityNotFoundException | NoResultException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User findUserByToken(String token) {
        String query = "select u from User u LEFT JOIN FETCH u.userProfile p where u.persistenceToken = :token";
        return (User) gService
                .createQuery(query.toString())
                .setParameter("token", token)
                .getSingleResult();
    }

    /**
     * also checks if the activation code has expired
     *
     * @param activationCode
     * @return
     */
    public User findUserWithActivationCode(String activationCode) {
        StringBuilder query = new StringBuilder("select u from User u where u.activationCode = :activationCode " +
                "and u.emailVerificationExpiryAt >= :currentTimestamp ");
        try {
            return (User) gService
                    .createQuery(query.toString())
                    .setParameter("activationCode", activationCode)
                    .setParameter("currentTimestamp", new Date())
                    .getSingleResult();
        } catch (EntityNotFoundException | NoResultException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
