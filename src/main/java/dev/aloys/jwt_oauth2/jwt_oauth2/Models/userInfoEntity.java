package dev.aloys.jwt_oauth2.jwt_oauth2.Models;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class userInfoEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID userId;

    @Column(name = "USER_NAME",unique = true,nullable = false)
    private String username;

    @Column(name = "EMAIL_ID",nullable = false,unique = true)
    private String emailId;

    @Column(name = "PASSWORD",nullable = false)
    @Size(min = 5,message = "Password must be 5 or more characters long.")
    private String password;

    @Column(name = "MOBILE_NUMBER",nullable = true)
    private String mobileNumber;

    @Column(name = "ROLES",nullable = false)
    private String roles;

    private Date timeToChangePassword;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = false;
}
