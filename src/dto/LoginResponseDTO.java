package dto;

public class LoginResponseDTO {
	private int memberId;      // member_id
    private String phoneNo;    // phone_no
    private String memberName; // member_name
    
    public LoginResponseDTO() {}
    
	public LoginResponseDTO(int memberId, String phoneNo, String memberName) {
		this.memberId = memberId;
		this.phoneNo = phoneNo;
		this.memberName = memberName;
	}
	
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
