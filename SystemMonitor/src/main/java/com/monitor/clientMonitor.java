
package com.monitor;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class clientMonitor {

    TransportClient client = null;
    final String indexName = "monitor_log";

    public Map<String, Object> putJsonDocument(String title, String content, Date postDate,
                                                      String[] tags, String author)
    {
        Map<String, Object> jsonDocument = new HashMap<String, Object>();
        jsonDocument.put("title", title);
        jsonDocument.put("conten", content);
        jsonDocument.put("postDate", postDate);
        jsonDocument.put("tags", tags);
        jsonDocument.put("author", author);
        return jsonDocument;
    }

    public void connect() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }

    public void disConnect() {
        client.close();
    }

    public void setUp() throws UnknownHostException
    {
        connect();
        createIndex();
    }

    public void createIndex() {
        try {
            internalCreateIndex();
        } catch(Exception e) {
            //throw new CreateIndexFailedException(indexName, e);
            System.out.println(e);
        }
    }

    private CreateIndexResponse internalCreateIndex() throws IOException {
        final CreateIndexRequestBuilder createIndexRequestBuilder = client
                .admin() // Get the Admin interface...
                .indices() // Get the Indices interface...
                .prepareCreate(indexName); // We want to create a new index ....
        final CreateIndexResponse indexResponse = createIndexRequestBuilder.execute().actionGet();
        return indexResponse;
    }

    public void insertData(HashMap<String, Object> jsonDocument, String indexType) throws IOException {
        IndexResponse response = client.prepareIndex(indexName, indexType)
                .setSource(jsonDocument
                )
                .get();
        System.out.println(" DONE");
    }

    /*
    public static void main(String[] args) throws Exception {
        System.out.println(" started");
        clientMonitor cm = new clientMonitor();
        cm.setUp();
        //cm.insertData();
    }*/


}
