package com.huateng.framework.msg;

import java.util.ArrayList;
import java.util.List;

public class PacketDef {
	protected int length;
	protected List fields = new ArrayList();

	public PacketDef() {
	}

	public void addField(FieldDef fielddef) {
		fields.add(fielddef);
	}

	public void removeField(FieldDef fielddef) {
		fields.remove(fielddef);
	}

	public List getFields() {
		return fields;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setFields(List fields) {
		this.fields = fields;
	}
}
