package com.gayatri.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gayatri.databaseconnection.DatabaseConnection;
import com.gayatri.entity.Accountant;
import com.gayatri.entity.Customer;
import com.gayatri.exception.AccountantException;
import com.gayatri.exception.CustomerException;

public class AccountantDaoImpl implements AccountantDao{

	@Override
	public Accountant LoginAccountant(String accountantUsername, String accountantPassword) throws AccountantException{
		Accountant acc= null;
		
		try(Connection conn = DatabaseConnection.provideConnection()){
			PreparedStatement ps= conn.prepareStatement("Select * from accountant where accountantUsername=? and  accountantPassword=?");
			
			ps.setString(1, accountantUsername);
			ps.setString(2, accountantPassword);
			
			ResultSet rs= ps.executeQuery();
			
			if(rs.next()) {
				String n= rs.getString("accountantUsername");
				String e= rs.getString("accountantEmail");
				String p= rs.getString("accountantPassword");
				
				acc = new Accountant(n,e,p);
			}
		}
		catch(SQLException e) {
			throw new AccountantException("Invalid Username or password");
			
		}
		return acc;
	}

	@Override
	public int addCustomer(String customerName, String customerMail, String customerPassword, String customerMobile, String customerAddress) throws CustomerException {
	    
	    int cid = -1;
	    try (Connection conn = DatabaseConnection.provideConnection()) {
	        PreparedStatement ps = conn.prepareStatement(
	            "INSERT INTO customerinformation (customerName, customerMail, customerPassword, customerMobile, customerAddress) VALUES (?, ?, ?, ?, ?)",
	            Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, customerName);
	        ps.setString(2, customerMail);
	        ps.setString(3, customerPassword);
	        ps.setString(4, customerMobile);
	        ps.setString(5, customerAddress);
	        
	        int x = ps.executeUpdate();
	        
	        if (x > 0) {
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                cid = rs.getInt(1); // Get the generated customer ID correctly
	            } else {
	                throw new SQLException("No ID obtained.");
	            }
	            rs.close();
	        } else {
	            System.out.println("Failed to insert customer information, please try again");
	        }
	        
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace for debugging
	        throw new CustomerException("Error adding customer: " + e.getMessage());
	    }
	    return cid;
	}
	@Override
	public String addAccount(int customerBalance, int cid) throws CustomerException {
	    String message = null;
	    
	    try (Connection conn = DatabaseConnection.provideConnection()) {
	        // Check if customer ID is valid
	        if (cid <= 0) {
	            throw new CustomerException("Invalid customer ID: " + cid);
	        }

	        PreparedStatement ps = conn.prepareStatement("INSERT INTO account (customerBalance, cid) VALUES (?, ?)");
	        ps.setInt(1, customerBalance);
	        ps.setInt(2, cid);
	        
	        int x = ps.executeUpdate();
	        if (x > 0) {
	            message = "Account inserted successfully";
	            System.out.println(message);
	        } else {
	            message = "Failed to insert account";
	            System.out.println(message);
	        }
	        
	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace for debugging
	        throw new CustomerException("Error adding account: " + e.getMessage());
	    }
	    return message;
	}

	@Override
	public String updateCustomer(int customerAccountNumber, String customerAddress) throws CustomerException {
		String message= null;
		
		try (Connection conn= DatabaseConnection.provideConnection()){
			PreparedStatement ps= conn.prepareStatement("update customerinformation i inner join account a on i.cid=a.cid and a.customerAccountNumber=? set i.customerAddress=?");
			ps.setInt(1, customerAccountNumber);
			ps.setString(2,customerAddress);
			
			int x= ps.executeUpdate();
			if(x>0) {
				System.out.println("Address updated successfully...");
				
			}
			else {
				System.out.println("Updation not successful...");
				System.out.println("----------------------------------");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			message= e.getMessage();
			
		}
		return message;
	}

	@Override
	public String deleteAccount(int customerAccountNumber) throws CustomerException {
		String message= null;
		try(Connection conn= DatabaseConnection.provideConnection()){
			PreparedStatement ps= conn.prepareStatement("delete i from customerinformation i inner join account a on i.cid= a.cid where a.customerAccountNumber=?");
			ps.setInt(1, customerAccountNumber);
			int x= ps.executeUpdate();
			
			if(x>0) {
				System.out.println("Account Deleted Successfully...");
			}
			else {
				System.out.println("Account Deletion Faild...");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			message= e.getMessage();
		}
		return message;
	}

	@Override
	public Customer viewCustomer(String customerAccountNumber) throws CustomerException {
		Customer cu= null;
		try(Connection conn= DatabaseConnection.provideConnection()){
			PreparedStatement ps= conn.prepareStatement("select * from customerinformation i inner join account a on a.cid= i.cid where customerAccountNumber=? ");
			ps.setString(1, customerAccountNumber);
			
			ResultSet rs= ps.executeQuery();
			
			if(rs.next()) {
				int a= rs.getInt("customerAccountNumber");
				String n= rs.getString("customerName");
				int b= rs.getInt("customerBalance");
				String e= rs.getString("customerPassword");
				String p= rs.getString("customerMail");
				String m= rs.getString("customerMobile");
				String ad= rs.getString("customerAddress");
				
				cu= new Customer(a,n,b,e,p,m,ad);
			}
			else {
				throw new CustomerException("Invalid Account Number....");
			}
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return cu;
	}

	@Override
	public Customer viewAllCustomer() throws CustomerException {
		Customer cu= null;
		try (Connection conn= DatabaseConnection.provideConnection()){
			PreparedStatement ps= conn.prepareStatement("select * from customerinformation i inner join account a on a.cid= i.cid ");
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				int a= rs.getInt("customerAccountNumber");
				String n= rs.getString("customerName");
				int b= rs.getInt("customerBalance");
				String e= rs.getString("customerPassword");
				String p= rs.getString("customerMail");
				String m= rs.getString("customerMobile");
				String ad= rs.getString("customerAddress");
				
				System.out.println("-------------------------------");
				System.out.println("Account Number: "+ a);
				System.out.println("Customer Name: "+n);
				System.out.println("Customer Balance: ");
				System.out.println("Customer Password: "+ e);
				System.out.println("Cusomter Mail: "+ p);
				System.out.println("Cusomter Mobile Number: "+ m);
				System.out.println("Customer Address: "+ ad);
				System.out.println("-------------------------------");
				
				cu= new Customer(a,n,b,e,p,m,ad);
			}
			
		}
		catch(SQLException e) {
			throw new CustomerException("Invalid Account Number!!!");
		}
		return cu;
	}

	
	

}
