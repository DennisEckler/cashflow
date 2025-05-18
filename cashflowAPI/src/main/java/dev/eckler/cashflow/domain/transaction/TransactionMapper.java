package dev.eckler.cashflow.domain.transaction;

import java.util.List;
import java.util.Objects;

import dev.eckler.cashflow.domain.identifier.IdentifierMapper;
import dev.eckler.cashflow.openapi.model.TransactionResponse;

public class TransactionMapper {

    static List<TransactionResponse> transactionToTransactionResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = transactions.stream()
                .map(TransactionMapper::transactionToTransactionResponse)
                .toList();
        return transactionResponses;
    }

    static TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        Objects.requireNonNull(transaction, "transaction can't be null for mapping");
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setDate(transaction.getDate());
        transactionResponse.setSource(transaction.getSource());
        transactionResponse.setUserID(transaction.getUserID());
        transactionResponse.setPurpose(transaction.getPurpose());
        transactionResponse.setIdentifier(IdentifierMapper.identifierToIdentifierResponse(transaction.getIdentifier()));
        transactionResponse.setAmount(transaction.getAmount());
        return transactionResponse;
    }
}
