package com.polls.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "is_multiple")
    private boolean isMultiple = false;

    @Column(name = "is_required")
    private boolean isRequired = false;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    public Question() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public boolean isMultiple() { return isMultiple; }
    public void setMultiple(boolean multiple) { isMultiple = multiple; }

    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { isRequired = required; }

    public Set<QuestionAnswer> getQuestionAnswers() { return questionAnswers; }
    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) { this.questionAnswers = questionAnswers; }
}
