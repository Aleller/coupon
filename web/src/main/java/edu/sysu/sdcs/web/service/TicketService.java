package edu.sysu.sdcs.web.service;

import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.repository.TicketRepo;
import edu.sysu.sdcs.web.repository.TicketUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 *
 * 辣鸡，谁他妈起的 coupon 作为优惠券的名字？
 *  这样的全部都是 圈圈圈圈（OoocoponCpoo）的 你看起来会很爽吗？
 *
 *   @Autowired
 *   CouponRepo couponRepo;
 *   @Autowired
 *   CouponUserRepo couponUserRepo;
 *
 *   public Coupon save(Coupon coupon) {
 *     return couponRepo.save(coupon);
 *   }
 *
 *
 *
 */
@Slf4j
@Service
@Transactional
public class TicketService {

  @Autowired
  TicketRepo ticketRepo;
  @Autowired
  TicketUserRepo ticketUserRepo;

  public Ticket save(Ticket ticket) {
    return ticketRepo.save(ticket);
  }



}
