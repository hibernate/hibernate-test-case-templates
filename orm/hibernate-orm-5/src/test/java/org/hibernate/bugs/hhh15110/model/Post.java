package org.hibernate.bugs.hhh15110.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POST")
public class Post implements Serializable {
    private Long postId;
    private Date someDate;
    private List<Comment> comments = new ArrayList<>();

    @Id
    @Column(name = "POST_ID")
    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SOME_DATE")
    public Date getSomeDate() {
        return this.someDate;
    }

    public void setSomeDate(Date someDate) {
        this.someDate = someDate;
    }

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
