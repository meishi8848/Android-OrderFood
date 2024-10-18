package com.example.orderfood.Bean;

public class CommentBean {
    private String commentId;
    private String commentUserId;
    private String commentBossId;
    private String commentContent;
    private String commentTime;
    private String commentScore;
    private String commentImg;

    public CommentBean() {
    }

    public CommentBean(String commentId, String commentUserId, String commentBossId, String commentContent, String commentTime, String commentScore, String commentImg) {
        this.commentId = commentId;
        this.commentUserId = commentUserId;
        this.commentBossId = commentBossId;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.commentScore = commentScore;
        this.commentImg = commentImg;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentBossId() {
        return commentBossId;
    }

    public void setCommentBossId(String commentBossId) {
        this.commentBossId = commentBossId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(String commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "commentId='" + commentId + '\'' +
                ", commentUserId='" + commentUserId + '\'' +
                ", commentBossId='" + commentBossId + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", commentScore='" + commentScore + '\'' +
                ", commentImg='" + commentImg + '\'' +
                '}';
    }

}
