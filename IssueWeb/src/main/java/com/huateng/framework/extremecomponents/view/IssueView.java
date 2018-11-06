package com.huateng.framework.extremecomponents.view;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.CompactView;
import org.extremecomponents.util.HtmlBuilder;

import com.huateng.framework.extremecomponents.toolbar.IssueToolbar;

public class IssueView extends CompactView {

    protected void toolbar(HtmlBuilder html, TableModel model) {
        new IssueToolbar(html, model).layout();
    }
}
