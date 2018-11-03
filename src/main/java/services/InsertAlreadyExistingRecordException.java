package services;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

public class InsertAlreadyExistingRecordException  extends PSQLException {

    public InsertAlreadyExistingRecordException(String msg, PSQLState state) {
        super(msg, state);
    }
}
