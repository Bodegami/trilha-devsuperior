package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Account;

public class AccountTests {
	
	@Test
	public void depositShouldIncreaseBalanceAndDiscountFeeWhenPositiveAmount() {
		//Pattern AAA
		
		//ARRANGE
		double amount = 200.0;
		double expectedValue = 196.0;
		Account acc = new Account(1L, 0.0);
		
		//ACT
		acc.deposit(amount);
		
		
		//ASSERT
		Assertions.assertEquals(expectedValue, acc.getBalance());	
	}
	
	@Test
	public void depositShouldDoNothingNegativeAmount() {


		double expectedValue = 100.0;
		Account acc = new Account(1L, expectedValue);
		double amount = -200.0;
		
		acc.deposit(amount);
		
		Assertions.assertEquals(expectedValue, acc.getBalance());	
	}

}
