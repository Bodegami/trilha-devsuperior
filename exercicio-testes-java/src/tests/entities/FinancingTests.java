package tests.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import entities.Financing;

public class FinancingTests {
	
	@Test
	public void financingShouldCreatedWhenValidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;

		Assertions.assertDoesNotThrow(() -> {
			new Financing(totalAmount, income, months);
		});

	}
	
	@Test
	public void financingShouldThrowExceptionWhenInvalidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 20;

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Financing(totalAmount, income, months);
		});
	}
	
	@Test
	public void setTotalAmountShouldUpdatedWhenValidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedTotalAmount = 90000.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertDoesNotThrow(() -> {
			financing.setTotalAmount(expectedTotalAmount);
		});

	}
		
	
	@Test
	public void setTotalAmountShouldThrowExceptionWhenInvalidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedTotalAmount = 1500000.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			financing.setTotalAmount(expectedTotalAmount);
		});
		
	}
	
	@Test
	public void setIncomeShouldUpdatedWhenValidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedIncome = 2500.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertDoesNotThrow(() -> {
			financing.setIncome(expectedIncome);
		});

	}
		
	
	@Test
	public void setIncomeShouldThrowExceptionWhenInvalidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedIncome = 1500.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			financing.setIncome(expectedIncome);
		});
		
	}
	
	@Test
	public void setMonthsShouldUpdatedWhenValidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		int expectedMonths = 90;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertDoesNotThrow(() -> {
			financing.setMonths(expectedMonths);
		});

	}
		
	
	@Test
	public void setMonthsShouldThrowExceptionWhenInvalidatedParameters() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		int expectedMonths = 60;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			financing.setMonths(expectedMonths);
		});
		
	}
	
	@Test
	public void financingShouldCorrectlyCalculateEntry() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedEntryResult = 20000.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		Double entryResult = financing.entry();
		
		Assertions.assertEquals(expectedEntryResult, entryResult);	
	}
	
	@Test
	public void financingShouldCorrectlyCalculateQuota() {
		
		double totalAmount = 100000.0;
		double income = 2000.0;
		int months = 80;
		double expectedQuotaResult = 1000.0;
		
		Financing financing = new Financing(totalAmount, income, months);
		
		Double quotaResult = financing.quota();
		
		Assertions.assertEquals(expectedQuotaResult, quotaResult);
		
	}


}
