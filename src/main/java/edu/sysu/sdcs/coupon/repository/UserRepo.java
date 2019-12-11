package edu.sysu.sdcs.coupon.repository;


import edu.sysu.sdcs.coupon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    User findUserByUsernameEquals(String s);
}
