package org.springframework.samples.petclinic.proxy;

public class Store {
	Payment payment;

	// DI
	public Store(Payment payment) {
		this.payment = payment;
	}

	public void buySomething(int amount) {
		payment.pay(amount);
	}
}
