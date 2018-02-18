package com.revolut.tests.zkiss.transfersvc.usecase;

import com.revolut.tests.zkiss.transfersvc.domain.Account;
import com.revolut.tests.zkiss.transfersvc.domain.TransferRequest;
import com.revolut.tests.zkiss.transfersvc.domain.TransferResult;
import com.revolut.tests.zkiss.transfersvc.persistence.AccountRepo;
import com.revolut.tests.zkiss.transfersvc.persistence.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.skife.jdbi.v2.DBI;

@Slf4j
public class Transfer {
    private final TransferRequest request;
    private final DBI dbi;

    public Transfer(TransferRequest request, DBI dbi) {
        this.request = request;
        this.dbi = dbi;
    }

    public TransferResult run() {
        return dbi.inTransaction(((handle, status) -> {
            AccountRepo accountRepo = handle.attach(AccountRepo.class);
            TransactionRepo txRepo = handle.attach(TransactionRepo.class);

            Account from = accountRepo.find(request.getFrom().getSortCode(), request.getFrom().getAccountNumber());

            // TODO
            return new TransferResult(false);
        }));
    }
}