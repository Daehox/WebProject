package com.yeonsung.dto;

//데이터 전송 객체 클래스
public class UserDto {
	
	//필드 선언
    private String id;
    private String passwd;
    private String name;
    private String email;
    private String postcode; 
    private String address; 
    private String detailAddress; 
    private String extraAddress; 

    // Getter와 Setter
    public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getExtraAddress() {
		return extraAddress;
	}

	public void setExtraAddress(String extraAddress) {
		this.extraAddress = extraAddress;
	}

	// id 필드에 대한 setter 메서드
    public void setId(String id) {
        this.id = id;
    }

    // id 필드에 대한 getter 메서드
    public String getId() {
        return id;
    }

    // passwd 필드에 대한 setter 메서드
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    // passwd 필드에 대한 getter 메서드
    public String getPasswd() {
        return passwd;
    }

    // name 필드에 대한 setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    // name 필드에 대한 getter 메서드
    public String getName() {
        return name;
    }

    // email 필드에 대한 setter 메서드
    public void setEmail(String email) {
        this.email = email;
    }

    // email 필드에 대한 getter 메서드
    public String getEmail() {
        return email;
    }
}
