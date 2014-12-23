package com.github.poserg.client;

import com.github.poserg.client.ds.FileUploadDS;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FileItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Entry point for test SmartGWT GWT module.
 */
public class Application implements EntryPoint {
    /**
     * This is called when the browser loads Application.html.
     */
    @Override
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(final Throwable t) {
                System.err.println("--- UNCAUGHT EXCEPTION ---");
                t.printStackTrace();
            }
        });

        HLayout hLayout = new HLayout();
        hLayout.setMargin(10);

        // *************** Add widgets to hLayout here. ****************
        final DynamicForm df = new DynamicForm();
        FileItem fileItem = new FileItem();

        ButtonItem button = new ButtonItem("Upload");
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (!df.hasErrors()) {
                    df.saveData(new DSCallback() {

                        @Override
                        public void execute(final DSResponse response, final Object rawData, final DSRequest request) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }
        });

        df.setItems(fileItem, button);
        df.setDataSource(FileUploadDS.getInstance());
        hLayout.addMember(df);

        hLayout.draw();
    }
}

