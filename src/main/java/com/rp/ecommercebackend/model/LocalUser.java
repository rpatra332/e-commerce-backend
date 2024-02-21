package com.rp.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "local_user")
public class LocalUser {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 1000)
	private String password;

	@Column(name = "email", nullable = false, unique = true, length = 320)
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("id DESC")
	private List<VerificationToken> verificationTokens = new ArrayList<>();

	@Column(name = "email_verified", nullable = false)
	private boolean emailVerified = false;

}
