package com.rollin.repo;


import com.rollin.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    public long countByUserId(String userId);
    public Optional<UserEntity> findByUserIdAndPassword(String userId, String password);

    public List<UserEntity> findByUserId(String userId);

    @Query(value="SELECT * FROM users u where u.id <> :id", nativeQuery = true)
    public List<UserEntity> findAllByNotId(@Param("id") Integer id);
}
