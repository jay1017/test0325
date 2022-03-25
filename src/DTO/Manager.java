package DTO;

public class Manager {
	// 1. 필드 생성
	private String branch;
	private String mId;
	private String mPw;
	private String mName;
	private String mPhone;
	
	// 2. 메소드 선언
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmPw() {
		return mPw;
	}
	public void setmPw(String mPw) {
		this.mPw = mPw;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	@Override
	public String toString() {
		return "Manager [branch=" + branch + ", mId=" + mId + ", mPw=" + mPw + ", mName=" + mName + ", mPhone=" + mPhone
				+ "]";
	}
}
