package com.huateng.framework.extremecomponents.toolbar;

import java.util.Iterator;

import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.CompactToolbar;
import org.extremecomponents.table.view.html.BuilderConstants;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.ToolbarBuilder;
import org.extremecomponents.util.HtmlBuilder;

public class IssueToolbar extends CompactToolbar {

    public IssueToolbar(HtmlBuilder html, TableModel model) {
	super(html, model);
    }
    
    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);

        IssueToolbarBuilder toolbarBuilder = new IssueToolbarBuilder(html, model);

        html.td(4).styleClass(BuilderConstants.COMPACT_TOOLBAR_CSS).align("right").close();

        html.table(4).border("0").cellPadding("1").cellSpacing("2").close();
        html.tr(5).close();

        if (showPagination) {
            html.td(5).close();
            toolbarBuilder.firstPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.prevPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.nextPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.lastPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.separator();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.rowsDisplayedDroplist();
            html.tdEnd();

            if (showExports) {
                html.td(5).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }
        }

        if (showExports) {
            Iterator iterator = model.getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext();) {
                html.td(5).close();
                Export export = (Export) iter.next();
                toolbarBuilder.exportItemAsImage(export);
                html.tdEnd();
            }
        }

        if (filterable) {
            if (showExports || showPagination) {
                html.td(5).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }

            html.td(5).close();
            toolbarBuilder.filterItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.clearItemAsImage();
            html.tdEnd();
        }

        html.trEnd(5);

        html.tableEnd(4);

        html.tdEnd();
    }

}
