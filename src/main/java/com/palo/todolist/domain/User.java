package com.palo.todolist.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter

public class User {
    @Nullable
    private Integer id;
    @Nonnull
    private String name;
    @Nonnull
    private String surname;
    @Nonnull
    private String nickname;
    @Nonnull
    private String email;
    @Nullable
    private Integer age;
    @Nonnull
    private String password;

    public User() {

    }

    public User(@Nullable Integer id, @Nonnull String name, @Nonnull String surname, @Nonnull String nickname, @Nonnull String email, @Nullable Integer age, @Nonnull String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(age, user.age) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, nickname, email, age, password);
    }
}
