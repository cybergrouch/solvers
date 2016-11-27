package org.lange.experiments.solver.service.spotrate.impl.model.yahoo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.lange.experiments.solver.models.TimeUtil;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by lange on 27/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteJsonModel {

    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal price;
    private final DateTime quoteTime;

    @JsonCreator
    public QuoteJsonModel(
            @JsonProperty("name") String name,
            @JsonProperty("price") String price,
            @JsonProperty("utctime") String quoteTime) {
        super();
        String[] split = StringUtils.split(name, '/');
        this.fromCurrency = split[0];
        this.toCurrency = split[1];
        this.price = new BigDecimal(price);
        this.quoteTime = Optional.ofNullable(ISODateTimeFormat.dateTimeParser().parseDateTime(quoteTime)).map(TimeUtil.adjustToUtc).orElse(null);
    }

    public DateTime getQuoteTime() {
        return quoteTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }
}
