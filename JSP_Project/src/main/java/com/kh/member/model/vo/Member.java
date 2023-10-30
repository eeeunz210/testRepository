package com.kh.member.model.vo;

import java.sql.Date;

public class Member {
	// Member 테이블에 맞춰서 필드 작성/get/set/toString 생성자 만들기

	private int userNo; // USER_NO NUMBER
	private String userId;// USER_ID VARCHAR2(30 BYTE)
	private String userPwd;// USER_PWD VARCHAR2(100 BYTE)
	private String userName;// USER_NAME VARCHAR2(15 BYTE)
	private String phone;// PHONE VARCHAR2(13 BYTE)
	private String email; // EMAIL VARCHAR2(100 BYTE)
	private String address; // ADDRESS VARCHAR2(100 BYTE)
	private String interest; // INTEREST VARCHAR2(100 BYTE)
	private Date enrollDate; // ENROLL_DATE DATE
	private Date modifyDate;// MODIFY_DATE DATE
	private String status;// STATUS VARCHAR2(1 BYTE)

	public Member() {
		super();
	}

	public Member(String userId, String userName, String phone, String email, String address, String interest) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}

	public Member(String userId, String userPwd, String userName, String phone, String email, String address,
			String interest) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}





	public Member(int userNo, String userId, String userPwd, String userName, String phone, String email,
			String address, String interest, Date enrollDate, Date modifyDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", phone=" + phone + ", email=" + email + ", address=" + address + ", interest=" + interest
				+ ", enrollDate=" + enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}

}
