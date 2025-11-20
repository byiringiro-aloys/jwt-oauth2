package dev.aloys.jwt_oauth2.jwt_oauth2.Models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Duration;
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

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL_ID", nullable = false, unique = true)
    private String emailId;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 5, message = "Password must be 5 or more characters long.")
    private String password;

    @Column(name = "MOBILE_NUMBER", nullable = true)
    private String mobileNumber;

    @Column(name = "ROLES", nullable = false)
    private String roles;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "PASSWORD_EXPIRE_DATE")
    private LocalDateTime passwordExpireDate;

    @Column(name = "IS_ACCOUNT_NON_EXPIRED")
    private boolean isAccountNonExpired = true;

    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean isAccountNonLocked = true;

    @Column(name = "IS_CREDENTIALS_NON_EXPIRED")
    private boolean isCredentialsNonExpired = true;

    @Column(name = "IS_ENABLED")
    private boolean isEnabled = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        passwordExpireDate = createdAt.plusDays(30);
        this.isCredentialsNonExpired = true;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
    }

    @PreUpdate
    protected void onUpdate() {
        checkAndUpdateCredentialsExpiration();
    }

    private void checkAndUpdateCredentialsExpiration() {
        if (passwordExpireDate != null && LocalDateTime.now().isAfter(passwordExpireDate)) {
            this.isCredentialsNonExpired = false;
        } else {
            this.isCredentialsNonExpired = true;
        }
    }

    public boolean areCredentialsValid() {
        checkAndUpdateCredentialsExpiration();
        return this.isCredentialsNonExpired;
    }

    public void resetPasswordExpiry() {
        this.passwordExpireDate = LocalDateTime.now().plusDays(30);
        this.isCredentialsNonExpired = true;
    }

    public long getDaysUntilPasswordExpiry() {
        if (passwordExpireDate == null) {
            return 0;
        }
        return Duration.between(LocalDateTime.now(), passwordExpireDate).toDays();
    }

    public void extendPasswordExpiry(int additionalDays) {
        this.passwordExpireDate = LocalDateTime.now().plusDays(additionalDays);
        this.isCredentialsNonExpired = true;
    }
}