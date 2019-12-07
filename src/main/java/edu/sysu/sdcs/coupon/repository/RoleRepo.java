package edu.sysu.sdcs.coupon.repository;

import edu.sysu.sdcs.coupon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    List<Role> findRolesByRoleEquals(String s);
}
