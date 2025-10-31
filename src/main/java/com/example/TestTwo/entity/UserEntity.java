package com.example.TestTwo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true,  nullable = false)
    private String name;

    @Column(unique = true,  nullable = false)
    private String email;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<TaskEntity> tasks;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
