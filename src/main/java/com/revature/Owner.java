/**
 * 
 */
package com.revature;

/**
 * @author bryan
 *
 */
public class Owner {

	/**
	 * 
	 */
	public Owner() {
		// TODO Auto-generated constructor stub
		this.balance = 0;
	}
	
	int balance;
	private String OwnerName;
	private String OwnerPassword;
	
	public int getBalance() {
		return this.balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getOwnerName() {
		return this.OwnerName;
	}
	public void setOwnerName(String ownerName) {
		this.OwnerName = ownerName;
	}
	public String getOwnerPassword() {
		return this.OwnerPassword;
	}
	public void setOwnerPassword(String ownerPassword) {
		this.OwnerPassword = ownerPassword;
	}
	
}
