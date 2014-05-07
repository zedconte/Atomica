package com.industrika.administration.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity						
@Table(name="policies")
public class Policy  implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	private String accountsDB;
	public enum PolicyType { INGRESO,EGRESO,DIARIO};
	
	@Id
	@GeneratedValue
	private Integer idPolicy;
	private String policyType;
	private String policyDate;
	@Transient
	private String rowsAsJson;

	@OneToMany(mappedBy="policy",cascade={CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<PolicyRow> rows= new HashSet<PolicyRow>();
	
    public Set<PolicyRow> getRows() {
		return rows;
	}
	
 
	public void setRows(Set<PolicyRow> rows) {
		this.rows = rows;
		if(rows!=null){
			PolicyRow[] policiesR=rows.toArray(new PolicyRow[rows.size()]);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String json = gson.toJson(policiesR);
			this.rowsAsJson=json;
		}
	}
	public Integer getIdPolicy() {
		return idPolicy;
	}
	public void setIdPolicy(Integer idPolicy) {
		this.idPolicy = idPolicy;
	}
	
	public String getPolicyType() {
		return policyType;
	}
	
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	
	public void addPolicyRow(PolicyRow row){
		rows.add(row);
	}

	public String getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(String policyDate) {
		this.policyDate = policyDate;
	}

	public String getAccountsDB() {
		return accountsDB;
	}

	public void setAccountsDB(String accountsDB) {
		this.accountsDB = accountsDB;
	}

	public String getRowsAsJson() {
		return rowsAsJson;
	}

	public void setRowsAsJson() {
		if(rows!=null){
			PolicyRow[] policiesR=rows.toArray(new PolicyRow[rows.size()]);
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String json = gson.toJson(policiesR);
			this.rowsAsJson = json;
		}
	}

	@Override
	public String toString() {
		return "Policy [accountsDB=" + accountsDB + ", idPolicy=" + idPolicy
				+ ", policyType=" + policyType + ", policyDate=" + policyDate
				+ ", rowsAsJson=" + rowsAsJson + ", rows=" + rows + "]";
	}
}
