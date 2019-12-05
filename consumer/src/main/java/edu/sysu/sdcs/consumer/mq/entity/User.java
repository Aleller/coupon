package edu.sysu.sdcs.consumer.mq.entity;

import edu.sysu.sdcs.consumer.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: anan
 * @date: Created in 2019/12/3 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableName.USER)
public class User implements Serializable {

  private static final long serialVersionUID = -1447536258826937017L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "account")
  @NotNull(message = "account not null")
  private String account;

  @Column(name = "password")
  @NotNull(message = "password not null")
  private String password;

  /**
   *  Buyer
   *  Seller
   */
  @Column(name = "role")
  private String role;

}
