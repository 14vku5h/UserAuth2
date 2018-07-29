/**
 * 
 */
package lkv.app.model;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

@Entity
@Table(name="Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userid")
	private Long id;
	
	@Column(nullable=false)
	private String firstName;
	private String lastName;


	@Column(unique=true,nullable=false)
	@Email(message="Invalid Email Format")
	private String email;
	
	@Column(unique=true,nullable=true)
	private String mobNo;
	
    private String gender;
	
	@Lob
	@Column(name="profile_image",columnDefinition="mediumblob")
	private byte[] image;
	
	@Transient
	private String base64Image;
	
	private int verified;
	private Date createdOn;
	private Date updatedOn;
	private String createdBy;
	private String updatedBy;

	@OneToOne(cascade=CascadeType.ALL,fetch =FetchType.EAGER)
	@JoinColumn(name="usercred_id")
	private UserCredential userCredential;
	
	
	public Users() {}
	public Users(String firstname,String lastname,String email,UserCredential userCredential) {
		this.firstName=firstname;
		this.lastName=lastname;
		this.email=email;
		this.userCredential=userCredential;
	}


	@PrePersist
	private void onCreate(){
		
		createdOn = new Date();
		userCredential.setUserName(this.getEmail());
		verified = 1;
	}
	
	@PreUpdate
	private void onUpdate(){		
		updatedOn = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
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

	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public UserCredential getUserCredential() {
		return userCredential;
	}

	public void setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobile=" + mobNo + ", gender=" + gender + ", verified=" + verified + "]";
	}
	
	
	
}
