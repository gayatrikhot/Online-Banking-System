package com.gayatri.presentation;

import java.util.Scanner;

import com.gayatri.dao.AccountantDao;
import com.gayatri.dao.AccountantDaoImpl;
import com.gayatri.dao.CustomerDao;
import com.gayatri.dao.CustomerDaoImpl;
import com.gayatri.entity.Accountant;
import com.gayatri.entity.Customer;
import com.gayatri.exception.AccountantException;
import com.gayatri.exception.CustomerException;

public class App {

	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		boolean f = true;
		while (f) {
			System.out.println("-------------Welcome To Online Banking System----------------");
			System.out.println("--------------------------------------------------------------");
			System.out.println("Login as:");
			System.out.println("1. Admin \n2. Customer");
			
			System.out.println("Enter your choice:");
			int choice = sc.nextInt();
			
			switch(choice) {
			case 1:
				System.out.println("------------Admin Login Credentials--------------");
				System.out.println("Enter username:");
				String userName= sc.next();
				System.out.println("Enter password:");
				String password= sc.next();
				
				AccountantDao ad= new AccountantDaoImpl();
				try {
					Accountant a = ad.LoginAccountant(userName, password);
					if(a==null) {
						System.out.println("Wrong Credential");
						break;
					}
					System.out.println("Login Successfully !!");
					System.out.println("Welcome "+ a.getAccountantUsername()+" !!");
					
					boolean y=true;
					while(y) {
						System.out.println("---------------------------------------\n1. Add New Customer\n2. Update Customer Details\n3. Delete Customer Account\n4. View Customer Details\n5. View All Customer Details\n6. Logout");
						System.out.println("Enter your choice:");
						int x= sc.nextInt();
						
						if(x==1) {
							System.out.println("\n------------New Account------------");
							System.out.println("Enter customerName: ");
							String a1= sc.next();
							System.out.println("Enter account opening balance: ");
							int a2= sc.nextInt();
							System.out.println("Enter customerMail: ");
							String a3= sc.next();
							System.out.println("Enter customerPassword: ");
							String a4= sc.next();
							System.out.println("Enter customerMobile: ");
							String a5= sc.next();
							System.out.println("Enter customerAddress: ");
							String a6= sc.next();
							
							int s1= -1;
							try{
								s1= ad.addCustomer(a1, a3, a4, a5, a6);
								
								try {
									ad.addAccount(a2, s1);
								}
								catch(CustomerException e) {
									e.printStackTrace();
								}
								
							}
							catch(Exception e) {
								System.out.println(e.getMessage());
							}
							
							System.out.println("------------------------------------------");
						}
						
						if(x==2) {
							System.out.println("----Update Customer Address----");
							System.out.println("Enter customer account number: ");
							int u= sc.nextInt();
							System.out.println("Enter New Address: ");
							String u2= sc.next();
							try {
								String mes = ad.updateCustomer(u, u2);
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
						
						if(x==3) {
							System.out.println("---Remove Customer Account---");
							System.out.println("Enter customer account number: ");
							int ac= sc.nextInt();
							String s= null;
							try {
								s= ad.deleteAccount(ac);
								
							}
							catch(CustomerException e) {
								e.printStackTrace();
							}
							if(s!=null) {
								System.out.println(s);
							}
							
						}
						
						if(x==4) {
							System.out.println("----View Customer Details----");
							System.out.println("Enter Customer Account Number: ");
							String ac= sc.next();
							
							try {
								Customer cus= ad.viewCustomer(ac);
								if(cus !=null) {
									System.out.println("------------------------------------------");
									System.out.println("Account Number: "+ cus.getCustomerAccountNumber());
									System.out.println("Customer Name: "+ cus.getCustomerName());
									System.out.println("Account Balance: "+ cus.getCustomerBalance());
									System.out.println("Customer Password: "+ cus.getCustomerPassword());
									System.out.println("Customer Mail: "+ cus.getCustomerMail());
									System.out.println("Customer Mobile Number: "+ cus.getCustomerMobile());
									System.out.println("Customer Address: "+ cus.getCustomerAddress());
									System.out.println("------------------------------------------");
								}
								else {
									System.out.println("Account Does not exist...");
									System.out.println("----------------------------");
								}
								
							}
							catch(CustomerException e){
								e.printStackTrace();
								
							}
						}
						
						if(x==5) {
							System.out.println("----All Customer Details----");
							try{
								Customer cus= ad.viewAllCustomer();
							}
							catch(CustomerException e) {
								e.printStackTrace();
							}
						}
						if(x==6) {
							System.out.println("Logged out successfully...");
							y=false;
						}
					}
					break;
					
				}
				catch(AccountantException e) {
					System.out.println(e.getMessage());
				}
				break;
				
				//Customer part
				
			case 2:
				System.out.println("<<-----------Customer Login-------------->>");
				System.out.println("-------------------------------------------");
				System.out.println("Enter User Name: ");
				String customerUsername= sc.next();
				System.out.println("Enter Password");
				String customerPassword= sc.next();
				System.out.println("Enter Account Number: ");
				int accountNumber= sc.nextInt();
				
				CustomerDao cd= new CustomerDaoImpl();
				
				try {
					Customer cus = cd.Login(customerUsername, customerPassword, accountNumber);
					System.out.println("Welcome "+ cus.getCustomerName());
					
					boolean n= true;
					while(n) {
						System.out.println("----------------------------------------------\n");
						System.out.println("1. View Balance\n2. Deposite Money\n3. Withdraw Money\n4. Transfer Money\n5. Logout ");
						
						int x= sc.nextInt();
						if(x==1) {
							System.out.println("------------Balance------------");
							System.out.println("Current Account Balance: "+cd.viewBalance(accountNumber));
							System.out.println("-------------------------------\n");
						}
						if(x==2) {
							System.out.println("-------Deposite Window--------");
							System.out.println("Enter Amount To Deposite: ");
							int am= sc.nextInt();
							cd.deposite(accountNumber, am);
							System.out.println("Your Balance After Deposite: "+ cd.viewBalance(accountNumber));
							System.out.println("------------------------------");
						}
						
						if(x==3) {
							System.out.println("-------Withdraw Window--------");
							System.out.println("Enter Amount To Withdraw: ");
							int am= sc.nextInt();
							try {
								cd.withdraw(accountNumber, am);
								System.out.println("Your Balance After Withdrawal: "+ cd.viewBalance(accountNumber));
								System.out.println("------------------------------");
							}
							catch(CustomerException e){
								System.out.println(e.getMessage());
							}
							
							
						}
						if(x==4) {
							System.out.println("--------Transfer Window----------");
							System.out.println("Enter Amount To Transfer: ");
							int t= sc.nextInt();
							System.out.println("Enter Account Number to Transfer Amount: ");
							int ac= sc.nextInt();
							
							try {
								cd.transfer(accountNumber,t, ac);
								System.out.println("Amount transfered successfully.");
								System.out.println("-----------------------------");
							}
							catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}
						
						//Logout
						if(x==5) {
							System.out.println("Customer Logout Successfully!!");
							System.out.println("Thank you for using banking services.....!!");
							n= false;
						}
					}
					break;
				}
				catch(CustomerException e) {
					System.out.println(e.getMessage()); 
				}
			}
		}
	}

}
