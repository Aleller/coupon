package edu.sysu.sdcs.web.repository;


import edu.sysu.sdcs.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
//    public List<User> findAllByUsernameEquals(String userName);

  User findByAccount(String account);

}
