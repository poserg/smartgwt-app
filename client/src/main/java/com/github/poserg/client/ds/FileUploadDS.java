package com.github.poserg.client.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;

public class FileUploadDS extends DataSource {

    private static final String ID_NAME = "FileUploadDS";
    private static FileUploadDS instance = null;

    public static FileUploadDS getInstance() {
        if (instance == null) {
            instance = new FileUploadDS(ID_NAME);
        }
        return instance;
    }

    protected FileUploadDS(final String id) {
        setID(id);

        OperationBinding add = new OperationBinding();
        add.setOperationType(DSOperationType.ADD);
        add.setDataProtocol(DSProtocol.POSTXML);
        add.setDataURL("/my_url");

        setOperationBindings(add);
    }
}
