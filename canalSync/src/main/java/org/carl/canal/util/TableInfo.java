package org.carl.canal.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TableInfo implements Serializable{
private String name,value;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}
}
