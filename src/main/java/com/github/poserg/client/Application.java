package com.github.poserg.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

/**
 * Entry point for test SmartGWT GWT module.
 */
public class Application implements EntryPoint {

	public static final TreeNode[] employeeData = new TreeNode[] {
			new EmployeeTreeNode("4", "1", "Charles Madigen",
					"Chief Operating Officer", true),
			new EmployeeTreeNode("189", "4", "Gene Porter",
					"Mgr Tech Plng IntIS T", false),
			new EmployeeTreeNode("265", "189", "Olivier Doucet",
					"Asset Spec Lines Stns", false),
			new EmployeeTreeNode("264", "189", "Cheryl Pearson", "Dsl Sys Rep",
					false),
			new EmployeeTreeNode("188", "4", "Rogine Leger", "Mgr Syst P P",
					true) };

	public static class EmployeeTreeNode extends TreeNode {
		public EmployeeTreeNode(String employeeId, String reportsTo,
				String name, String job, boolean isOpen) {
			setAttribute("EmployeeId", employeeId);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("Job", job);
			setAttribute("isOpen", isOpen);
		}
	}

	/**
	 * This is called when the browser loads Application.html.
	 */
	public void onModuleLoad() {
		CheckboxItem ci = new CheckboxItem();

		final Tree employeeTree = new Tree();
		employeeTree.setModelType(TreeModelType.PARENT);
		employeeTree.setRootValue(1);
		employeeTree.setNameProperty("Name");
		employeeTree.setIdField("EmployeeId");
		employeeTree.setParentIdField("ReportsTo");
		employeeTree.setOpenProperty("isOpen");
		employeeTree.setData(employeeData);

		final TreeGrid employeeTreeGrid = new TreeGrid();
		employeeTreeGrid.setWidth(200);
		employeeTreeGrid.setHeight(240);
		employeeTreeGrid.setShowOpenIcons(false);
		employeeTreeGrid.setShowDropIcons(false);
		employeeTreeGrid.setClosedIconSuffix("");
		employeeTreeGrid.setData(employeeTree);
		employeeTreeGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		employeeTreeGrid.setShowSelectedStyle(false);
		employeeTreeGrid.setShowPartialSelection(true);
		employeeTreeGrid.setCascadeSelection(true);

		employeeTreeGrid.addDrawHandler(new DrawHandler() {
			public void onDraw(DrawEvent event) {
				employeeTreeGrid.getTree().openAll();
			}
		});

		employeeTreeGrid
				.addSelectionChangedHandler(new SelectionChangedHandler() {
					private static final String IS_CHECKED = "IS_CHECKED";

					@Override
					public void onSelectionChanged(SelectionEvent event) {
						ListGridRecord selectedRecord = event
								.getSelectedRecord();
						if (selectedRecord != null) {
							TreeNode record = (TreeNode) selectedRecord;
							String attribute = record.getAttribute(IS_CHECKED);
							if (attribute == null) {
								record.setAttribute(IS_CHECKED, false);
							}
							
							Boolean isChecked = record.getAttributeAsBoolean(IS_CHECKED);
							if (!isChecked) {
								record.setAttribute(IS_CHECKED, true);
								employeeTree.openAll(record);
							}
						}
					}
				});

		DynamicForm df = new DynamicForm();

		final CheckboxItem partialSelection = new CheckboxItem("partialSelect",
				"Allow Partial Selection");
		partialSelection.setDefaultValue(true);
		partialSelection.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				boolean selected = partialSelection.getValueAsBoolean();
				employeeTreeGrid.setShowPartialSelection(!selected);
				employeeTreeGrid.redraw();
			}
		});

		df.setFields(partialSelection);

		HLayout layout = new HLayout(20);
		layout.addMember(employeeTreeGrid);
		layout.addMember(df);

		layout.draw();
	}
}
