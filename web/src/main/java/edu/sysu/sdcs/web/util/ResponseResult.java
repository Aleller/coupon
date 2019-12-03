package edu.sysu.sdcs.web.util;

import java.util.ArrayList;
import java.util.List;

public class ResponseResult {

	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;
	public static final int WARNING = 6;

	/**
	 * 请求状态 0 成功 -1 失败 6 部分成功
	 */
	private int code;

	/**
	 * 返回信息说明
	 */
	private String message;

	private List<String> errors;

	private List<String> messages;

	private Integer id;

	/**
	 * 错误列表（预留）
	 */
	private List<Object> list;

	public void addError(String error) {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		this.errors.add(error);
		this.code = ResponseResult.FAILURE;
	}

	public void addMessage(String message) {
		if (this.messages == null) {
			this.messages = new ArrayList<String>();
		}
		this.messages.add(message);
	}

	/**
	 * 是否存在错误信息
	 * 
	 * @return
	 */
	public boolean hasErrors() {
		return !(errors == null || errors.isEmpty());
	}

	/**
	 * 是否存在信息
	 * 
	 * @return
	 */
	public boolean hasMessages() {
		return !(messages == null || messages.isEmpty());
	}

	public int getCode() {
		return (errors == null || errors.isEmpty()) ? code : ResponseResult.FAILURE;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		if (hasMessages()) {
			this.message = (this.message == null ? "" : this.message);
			this.message += this.messages.toString();
		}
		if (hasErrors()) {
			this.message = (this.message == null ? "" : this.message);
			this.message += errors.toString();
			this.errors = new ArrayList<String>();
		}
		return message;
	}

	public int getMessageSize() {
		return this.messages == null ? 0 : this.messages.size();
	}

	public int getErrorSize() {
		return this.errors == null ? 0 : this.errors.size();
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 将提示信息转为错误信息
	 */
	public void message2error() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		this.errors.addAll(messages);
		messages = new ArrayList<String>();
	}

}
