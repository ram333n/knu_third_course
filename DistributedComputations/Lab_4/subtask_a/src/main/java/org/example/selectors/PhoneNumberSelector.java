package org.example.selectors;

import org.example.Database;

public class PhoneNumberSelector extends AbstractSelector {
    public PhoneNumberSelector(Database database, String queryParameter) {
        super(database, queryParameter);
    }

    @Override
    protected String select() {
        try {
            return database.getPhoneNumberByName(queryParameter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
