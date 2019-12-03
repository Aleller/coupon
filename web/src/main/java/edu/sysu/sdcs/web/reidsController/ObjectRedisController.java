package edu.sysu.sdcs.web.reidsController;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author anan
 * @since 2016/10/26 - 22:43
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ObjectRedisController {

  @Autowired
  private RedisService redisService;


  @GetMapping("setObject")
  public String setDataToRedis(@RequestBody User user,
                               BindingResult bindingResult) {

    redisService.set(user.getAccount(), user);
    return "success";
  }

  @GetMapping("getObject")
  public User getDataFromRedisById(String id) {
    return redisService.get(id, User.class);
  }

  @DeleteMapping("deleteObject")
  public String deleteObject(String id) {
    redisService.delete(id);
    return "delete success";
  }


  /**********************************************************************
   * Sample RedisTemplate
   */
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Resource(name="stringRedisTemplate")
  ValueOperations<String,String> valueOperations;

  @RequestMapping("/")
  public String helloworld(){
    return "Hello world!";
  }

  @GetMapping("set")
  public Object setDataToRedis(String key, String value) {
    if (!this.stringRedisTemplate.hasKey(key)) {
      valueOperations.set(key, value);
    }
    System.out.println("set data: key=" + key + ", value=" + valueOperations.get(key));
    return "success";
  }

  @GetMapping("get")
  public Object getDataFromRedis(String key) {
    if (this.stringRedisTemplate.hasKey(key)) {
      String value = valueOperations.get(key);
      System.out.println("found key " + key + ", value=" + valueOperations.get(key));
      return value;
    } else {
      return "faild";
    }
  }

}
