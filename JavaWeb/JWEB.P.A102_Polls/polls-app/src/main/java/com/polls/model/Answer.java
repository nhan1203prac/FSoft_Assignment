package com.polls.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnswerUser> answerUsers = new ArrayList<>();

    public Answer() {}

    public Answer(String content) {
        this.content = content;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<AnswerUser> getAnswerUsers() { return answerUsers; }


    public void setAnswerUsers(List<AnswerUser> answerUsers) { this.answerUsers = answerUsers; }

    public int getVoteCount() { return answerUsers != null ? answerUsers.size() : 0; }
}
