package com.ashok.shopInventory.web.Repository;


import com.ashok.shopInventory.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.name = :name")
    User getUserByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User getUserById(@Param("userId") long userId);
}
