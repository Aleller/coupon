package edu.sysu.sdcs.consumer.mq.service;

import com.alibaba.fastjson.JSON;
import edu.sysu.sdcs.consumer.mq.entity.TicketUser;
import edu.sysu.sdcs.consumer.mq.entity.User;
import edu.sysu.sdcs.consumer.mq.enums.RedisEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author anan
 * @date: Created in 2019/12/3 15:08
 */
@Service
public class RedisService {

  /*@Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  @Resource(name = "redisTemplate")
  private ValueOperations<Object, Object> valOps;*/

  @Resource(name = "stringRedisTemplate")
  ValueOperations<String, String> valOps;

  @Autowired
  private StringRedisTemplate redisTemplate;


  public void setUser(User user) {
    this.set(user.getAccount(), user, RedisEnum.USER);
  }
  public User getUserByAccount(String account) {
    return get(account, User.class, RedisEnum.USER);
  }

  /**
   * 用来 做 已秒杀过 的标识
   * key : ticketId-userId-0
   * @param tickerUser
   */
  public void setTicketUser(TicketUser tickerUser) {
    this.set(tickerUser.getId()+"", tickerUser, RedisEnum.TICKET_USER);
  }

  /**
   * 用来判断是否秒杀过的
   * key : ticketId-userId-0
   * @param tickerId
   * @param userId
   * @return true 秒杀过
   *         false 还没秒杀成功过
   */
  public Boolean hasTicketUser(Integer tickerId, Integer userId) {
    return hasKeyByKey(tickerId + "-" + userId, RedisEnum.TICKET_USER);
  }

  /**
   * 查找 排队 的 Ticket_User
   * @return
   */
  public Boolean hasQueueByKey(TicketUser ticketUser) {
    return hasKeyByKey(ticketUser.getTicket().getId() + "-" + ticketUser.getUserId(), RedisEnum.TICKET_USER);
  }

  /**
   * 查找 排队 的 Ticket_User
   * @return
   */
  public void deleteQueueByKey(TicketUser ticketUser) {
    deleteByQueueKey(ticketUser.getTicket().getId() ,ticketUser.getUserId(), RedisEnum.TICKET_USER);
  }


  /**
   * 清空所有
   */
  public void deleteAll(){
    Set<String> keys = redisTemplate.keys("*");
    redisTemplate.delete(keys);
  }



  /************************************    Util   *******************************************************/

  /**
   * 设置缓存
   *
   * @param key   缓存key
   * @param value 缓存value
   */
  public void set(String key, Object value, RedisEnum type) {
    valOps.set(key + "-" + type.getType(), JSON.toJSONString(value));
  }

  /**
   * 获取指定key的缓存
   *
   * @param key
   */
  public <T> T get(String key, Class<T> clazz, RedisEnum type) {
    Object value = JSON.parseObject((String) valOps.get(key + "-" + type.getType()), clazz);
    return (T) value;
  }


  /**
   * 是否存在
   * @param key
   * @param type
   * @return
   */
  public Boolean hasKeyByKey(String key, RedisEnum type) {
    return redisTemplate.hasKey(key + "-" + type.getType());
  }

  /**
   * 设置缓存，并且自己指定过期时间
   *
   * @param key
   * @param value
   * @param timeout  过期时间
   * @param timeunit 时间单位
   */
  public void setWithTimeout(String key, Object value, RedisEnum type, long timeout, TimeUnit timeunit) {
    valOps.set(key + "-" + type.getType(), JSON.toJSONString(value), timeout, timeunit);
  }

  /**
   * 删除指定key的缓存
   *
   * @param key
   */
  public void delete(String key, RedisEnum type) {
      redisTemplate.delete(key+ "-" + type.getType());
  }


  /****************************************  queue ********************************************
   * key : queue-ticketId-userId-0
   */

  /**
   * 查找指定的key
   * @param ticker
   * @param userId
   * @param type
   */
  public Boolean hasKeyByQueueKey(Integer ticker ,Integer userId, RedisEnum type) {
    return redisTemplate.hasKey("queue-" + ticker + "-" + userId + type.getType());
  }

  /**
   * 写入指定的key
   * @param ticker
   * @param userId
   * @param type
   */
  public void addByQueueKey(Integer ticker ,Integer userId, RedisEnum type) {
    valOps.set("queue-" + ticker + "-" + userId + type.getType(), "");
  }


  /**
   * 写入指定的key
   * @param ticker
   * @param userId
   * @param type
   */
  public void deleteByQueueKey(Integer ticker ,Integer userId, RedisEnum type) {
    redisTemplate.delete("queue-" + ticker + "-" + userId + type.getType());
  }
}
