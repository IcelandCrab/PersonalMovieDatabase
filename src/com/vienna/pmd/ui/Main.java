package com.vienna.pmd.ui;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thoughtworks.xstream.XStream;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Injector injector = Guice.createInjector();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("javafx/sample.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return injector.getInstance(param);
            }
        });
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            /*
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder().setScheme("http")
                    .setHost("www.omdbapi.com")
                    .setPath("/")
                    .setParameter("i", "tt0093773")
                    //.setParameter("s", "House of Cards")
                    .setParameter("r", "XML");

            HttpGet get = new HttpGet(uriBuilder.build());

            System.out.println(get.getURI());

            CloseableHttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();

            System.out.println(EntityUtils.toString(entity));

            /*
            XStream stream = new XStream();
            stream.processAnnotations(new Class[] {Result.class, Root.class});
            Root root = (Root) stream.fromXML(entity.getContent());
            System.out.println("found total results: " + root.getResults());
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
