package org.lange.experiments.solver.service.spotrate;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.json.JSONObject;
import rx.Observable;
import rx.Observer;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lange on 26/11/16.
 */
public class Example {

    @SuppressWarnings({"rawtypes","unchecked"})
    private static Observable<JSONObject> requestJson(HttpAsyncClient client, String url) {
        return ObservableHttp.createGet(url, client).toObservable()
                .flatMap(resp -> resp.getContent().map(String::new))
                .retry(5)
                .cast(String.class)
                .map(String::trim)
                .map(JSONObject::new)
                .map(jsonObject -> jsonObject.getJSONObject("list"));
//                .map(JSONObject::getJSONArray)
    }

    public static void main(String[] args) {

            try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
                client.start();

                Observer<? super JSONObject> observer = new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        System.out.println("Received JSONObject : " + jsonObject.toString());

                    }
                };

//            String userDirectory = System.getProperty("user.dir");
//            String resourcePath = String.format("%s/%s/%s", userDirectory, "spotrateservice/src/test/resources/json", "yahoo_finance.json");
//
//            String content = new String(Files.readAllBytes(Paths.get(resourcePath)));
//            Observable<String> myObservable =
//                    Observable.just(content);
//
//            myObservable.map(JSONObject::new).subscribe(observer);

                requestJson(client, "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json").subscribe(observer);

                while (true) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
