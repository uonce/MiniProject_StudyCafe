package dto;

public class MemberDTO {

    private int memberId;      // member_id
    private String phoneNo;    // phone_no
    private String password;   // password
    private String memberName; // member_name

    // 생성자
    public MemberDTO() {}

    public MemberDTO(int memberId, String phoneNo, String password, String memberName) {
        this.memberId = memberId;
        this.phoneNo = phoneNo;
        this.password = password;
        this.memberName = memberName;
    }
    
    public MemberDTO(String phoneNo, String password, String memberName) {
        this.phoneNo = phoneNo;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
