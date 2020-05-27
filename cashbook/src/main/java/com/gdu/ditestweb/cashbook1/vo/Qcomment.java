package com.gdu.ditestweb.cashbook1.vo;

public class Qcomment {
	private int commentNo;
	private String memberId;
	private String commentContents;
	private int boardNo;
	private String commentDate;
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContents() {
		return commentContents;
	}
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	@Override
	public String toString() {
		return "Qcomment [commentNo=" + commentNo + ", memberId=" + memberId + ", commentContents=" + commentContents
				+ ", boardNo=" + boardNo + ", commentDate=" + commentDate + "]";
	}
	
	
}
