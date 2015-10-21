package com.cts.dao;

import java.sql.Timestamp;

public class InOutParam<T> {

	private T parameter;
	private boolean isOutputParam = false;
	private String name;

	public InOutParam(T parameter, String name) {
		this.parameter = parameter;
		this.name = name;
	}

	public InOutParam(T parameter, String name, boolean isOutputParam) {
		this.parameter = parameter;
		this.name = name;
		this.isOutputParam = isOutputParam;
	}

	public String getName() {
		return name;
	}

	public T getParameter() {
		return parameter;
	}

	public void setParameter(T parameter) {
		this.parameter = parameter;
	}

	public boolean isOutPutParam() {
		return isOutputParam;
	}

	public int getType() {
		if (parameter instanceof String) {
			return java.sql.Types.VARCHAR;
		} else if (parameter instanceof Integer) {
			return java.sql.Types.INTEGER;
		} else if (parameter instanceof Float) {
			return java.sql.Types.FLOAT;
		} else if (parameter instanceof Double) {
			return java.sql.Types.DOUBLE;
		} else if (parameter instanceof Boolean) {
			return java.sql.Types.BOOLEAN;
		} else if (parameter instanceof Timestamp) {
			return java.sql.Types.TIMESTAMP;
		} else {
			return 0;
		}
	}
}