package com.polls.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answer_user")
public class AnswerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    @JsonBackReference
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "voted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date votedAt = new Date();

    public AnswerUser() {}

    public AnswerUser(Answer answer, User user) {
        this.answer = answer;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Answer getAnswer() { return answer; }
    public void setAnswer(Answer answer) { this.answer = answer; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Date getVotedAt() { return votedAt; }
    public void setVotedAt(Date votedAt) { this.votedAt = votedAt; }
}
