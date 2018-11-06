package com.huateng.framework.extremecomponents.view;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.ExportViewUtils;
import org.extremecomponents.table.view.View;

public class TextView implements View{
	public final static String DELIMITER = "delimiter";
    final static String DEFAULT_DELIMITER = "\t";
    private StringBuffer plainData = new StringBuffer();

    public void beforeBody(TableModel model) {
    }

    public void body(TableModel model, Column column) {
        Export export = model.getExportHandler().getCurrentExport();
        String delimiter = export.getAttributeAsString(DELIMITER);
        if (StringUtils.isBlank(delimiter)) {
            delimiter = DEFAULT_DELIMITER;
        }

        String value = ExportViewUtils.parseCSV(column.getCellDisplay());

        plainData.append(value.trim());
        plainData.append(delimiter);
        if (column.isLastColumn()) {
            plainData.append("\r\n");
        }
    }

    public Object afterBody(TableModel model) {
        return plainData.toString();
    }
}
