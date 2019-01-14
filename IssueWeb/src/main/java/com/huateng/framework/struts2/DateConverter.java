package com.huateng.framework.struts2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConverter extends DefaultTypeConverter {
	private Logger logger = Logger.getLogger(DateConverter.class);

	@SuppressWarnings("unchecked")
	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		try {
			if (toType.getName().equals("java.util.Date")) {
				String dateValue = ((String[]) value)[0];
				if (dateValue.trim().equals("")) {
					return null;
				}
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
				return dateFormat.parse(dateValue);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return super.convertValue(context, value, toType);
	}

}
