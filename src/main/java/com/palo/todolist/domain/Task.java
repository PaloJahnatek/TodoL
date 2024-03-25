package com.palo.todolist.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class Task {
    @Nullable
    private Integer id;
    @Nonnull
    private Integer userId;
    @Nonnull
    private String name;
    @Nonnull
    private Integer status;
    @Nonnull
    private String category;
    @Nonnull
    private String description;
    @Nonnull
    private Timestamp createdAt;

    public Task() {
    }

    public Task(@Nullable Integer id, @Nonnull Integer userId, @Nonnull String name, @Nonnull Integer status, @Nonnull String category, @Nonnull String description, @Nonnull Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.category = category;
        this.description = description;
        this.createdAt = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(userId, task.userId) && Objects.equals(name, task.name) && Objects.equals(status, task.status) && Objects.equals(category, task.category) && Objects.equals(description, task.description) && Objects.equals(createdAt, task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, status, category, description, createdAt);
    }
}
