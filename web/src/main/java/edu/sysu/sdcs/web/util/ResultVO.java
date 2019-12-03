package edu.sysu.sdcs.web.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author anan
 * @date: Created in 2019/12/3 10:57
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> implements Serializable {

  private static final long serialVersionUID = 6313679192453605573L;

  private Integer code;
  private String msg;
  private T data;


}
