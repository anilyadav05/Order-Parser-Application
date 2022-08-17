package com.unlimint.orderparser.entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private int Id;
	private long orderId;
	private Double amount;
	private String currency;
	private String comment;
	private String fileName;
	private int line;
	private HttpStatus result;

}
