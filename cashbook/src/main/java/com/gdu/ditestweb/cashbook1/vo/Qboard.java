package com.gdu.ditestweb.cashbook1.vo;

public class Qboard {
	private int boardNo;
	private String memberId;
	private String boardTitle;
	private String boardContents;
	private String boardDate;
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContents() {
		return boardContents;
	}
	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	@Override
	public String toString() {
		return "Qboard [boardNo=" + boardNo + ", memberId=" + memberId + ", boardTitle=" + boardTitle
				+ ", boardContents=" + boardContents + ", boardDate=" + boardDate + "]";
	}
	
	
	
}
