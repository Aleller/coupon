package edu.sysu.sdcs.web.entity;

import edu.sysu.sdcs.web.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableName.COUPON)
public class Coupon implements Serializable {

  private static final long serialVersionUID = 7444409539093545314L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull(message = "coupon name not null")
  private String name;

  @NotNull(message = "amount not null")
  @Min(value=0, message = "amount must >= 0")
  private Integer amount;

  private String description;

  @NotNull(message = "stock not null")
  @Min(value = 0 , message = "stock must >= 0")
  private Integer stock;

  @Column(name = "create_time")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

  @Column(name = "update_time")
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
}
