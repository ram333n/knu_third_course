package org.example.selectors;

import org.example.Database;

public class NameSelector extends AbstractSelector {
    public NameSelector(Database database, String queryParameter) {
        super(database, queryParameter);
    }

    @Override
    protected String select() {
        try {
            return database.getNameByPhoneNumber(queryParameter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
