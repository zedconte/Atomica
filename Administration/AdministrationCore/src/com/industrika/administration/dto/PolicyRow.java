package com.industrika.administration.dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name="policyrows")
public class PolicyRow  implements java.io.Serializable{
	
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPolicyRow")
	private Integer idPolicyRow;
	
	@ManyToOne
	@JoinColumn(name="policy")
	private Policy policy;
	
	@SerializedName("accountNumber")
	@Expose
	private String accountNumber;
	
	@SerializedName("checkNumber")
	@Expose
	private String checkNumber;
	
	@SerializedName("name")
	@Expose
	private String name;
	
	@SerializedName("details")
	@Expose
	private String details;
	
	@SerializedName("debit")
	@Expose
	private double debit;
	
	@SerializedName("credit")
	@Expose
	private double credit;
	
	
	public Integer getIdPolicyRow() {
		return idPolicyRow;
	}
	public void setIdPolicyRow(Integer idPolicyRow) {
		this.idPolicyRow = idPolicyRow;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	
	public Policy getPolicy() {
		return policy;
	}
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	@Override
	public String toString() {
		return "PolicyRow [idPolicyRow=" + idPolicyRow + ", accountNumber="
				+ accountNumber + ", checkNumber=" + checkNumber + ", name="
				+ name + ", details=" + details + ", debit=" + debit
				+ ", credit=" + credit + "]";
	}
	
	
}
