package edu.sysu.sdcs.provider;


import edu.sysu.sdcs.provider.service.Sendder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author anan
 * @created by anan on 2019/3/4 16:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SendderTest {

  @Autowired
  private Sendder sendder;

  @Test
  public void send() throws InterruptedException {
//    int flag = 0;
//    while (true){
//      flag++;
//      Thread.sleep(2000);
//      this.sender.send("hello RabbitMQ "+ flag);
      this.sendder.send("hello RabbitMQ ");
//    }
  }
}