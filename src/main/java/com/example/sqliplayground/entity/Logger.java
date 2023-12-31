package com.example.sqliplayground.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "logger")
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String agent;

    public void setId(long id) {
        this.id = id;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String ip;
}
