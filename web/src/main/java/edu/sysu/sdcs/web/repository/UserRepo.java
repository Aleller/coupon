package edu.sysu.sdcs.web.repository;


import edu.sysu.sdcs.web.TableName;
import edu.sysu.sdcs.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
//    public List<User> findAllByUsernameEquals(String userName);

  User findByAccount(String account);

  @Query(value = "SELECT * FROM "+ TableName.USER +" WHERE id = ?", nativeQuery = true)
  User findOneById(Integer id);

}
