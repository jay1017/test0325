package DTO;

public class Book {
	private String bDate;
	private String bBranch;
	private String bTheme;
	private String bId;
	
	public String getbDate() {
		return bDate;
	}
	public void setbDate(String bDate) {
		this.bDate = bDate;
	}
	
	public String getbBranch() {
		return bBranch;
	}
	public void setbBranch(String bBranch) {
		this.bBranch = bBranch;
	}
	public String getbTheme() {
		return bTheme;
	}
	public void setbTheme(String bTheme) {
		this.bTheme = bTheme;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	@Override
	public String toString() {
		return "Book [bDate=" + bDate + ", bBranch=" + bBranch + ", bTheme=" + bTheme + ", bId=" + bId + "]";
	}
	
}
