package com.cg.banking.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Customer;
import com.cg.banking.beans.Transaction;
import com.cg.banking.services.BankingServicesImpl;

public class MainClass {
	public static void main(String[] args) {
		BankingServicesImpl bankingServices=new BankingServicesImpl();
		int customerId,pinNumber;
		long accountNo;
		float amount;
		int key=0;
		Scanner sc=new Scanner(System.in);
		while(key!=12){
			System.out.println("Enter 1 : Add New Customer"+"\n"+
					"2 : Open a Account"+"\n"+
					"3 : Generate New Pin"+"\n"+
					"4 : Change Account Pin"+"\n"+
					"5 : Deposit amount"+"\n"+
					"6 : withdraw amount"+"\n"+
					"7 : Fund Transfer"+"\n"+
					"8 : View Account Detaills"+"\n"+
					"9 : View AllTransaction Details"+"\n"+
					"10 : View All Customer Details");
			key=sc.nextInt();
			switch (key) {
			case 1:System.out.println("Enter the Customer Details");
			System.out.println("Enter firstName");
			String firstName=sc.next();
			System.out.println("Enter lastName");
			String lastName=sc.next();
			System.out.println("Enter emailId");
			String emailId=sc.next();
			System.out.println("Enter panCard	");
			String panCard=sc.next();
			System.out.println("Enter LocalAddressCity");
			String localAddressCity=sc.next();
			System.out.println("Enter LocalAddressState");
			String localAddressState=sc.next();
			System.out.println("Enter LocalAddresspinCode");
			int localAddressPinCode=sc.nextInt();
			System.out.println("Enter HomeAddresscity");
			String homeAddressCity=sc.next();
			System.out.println("Enter HomeAddressState");
			String homeAddressState=sc.next();
			System.out.println("Enter  homeAddressPinCode");
			int  homeAddressPinCode=sc.nextInt();
			customerId=bankingServices.acceptCustomerDetails(firstName, lastName, emailId, panCard, localAddressCity, localAddressState, localAddressPinCode, homeAddressCity, homeAddressState, homeAddressPinCode);
			System.out.println("Customer Id is"+customerId);
			break;
			case 2: System.out.println("Opening an account");
			System.out.println("Enter the customer Id");
			customerId=sc.nextInt();
			System.out.println("Give the Account Type");
			String accountType=sc.next();
			System.out.println("Enter the Initial balance ");
			float initBalance=sc.nextFloat();
			accountNo=bankingServices.openAccount(customerId, accountType, initBalance);
			System.out.println("Account Number is"+accountNo);
			break;
			case 3: 
				System.out.println("Enter CustomerId");
				customerId=sc.nextInt();
				System.out.println("Enter Account number");
				accountNo=sc.nextLong(); 
				pinNumber=bankingServices.generateNewPin(customerId, accountNo);
				System.out.println("pin Number is"+pinNumber);
				break;
			case 4:
				System.out.println("Enter CustomerId");
				customerId=sc.nextInt();
				System.out.println("Enter Account number");
				accountNo=sc.nextLong(); 
				System.out.println("Enter the old Pin Number");
				pinNumber=sc.nextInt();
				System.out.println("Enter the New Pin Number");
				int newPinNumber=sc.nextInt();
				System.out.println(bankingServices.changeAccountPin(customerId, accountNo, pinNumber, newPinNumber));
				break;
			case 5: 
				System.out.println("Enter CustomerId");
				customerId=sc.nextInt();
				System.out.println("Enter Account number");
				accountNo=sc.nextLong(); 
				System.out.println("Enter the amount to be deposited");
				amount=sc.nextFloat();
				System.out.println(bankingServices.depositAmount(customerId, accountNo, amount));
				break;
			case 6:  
				System.out.println("Enter CustomerId");
				customerId=sc.nextInt();
				System.out.println("Enter Account number");
				accountNo=sc.nextLong(); 
				System.out.println("Enter the amount to be Withdrawn");
				amount=sc.nextFloat();
				System.out.println("Enter the Pin Number");
				pinNumber=sc.nextInt();
				bankingServices.withdrawAmount(customerId, accountNo, amount, pinNumber);
				break;
			case 7: 
				System.out.println("Enter CustomerIdTo");
				customerId=sc.nextInt();
				System.out.println("Enter Account numberTo");
				accountNo=sc.nextLong(); 
				System.out.println("Enter CustomerIdFrom");
				int customerId2=sc.nextInt();
				System.out.println("Enter Account numberFrom");
				long accountNo2=sc.nextLong(); 
				System.out.println("Enter the amount to be Transfered");
				amount=sc.nextFloat();
				System.out.println("Enter the Pin Number");
				pinNumber=sc.nextInt();
				System.out.println(bankingServices.fundTransfer(customerId, accountNo, customerId2, accountNo2, amount, pinNumber));
				break;
			case 8: System.out.println("View account details");
			System.out.println("Enter CustomerId");
			customerId=sc.nextInt();
			System.out.println("Enter Account number");
			accountNo=sc.nextLong(); 
			System.out.println(bankingServices.getAccountDetails(customerId, accountNo));
			break;
			case 9: System.out.println("View All Transaction details");
			System.out.println("Enter CustomerId");
			customerId=sc.nextInt();
			System.out.println("Enter Account number");
			accountNo=sc.nextLong();
			for ( Transaction transaction : bankingServices.getAccountAllTransaction(customerId, accountNo)) {
				System.out.println(transaction);
			}
			break;
			case 10: System.out.println("Viewing All Customer Details");
			for (Customer customer : bankingServices.getAllCustomerDetails()) {
				System.out.println(customer);
			}
			break;
			default: System.out.println("Enter the valid key ");
			break;
			}
		}
	}
}