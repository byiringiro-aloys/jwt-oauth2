package dev.aloys.jwt_oauth2.jwt_oauth2.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.aloys.jwt_oauth2.jwt_oauth2.Models.userInfoEntity;

@Repository
public interface userInfoRepo extends JpaRepository<userInfoEntity,UUID> {
    
}
 