package com.ashok.shopInventory.web.Repository;


import com.ashok.shopInventory.web.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole,Long> {

}
