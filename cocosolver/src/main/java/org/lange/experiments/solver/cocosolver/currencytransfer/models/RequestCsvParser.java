package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.lange.experiments.solver.models.Currency;
import org.lange.experiments.solver.models.MonetaryAmount;
import org.lange.experiments.solver.models.TimeUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by lange on 26/11/16.
 */
public class RequestCsvParser {

    public enum Headers {
        requestId, requestorId, sourceCurrency, targetCurrency, amount, validFromDate, validToDate;
    }


    public static final Function<String, Optional<String>> TRIM = string -> Optional.ofNullable(string).map(StringUtils::trimToEmpty);
    public static final Function<String, Long> PARSER_TO_LONG = idString -> TRIM.apply(idString).map(StringUtils::trimToEmpty).map(trimmed -> StringUtils.defaultString(trimmed, null)).map(Long::parseLong).orElse(null);
    public static final Function<String, Optional<Currency>> PARSER_CURRENCY = currencyStr -> TRIM.apply(currencyStr).map(Currency::resolve).orElse(null);
    public static final BiFunction<Long, Currency, Optional<MonetaryAmount>> PARSER_AMOUNT =
            (amount, currency) ->
                    Optional.ofNullable(amount)
                            .map(amt -> Optional.ofNullable(currency).map(curr -> Pair.of(amount, currency)).orElse(null))
                            .map(pair -> amount * ((Double) Math.pow(10, currency.getMinorUnits())).longValue())
                            .map(amt -> MonetaryAmount.Builder.create().amount(amt).currency(currency).build().orElse(null));
    public static final Function<String, Optional<DateTime>> PARSER_DATE_TIME =
            dateStr -> TRIM.apply(dateStr)
                            .map(trimmed -> ISODateTimeFormat.dateTimeNoMillis().parseDateTime(trimmed))
                            .map(dt -> TimeUtil.toUtcDateTime
                                    .apply(dt.getYear())
                                    .apply(dt.getMonthOfYear())
                                    .apply(dt.getDayOfMonth())
                                    .apply(dt.getHourOfDay())
                                    .apply(dt.getMinuteOfHour()));

    public static List<TransferRequest> parse(Reader in) throws IOException {
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers.class).parse(in);
        return StreamSupport.stream(records.spliterator(), false)
                .map(record -> {
                    TransferRequest.Builder builder = TransferRequest.Builder.create();

                    Optional.ofNullable(record.get(Headers.requestId)).map(PARSER_TO_LONG).ifPresent(builder::requestId);
                    Optional.ofNullable(record.get(Headers.requestorId)).map(PARSER_TO_LONG).ifPresent(builder::requestorId);
                    Optional.ofNullable(record.get(Headers.targetCurrency)).map(PARSER_CURRENCY).orElse(Optional.empty()).ifPresent(builder::targetCurrency);

                    Optional<Currency> sourceCurrency = Optional.ofNullable(record.get(Headers.sourceCurrency)).map(PARSER_CURRENCY).orElse(Optional.empty());
                    Optional.ofNullable(record.get(Headers.amount)).map(PARSER_TO_LONG).map(amt -> sourceCurrency.map(currency -> PARSER_AMOUNT.apply(amt, currency).orElse(null)).orElse(null)).ifPresent(builder::monetaryAmount);
                    Optional.ofNullable(record.get(Headers.validFromDate)).map(PARSER_DATE_TIME).orElse(null).ifPresent(builder::validFrom);
                    Optional.ofNullable(record.get(Headers.validToDate)).map(PARSER_DATE_TIME).orElse(null).ifPresent(builder::validUntil);

                    return builder.build();
                }).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }
}
