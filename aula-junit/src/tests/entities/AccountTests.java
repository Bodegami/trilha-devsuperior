package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Account;

public class AccountTests {
	
	@Test
	public void depositShouldIncreaseBalanceWhenPositiveAmount() {
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

}
