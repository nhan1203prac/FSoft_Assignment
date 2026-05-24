package com.polls.dao;

import com.polls.model.Answer;
import com.polls.model.AnswerUser;
import com.polls.model.User;
import com.polls.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AnswerDAO {

    public Answer findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Answer.class, id);
        }
    }

    public void saveVote(Answer answer, User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Answer managedAnswer = session.get(Answer.class, answer.getId());
            User managedUser = session.get(User.class, user.getId());
            AnswerUser answerUser = new AnswerUser(managedAnswer, managedUser);
            session.save(answerUser);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<AnswerUser> findVotesByAnswerId(Long answerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<AnswerUser> q = session.createQuery(
                "FROM AnswerUser WHERE answer.id = :aid", AnswerUser.class);
            q.setParameter("aid", answerId);
            return q.list();
        }
    }

    public long countByAnswerId(Long answerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> q = session.createQuery(
                "SELECT COUNT(au) FROM AnswerUser au WHERE au.answer.id = :aid", Long.class);
            q.setParameter("aid", answerId);
            return q.uniqueResult();
        }
    }
}
