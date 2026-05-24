package com.polls.dao;

import com.polls.model.Poll;
import com.polls.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PollDAO {

    public List<Poll> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Poll ORDER BY createdAt DESC", Poll.class).list();
        }
    }

    public List<Poll> findByStatus(String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Poll> query = session.createQuery("FROM Poll WHERE status = :status ORDER BY createdAt DESC", Poll.class);
            query.setParameter("status", status);
            return query.list();
        }
    }

    public Poll findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Poll.class, id);
        }
    }

    public Poll findByIdWithDetails(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Poll poll = session.createQuery(
                            "SELECT p FROM Poll p " +
                                    "LEFT JOIN FETCH p.pollQuestions pq " +
                                    "LEFT JOIN FETCH pq.question q " +
                                    "LEFT JOIN FETCH q.questionAnswers qa " +
                                    "LEFT JOIN FETCH qa.answer a " +
                                    "LEFT JOIN FETCH a.answerUsers " +
                                    "WHERE p.id = :id", Poll.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return poll;
        }
    }

    public List<Poll> findAllWithDetails() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT DISTINCT p FROM Poll p " +
                "LEFT JOIN FETCH p.pollQuestions pq " +
                "LEFT JOIN FETCH pq.question q " +
                "LEFT JOIN FETCH q.questionAnswers qa " +
                "LEFT JOIN FETCH qa.answer " +
                "WHERE p.status = 'ACTIVE' " +
                "ORDER BY p.createdAt DESC", Poll.class)
                .list();
        }
    }

    public void save(Poll poll) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(poll);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void update(Poll poll) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(poll);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public boolean delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Poll poll = session.get(Poll.class, id);
            if (poll != null) {
                session.delete(poll);
                tx.commit();
                return true;
            }
            tx.commit();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void updateStatus(Long id, String status) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Poll poll = session.get(Poll.class, id);
            if (poll != null) {
                poll.setStatus(status);
                session.merge(poll);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
