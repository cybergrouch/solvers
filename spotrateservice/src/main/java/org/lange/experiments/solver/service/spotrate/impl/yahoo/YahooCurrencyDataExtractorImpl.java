package org.lange.experiments.solver.service.spotrate.impl.yahoo;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.json.JSONObject;
import org.lange.experiments.solver.service.spotrate.CurrencyDataExtractor;
import org.lange.experiments.solver.service.spotrate.impl.yahoo.model.QuoteJsonModel;
import org.lange.experiments.solver.service.spotrate.impl.yahoo.model.TransformUtils;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;
import org.lange.experiments.solver.service.spotrate.utils.JsonUtils;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lange on 28/11/16.
 */
public class YahooCurrencyDataExtractorImpl implements CurrencyDataExtractor {

    private YahooCurrencyDataExtractorImpl() {
        super();
    }

    public static Observable<List<SpotRateQuote>> request(String url) {

        Func1<? super byte[], Reader> toReader =
                byteArr -> Optional.ofNullable(byteArr).map(ByteArrayInputStream::new).map(InputStreamReader::new).orElse(null);

        Func0<CloseableHttpAsyncClient> resourceFactory = () -> {
            CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
            client.start();
            System.out.println(Thread.currentThread().getName() + " : Created and started the client.");
            return client;
        };

        Func1<HttpAsyncClient, Observable<List<SpotRateQuote>>> observableFactory = (client) -> {
            System.out.println(Thread.currentThread().getName() + " : About to create Observable.");
            return ObservableHttp.createGet(url, client)
                    .toObservable()
                    .flatMap(resp -> resp.getContent().map(toReader))
                    .retry(5)
                    .cast(Reader.class)
                    .map(rd -> {
                        try {
                            return IOUtils.toString(rd);
                        } catch (IOException e) {
                            return "";
                        }
                    })
                    .map(JSONObject::new)
                    .map(object -> object.getJSONObject("list"))
                    .map(object -> object.getJSONArray("resources"))
                    .map(resources -> {
                        List<QuoteJsonModel> objectList = new LinkedList<>();
                        Function<String, QuoteJsonModel> castor = JsonUtils.getReaderForClass(QuoteJsonModel.class);
                        for (int i = 0; i < resources.length(); ++i) {
                            JSONObject fields = resources.getJSONObject(i).getJSONObject("resource").getJSONObject("fields");
                            String jsonString = fields.toString();
                            QuoteJsonModel model = castor.apply(jsonString);
                            objectList.add(model);
                        }
                        return objectList;
                    })
                    .map(List::stream)
                    .map(stream ->
                            stream.map(TransformUtils.toSpotRateQuote)
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .collect(Collectors.toList()));
        };

        Action1<CloseableHttpAsyncClient> disposeAction = (client) -> {
            try {
                System.out.println(Thread.currentThread().getName() + " : Closing the client.");
                client.close();
            } catch (IOException e) {
                System.err.println("Error encountered");
            }
        };

        return Observable.using(resourceFactory, observableFactory, disposeAction);

    }

    @Override
    public Observable<List<SpotRateQuote>> extract() {
        return request("http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json");
    }

    public static class Holder {
        public static final YahooCurrencyDataExtractorImpl INSTANCE = new YahooCurrencyDataExtractorImpl();
    }

    public static void main(String... args) {
        YahooCurrencyDataExtractorImpl impl = YahooCurrencyDataExtractorImpl.Holder.INSTANCE;

        impl.extract().subscribe(new rx.Observer<List<SpotRateQuote>>() {
            @Override
            public void onCompleted() {
                System.out.println("Request completed.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error encountered");
            }

            @Override
            public void onNext(List<SpotRateQuote> spotRateQuotes) {
                System.out.println(String.format("Received %s spot rate quotes", spotRateQuotes.size()));
            }
        });
    }
}
