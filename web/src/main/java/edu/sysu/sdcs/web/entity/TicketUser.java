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
@Entity
@Table(name = TableName.TICKET_USER)
public class TicketUser implements Serializable {

  private static final long serialVersionUID = 1452073196199651901L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

//  @NotNull(message = "ticketId name not null")
//  @Column(name = "ticket_id")
//  private Integer ticketId;

  @JoinColumn(name="ticket_id")
  @NotNull(message = "ticketId name not null")
  @ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
  private Ticket ticket  ;


  @NotNull(message = "userId name not null")
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "create_time")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

}
