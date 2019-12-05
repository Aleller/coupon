package edu.sysu.sdcs.web.entity;

import edu.sysu.sdcs.web.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUserForm implements Serializable {

  private static final long serialVersionUID = 2710913174946744229L;
  private Integer ticketId  ;
  private Integer userId;

}
