package com.revature;


// Not using this for now... need to redesign before using something like this

public class CLI implements MenuOutput {

	public CLI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void showActionMenu() {
		//logger.info("in showActionMenu");
		System.out.println("Welcome, What would you like to do?");
		System.out.println("");
		System.out.println("V: View balance");
		System.out.println("W: Withdraw money");
		System.out.println("D: Deposit money");
		System.out.println("Q: Quit");
//		System.out.println("Your balance is currently " + this.balance);
	}

	@Override
	public void showWithdrawMenu() {
//		System.out.println("Withdrawing from a a balance of " + this.balance);
		System.out.println("Enter amount to withdraw: ");
	}

	@Override
	public void showDepositMenu() {
//		System.out.println("Depositing money into an account that has a balance of " + this.balance);
		System.out.println("Enter amount to deposit: ");
	}

	@Override
	public void showBalance() {
//		System.out.println(this.balance);
	}

}
