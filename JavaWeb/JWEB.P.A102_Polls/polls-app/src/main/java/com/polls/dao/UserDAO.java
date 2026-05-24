package com.polls.dao;

import com.polls.model.User;
import com.polls.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAO {

    public User findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                "FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                "FROM User WHERE username = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }

    public void save(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public User saveOrGet(String ipAddress) {
        // Find or create an anonymous user keyed by IP
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> q = session.createQuery("FROM User WHERE ipAddress = :ip AND role = 'VOTER'", User.class);
            q.setParameter("ip", ipAddress);
            User user = q.uniqueResult();
            if (user == null) {
                Transaction tx = session.beginTransaction();
                user = new User();
                user.setUsername("voter_" + ipAddress.replace(".", "_") + "_" + System.currentTimeMillis());
                user.setPassword("N/A");
                user.setRole("VOTER");
                user.setIpAddress(ipAddress);
                session.save(user);
                tx.commit();
            }
            return user;
        }
    }
}
