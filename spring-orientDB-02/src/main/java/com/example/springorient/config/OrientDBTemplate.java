package com.example.springorient.config;

import com.orientechnologies.orient.core.db.ODatabaseSession;

import java.util.function.Consumer;

import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;

public class OrientDBTemplate {

    private final ODatabaseSession db;

    public OrientDBTemplate(ODatabaseSession db) {
        this.db = db;
    }

    public void execute(Consumer<ODatabaseSession> action) {
        try {
            ODatabaseRecordThreadLocal.instance().set((ODatabaseDocumentInternal) db);
            action.accept(db);
        } finally {
            ODatabaseRecordThreadLocal.instance().remove();
        }
    }
}