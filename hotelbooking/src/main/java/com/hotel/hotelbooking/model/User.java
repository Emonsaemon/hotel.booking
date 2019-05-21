package com.hotel.hotelbooking.model;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String surname;

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9]+@[a-z]+\\.[a-z]+")
    @Size(min = 5, max = 30)
    @Column(nullable = false, length = 30)
    private String email;

    @NotBlank
    @Pattern(regexp = "(\\+)?[0-9]+")
    @Size(min = 7, max = 13)
    @Column(nullable = false, length = 13, name = "phone_number")
    private String phoneNumber;

    @Size(max = 65)
    @Column(length = 65)
    private String password;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    @OneToMany(mappedBy="user")
    private List<Reservation> reservations;

    public User() {
    }

    public User(@NotBlank @Size(max = 30) String name,
                @NotBlank @Size(max = 30) String surname,
                @NotBlank @Pattern(regexp = "[A-Za-z0-9]+@[a-z]+\\.[a-z]+") @Size(min = 5, max = 30) String email,
                @NotBlank @Pattern(regexp = "(\\+)?[0-9]+") @Size(min = 7, max = 13) String phoneNumber,
                @Size(max = 65) String password,
                @NotNull UserRole role, List<Reservation> reservations) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
