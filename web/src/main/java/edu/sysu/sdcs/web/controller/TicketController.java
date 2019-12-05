package edu.sysu.sdcs.web.controller;

import edu.sysu.sdcs.web.entity.Ticket;
import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.enums.ResultEnum;
import edu.sysu.sdcs.web.service.TicketService;
import edu.sysu.sdcs.web.util.Result;
import edu.sysu.sdcs.web.util.ResultVO;
import edu.sysu.sdcs.web.util.SubjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author  anan
 */
@Slf4j
@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

  @Autowired
  TicketService ticketService;

  @PostMapping()
  @RequiresPermissions({"SELLER"})
  public ResultVO save(@Valid @RequestBody Ticket ticket,
                       BindingResult bindingResult) {
    if (bindingResult.hasErrors() ){
      log.error("==>[TicketController.save]PARAM_ERROR, ticket={}, message={}", ticket, bindingResult.getFieldError().getDefaultMessage());
      return Result.error(ResultEnum.PARAM_ERROR);
    }
    return Result.success(ticketService.save(ticket));
  }


  /**
   * find ticket_user by user BUYER
   * @return
   */
  @GetMapping()
  @RequiresPermissions({"BUYER"})
  public ResultVO findAllByUser() {
    User user = SubjectUtils.getProfile();
    return Result.success(ticketService.findAllByUser(user.getId()));
  }

//
//  @DeleteMapping("/{id}")
//  public ResultVO delete(@PathVariable("id") Integer  id) {
//    ticketService.delete(id);
//    return Result.success();
//  }
//


}
