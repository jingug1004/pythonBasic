package com.beauty.admin.service;

import com.beauty.entity.DataTables;
import com.beauty.response.DataTablesResult;

public interface AdminPaymentService {

	public DataTablesResult list(DataTables input, String type);
	public DataTablesResult itemList(DataTables input, String paymentId);
}
