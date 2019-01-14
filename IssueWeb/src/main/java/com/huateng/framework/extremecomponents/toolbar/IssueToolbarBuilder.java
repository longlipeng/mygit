package com.huateng.framework.extremecomponents.toolbar;

import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.TableActions;
import org.extremecomponents.table.view.html.ToolbarBuilder;
import org.extremecomponents.util.HtmlBuilder;

import com.huateng.framework.extremecomponents.tableaction.IssueOnclickTableActions;
import com.huateng.framework.extremecomponents.tableaction.IssueTableActions;

public class IssueToolbarBuilder extends ToolbarBuilder {
    private static String pathString = "/IssueWeb";

    public IssueToolbarBuilder(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    public void rowsDisplayedDroplist() {
        int rowsDisplayed = getTableModel().getTableHandler().getTable().getRowsDisplayed();
        int medianRowsDisplayed = getTableModel().getTableHandler().getTable().getMedianRowsDisplayed();
        int maxRowsDisplayed = getTableModel().getTableHandler().getTable().getMaxRowsDisplayed();
        int currentRowsDisplayed = getTableModel().getLimit().getCurrentRowsDisplayed();

        String prefixWithTableId = getTableModel().getTableHandler().prefixWithTableId();
        // 显示页数的下拉框

        getHtmlBuilder().table(0).close().tr(4).close().td(8).style(" padding:0 0 0 4px;").close();

        int currentPage = getTableModel().getLimit().getPage();
        int totalRows = getTableModel().getLimit().getTotalRows();
        int totalPages = 1;

        if (totalRows % currentRowsDisplayed > 0) {
            totalPages = totalRows / currentRowsDisplayed + 1;
        } else {
            totalPages = totalRows / currentRowsDisplayed;
        }

        getHtmlBuilder().select().name(prefixWithTableId + TableConstants.PAGE_SCOPE);
        if (totalPages > 0) {
            // 覆盖css定义的75px宽度以容纳更长的页码
            getHtmlBuilder().style("width:auto;");
        }

        StringBuffer onchangePage = new StringBuffer();
        onchangePage.append(new IssueTableActions(getTableModel()).getPagesDisplayedAction());
        getHtmlBuilder().onchange(onchangePage.toString());

        getHtmlBuilder().close();

        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);

        // 20131126修改为上下至多10页下拉选择
        int startPage = currentPage < 6 || totalPages < 11 ? 1 : Math.min(currentPage - 4, totalPages - 9);
        int pageCounter = totalPages > 10 ? 10 : totalPages;
        // 20131127如果未显示第一页，则可以选择前10页
        if (startPage > 1) {
            // 前移10页
            getHtmlBuilder().option().value(String.valueOf(Math.max(1, currentPage - 10)));
            getHtmlBuilder().close();
            getHtmlBuilder().append("&lt;&lt;&lt;");
            getHtmlBuilder().optionEnd();
        }
        for (int i = startPage; i < startPage + pageCounter; i++) {
            getHtmlBuilder().option().value(String.valueOf(i));
            if (i == currentPage) {
                getHtmlBuilder().selected();
            }
            getHtmlBuilder().close();
            getHtmlBuilder().append("第" + String.valueOf(i) + "页");
            getHtmlBuilder().optionEnd();
        }
        // 20131127如果未显示最后一页，则可以选择后10页
        if (startPage + pageCounter - 1 < totalPages) {
            // 后移10页
            getHtmlBuilder().option().value(String.valueOf(Math.min(totalPages, currentPage + 10)));
            getHtmlBuilder().close();
            getHtmlBuilder().append("&gt;&gt;&gt;");
            getHtmlBuilder().optionEnd();
        }

        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);
        getHtmlBuilder().selectEnd();

        // getHtmlBuilder().br();
        // getHtmlBuilder().img("images/extremecomponents/rowsDisplayed.gif");
        // getHtmlBuilder().nbsp();

        // 输入页码
        getHtmlBuilder().tdEnd().td(8).style(" padding:0 0 0 4px;").close();
        //getHtmlBuilder().append("转到");
        getHtmlBuilder().input().name(prefixWithTableId + "aa");
        // 无记录禁用输入框
        if (totalPages < 1) {
            getHtmlBuilder().disabled();
        }
        getHtmlBuilder().style("text-align:center; width:50px;");
        getHtmlBuilder().onkeypress(
                "javascript:if(event.keyCode==13) document.getElementById('" + prefixWithTableId + "gobtn').click();");
        getHtmlBuilder().xclose();
        // 解决IE按回车键提交表单
        getHtmlBuilder().input().style("display:none").name(prefixWithTableId + "bb");
        getHtmlBuilder().xclose();
        getHtmlBuilder().input("button").id(prefixWithTableId + "gobtn").value("Go");
        // 无记录禁用Go按钮
        if (totalPages < 1) {
            getHtmlBuilder().disabled();
        }
        StringBuffer onClick = new StringBuffer();
        onClick.append(new IssueOnclickTableActions(getTableModel()).getPagesDisplayedAction(totalPages));
        getHtmlBuilder().onclick(onClick.toString());
        getHtmlBuilder().close();
        // getHtmlBuilder().tdEnd();

        // 显示条数的下拉框
        getHtmlBuilder().tdEnd().td(8).style(" padding:0 0 0 4px;").close();
        getHtmlBuilder().select().name(prefixWithTableId + TableConstants.ROWS_DISPLAYED);

        StringBuffer onchange = new StringBuffer();
        onchange.append(new TableActions(getTableModel()).getRowsDisplayedAction());
        getHtmlBuilder().onchange(onchange.toString());

        getHtmlBuilder().close();

        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);

        // default rows
        getHtmlBuilder().option().value(String.valueOf(rowsDisplayed));
        if (currentRowsDisplayed == rowsDisplayed) {
            getHtmlBuilder().selected();
        }
        getHtmlBuilder().close();
        getHtmlBuilder().append(String.valueOf(rowsDisplayed));
        getHtmlBuilder().optionEnd();

        // median rows
        getHtmlBuilder().option().value(String.valueOf(medianRowsDisplayed));
        if (currentRowsDisplayed == medianRowsDisplayed) {
            getHtmlBuilder().selected();
        }
        getHtmlBuilder().close();
        getHtmlBuilder().append(String.valueOf(medianRowsDisplayed));
        getHtmlBuilder().optionEnd();

        // max rows
        getHtmlBuilder().option().value(String.valueOf(maxRowsDisplayed));
        if (currentRowsDisplayed == maxRowsDisplayed) {
            getHtmlBuilder().selected();
        }
        getHtmlBuilder().close();
        getHtmlBuilder().append(String.valueOf(maxRowsDisplayed));
        getHtmlBuilder().optionEnd();

        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);
        getHtmlBuilder().selectEnd();

        getHtmlBuilder().tdEnd().trEnd(4);
        getHtmlBuilder().tr(4).close().td(8).close();
        getHtmlBuilder().img(pathString + "/images/extremecomponents/rowsDisplayed.gif");
        getHtmlBuilder().tdEnd().td(8).close();
        getHtmlBuilder().tdEnd().td(8).close();
        getHtmlBuilder().img(pathString + "/images/extremecomponents/table/rowsDisplayed.gif");
        getHtmlBuilder().tdEnd().trEnd(4).tableEnd(0);
    }
}
