package com.gayatri.dao;

import com.gayatri.entity.Customer;
import com.gayatri.exception.CustomerException;

public interface CustomerDao {
	public Customer Login(String customerUsername, String customerPassword, int customerAccountNumber)throws CustomerException;
	public int viewBalance(int customerAccountNumber) throws CustomerException;
	public int deposite(int customerAccountNumber, int amount)throws CustomerException;
	public int withdraw(int customerAccountNumber, int amount)throws CustomerException;
	public int transfer(int customerAccountNumber, int amount, int customerAccountNumber2) throws CustomerException;
	
}
