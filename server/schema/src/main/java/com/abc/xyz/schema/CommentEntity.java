package com.abc.xyz.schema;


import javax.persistence.*;

@Entity
@Table(name="COMMENT")
public class CommentEntity extends BaseEntity{

    private String comment;
    private BlogEntity blog;
    private String refBlog;
    private UserEntity user;
    private String refUser;
    private Long commentTime;

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "refBlog", referencedColumnName = "id")
    public BlogEntity getBlog() {
        return blog;
    }

    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }

    @Basic
    @Column(name = "refBlog", insertable = false, updatable = false)
    public String getRefBlog() {
        return refBlog;
    }

    public void setRefBlog(String refBlog) {
        this.refBlog = refBlog;
    }

    @ManyToOne
    @JoinColumn(name = "refUser", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Basic
    @Column(name = "refUser", insertable = false, updatable = false)
    public String getRefUser() {
        return refUser;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    @Basic
    @Column(name="commentTime")
    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }
}
