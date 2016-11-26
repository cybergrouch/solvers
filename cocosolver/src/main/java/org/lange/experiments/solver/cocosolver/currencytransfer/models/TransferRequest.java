package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 25/11/16.
 */
public class TransferRequest {
    private MonetaryAmount amount;
    private Currency targetCurrency;
    private DateTime validFrom;
    private DateTime validUntil;
    private Long requestorId;
    private Long requestId;

    private TransferRequest() {
        super();
    }

    public void setAmount(MonetaryAmount amount) {
        this.amount = amount;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public DateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(DateTime validFrom) {
        this.validFrom = validFrom;
    }

    public DateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(DateTime validUntil) {
        this.validUntil = validUntil;
    }

    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }

    public Long getRequestorId() {
        return requestorId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public static class Builder implements ModelBuilder<TransferRequest> {
        TransferRequest object = new TransferRequest();

        private Builder() {
            super();
        }

        public Builder monetaryAmount(MonetaryAmount amount) {
            Optional.ofNullable(amount).ifPresent(object::setAmount);
            return this;
        }

        public Builder targetCurrency(Currency targetCurrency) {
            Optional.ofNullable(targetCurrency).ifPresent(object::setTargetCurrency);
            return this;
        }

        public Builder validFrom(DateTime dateTime) {
            Optional.ofNullable(dateTime).ifPresent(object::setValidFrom);
            return this;
        }

        public Builder validUntil(DateTime dateTime) {
            Optional.ofNullable(dateTime).ifPresent(object::setValidUntil);
            return this;
        }

        public Builder requestorId(Long requestorId) {
            Optional.ofNullable(requestorId).ifPresent(object::setRequestorId);
            return this;
        }

        public Builder requestId(Long requestId) {
            Optional.ofNullable(requestId).ifPresent(object::setRequestId);
            return this;
        }

        @Override
        public Stream<Function<TransferRequest, ?>> getFieldExtractors() {
            return Stream.of(
                    TransferRequest::getAmount,
                    TransferRequest::getTargetCurrency,
                    TransferRequest::getValidFrom,
                    TransferRequest::getValidUntil,
                    TransferRequest::getRequestorId,
                    TransferRequest::getRequestId);
        }

        @Override
        public TransferRequest getBaseObject() {
            return object;
        }

        public static Builder create() {
            return new Builder();
        }
    }
}
