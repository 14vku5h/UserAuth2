package thym.app.model;



import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;



@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="User_Id")
	private Long id;
	
	private String firstname;
	private String lastname;
	@Column(unique=true,nullable=false)
	private String email;
	@Column(unique=true,nullable=true)
	private String mobile;
	private Date dob;
	private int age;
	private String gender;	
	@Lob
	private byte[] image;	
	@Transient
	private String base64Image;
	
	@OneToOne(cascade=CascadeType.ALL,fetch =FetchType.EAGER)
	@JoinColumn(name="usercred_id")	
	private UserCredential credentials;
	
	@Transient
	private int otp;
	
	private String confirmationToken;
	
	private boolean verified;
	
	
	private Date createdOn;
	private Date updatedOn;
	
	@PrePersist
	void beforeSaving() {
		createdOn = new Date();
		//credentials.setUsername(email);
	}
	@PreUpdate
	void beforeUpdate() {
		this.updatedOn = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public UserCredential getCredentials() {
		return credentials;
	}
	public void setCredentials(UserCredential credentials) {
		this.credentials = credentials;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdateOn() {
		return updatedOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updatedOn = updateOn;
	}
	

	
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", mobile=" + mobile + ", dob=" + dob + ", age=" + age + ", gender=" + gender + ", image="
				+ Arrays.toString(image) + ", base64Image=" + base64Image 
				+ ", createdOn=" + createdOn + ", updateOn=" + updatedOn + "]";
	}
	

}
