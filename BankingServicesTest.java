package com.cg.banking.test;



import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Address;
import com.cg.banking.beans.Customer;
import com.cg.banking.daoservices.BankingDAOServicesImpl;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.CustomerNotFoundException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;
import com.cg.banking.services.BankingServicesImpl;
import com.cg.banking.utility.BankingUtility;




public class BankingServicesTest {
	public static BankingServicesImpl bankingservices; 
	@BeforeClass
	public static void setUpTestEnv() {
		bankingservices=new BankingServicesImpl();
		

	}
	@Before
	public  void setUpMockData() {
		//BankingUtility.CUSTOMER_ID_COUNTER=100;
		Customer customer=new Customer(BankingUtility.CUSTOMER_ID_COUNTER++,"shyam", "prasd", "Sdad", "asd45", new Address(456, "asd", "we"), new Address(789, "qwe", "ts"));
		//customer.setCustomerId(BankingUtility.CUSTOMER_ID_COUNTER++);
		Customer customer1=new Customer(BankingUtility.CUSTOMER_ID_COUNTER++,"shyam", "prasd", "Sdad", "asd45", new Address(456, "asd", "we"), new Address(789, "qwe", "ts"));
		//customer1.setCustomerId(BankingUtility.CUSTOMER_ID_COUNTER++);
		BankingDAOServicesImpl.customers.put(customer.getCustomerId(), customer);
		BankingDAOServicesImpl.customers.put(customer1.getCustomerId(), customer1);
		
		
		//BankingUtility.ACCOUNT_ID_COUNTER=111;
		Account account=new Account("savings", 1000,BankingUtility.ACCOUNT_ID_COUNTER++);
		account.setStatus("active");
		account.setPinNumber(1234);
		Account account1=new Account("current", 2000, BankingUtility.ACCOUNT_ID_COUNTER++);
		account1.setStatus("active");
		account1.setPinNumber(2345);
		BankingDAOServicesImpl.customers.get(customer.getCustomerId()).getAccounts().put(account.getAccountNo(), account);
		BankingDAOServicesImpl.customers.get(customer1.getCustomerId()).getAccounts().put(account1.getAccountNo(), account1);


	}
	
	@Test
	public void testAcceptCustomerDetails() throws BankingServicesDownException {
		int actual= bankingservices.acceptCustomerDetails("asd", "asd", "dfed", "asd", "qwe", "wfe", 4564, "asd", "asdas", 789);
		assertEquals(102, actual);
	}
	
	@Test
	public void testValidCustomerDetails() throws CustomerNotFoundException, BankingServicesDownException {
		Customer customer1=new Customer(101,"shyam", "prasd", "Sdad", "asd45", new Address(456, "asd", "we"), new Address(789, "qwe", "ts"));
		BankingDAOServicesImpl.customers.put(customer1.getCustomerId(), customer1);
		Account account1=new Account("current", 2000, 112);
		BankingDAOServicesImpl.customers.get(customer1.getCustomerId()).getAccounts().put(account1.getAccountNo(), account1);
		assertEquals(bankingservices.getCustomerDetails(101), customer1);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInvalidCustomerDetails() throws CustomerNotFoundException,BankingServicesDownException{
		bankingservices.getCustomerDetails(452);
	}
	
	@Test
	public void testOpenAccount() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException, AccountNotFoundException {
		long actual=bankingservices.openAccount(101, "savings", 1000);
		assertEquals(113, actual);
	}
	
	@Test
	public void testValidCustomerOpenAcoount() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException{
		bankingservices.openAccount(100, "savings", 1000);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInvalidCustomerOpenAcoount() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException{
		bankingservices.openAccount(105, "savings", 1000);
	}
		
	@Test
	public void testValidOpenAccount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException {
		bankingservices.getAccountDetails(100, 111);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testInvalidOpenAccount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException {
		assertEquals(bankingservices.getAccountDetails(100, 113), new Account("savings", 1000,BankingUtility.ACCOUNT_ID_COUNTER));
	}
	
	@Test
	public void testValidOpenAccountStatus() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException {
		bankingservices.openAccount(100, "savings", 1000);
	}
		
	@Test(expected=InvalidAccountTypeException.class)
	public void testInvalidOpenAccountStatus() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException {
		bankingservices.openAccount(100, "savingsss", 2000);
	}
	
	@Test
	public void testValidOpenAccountAmount() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException {
		bankingservices.openAccount(100, "current", 1000);
	}
	
	@Test(expected=InvalidAmountException.class)
	public void testInvalidOpenAccountAmount() throws InvalidAmountException, CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException {
		bankingservices.openAccount(100, "savings", -100);
	}
	//Deposit
	@Test
	public void testValidDepositAmount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		//assertEquals((int)bankingservices.depositAmount(100, 111, 100), 1100 );
		bankingservices.depositAmount(100, 111, 100);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInvalidCustomerDepositAmount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.depositAmount(105, 112, 1000);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testInValidAccountFoundDepositAmount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		//assertEquals((int)bankingservices.depositAmount(100, 112, 100),1100);
		bankingservices.depositAmount(100, 112, 100);
	}
	
	@Test(expected=AccountBlockedException.class)
	public void testInValidAccountStatus() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException, AccountBlockedException, InvalidAmountException{
		BankingDAOServicesImpl.customers.get(100).getAccounts().get((long)111).setStatus("blocked");
		bankingservices.depositAmount(100, 111, 100);
	}
	
	@Test(expected=InvalidAmountException.class)
	public void testInValidAmountDepositAmount() throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.depositAmount(100, 111, -100);
	}
	
	@Test 
	public void testValidWithdrawAmount() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(100, 111, 100, 1234);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInValidCustomerWithdraw() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(104, 111, 100, 1234);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testInValidAccountWithdraw() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(100, 112, 100, 1234);
	}
	
	@Test(expected=AccountBlockedException.class)
	public void testInvalidAccountStatus() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException{
		BankingDAOServicesImpl.customers.get(100).getAccounts().get((long)111).setStatus("blocked");
		bankingservices.withdrawAmount(100, 111, 100, 1234);
	}
	
	@Test(expected=InsufficientAmountException.class)
	public void testInvalidInsufficientAmountWithdraw() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(100, 111, 2000, 1234);
	}
	
	@Test(expected=InvalidPinNumberException.class)
	public void testInvalidPinNumberWithdraw() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(100, 111, 100, 0000);
	}
	
	@Test(expected=InvalidAmountException.class)
	public void testInvalidAmountWithdraw() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.withdrawAmount(100, 111, -100, 1234);
	}
	
	@Test()
	public void testValidFundTransfer() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		assertEquals(bankingservices.fundTransfer(101, 112, 100, 111, 100, 1234), true);
		//bankingservices.fundTransfer(101, 112, 100, 111, 100, 1234);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInvalidCustomerToFundTransfer() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.fundTransfer(102, 111, 100, 111, 100, 1234);
	}
	
	@Test(expected=CustomerNotFoundException.class)
	public void testInvalidCustomerFromFundTransfer() throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException, InvalidAmountException {
		bankingservices.fundTransfer(101, 111, 102, 112, 100, 1234);
	}
	
	
	@After
	public  void tearDownMockdata() throws BankingServicesDownException {
	bankingservices.getAllCustomerDetails().clear();
	BankingUtility.CUSTOMER_ID_COUNTER=100;
	BankingUtility.ACCOUNT_ID_COUNTER=111;
	}
	@AfterClass
	public static void tearDownTestEnv() {
		bankingservices=null;
	}
}
