package com.example.TestTwo.service;

import com.example.TestTwo.model.Comment;
import com.example.TestTwo.model.User;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentService {
    private final Map<User, Comment> comments = new HashMap<>();

    public Comment getComment(User author) {
        return comments.get(author);
    }

    public Comment addComment(Comment comment) {
        comments.put(comment.getAuthor(), comment);
        return comments.get(comment.getAuthor());
    }

    public void removeComment(User author) {
        comments.remove(author);
    }

    public Comment updateComment(User author, Comment updates){
        Comment existingComment = getComment(author);
        if (existingComment == null) {
            return null;
        }
        if (updates.getDescription() != null) {
            existingComment.setDescription(updates.getDescription());
        }
        if (updates.getAuthor() != null) {
            existingComment.setAuthor(updates.getAuthor());
        }
        if (updates.getDate() != null) {
            existingComment.setDate(updates.getDate());
        }
        return existingComment;
    }
}
