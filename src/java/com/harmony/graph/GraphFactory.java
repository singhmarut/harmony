package com.harmony.graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.springframework.web.client.support.RestGatewaySupport;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 01/05/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public  enum GraphFactory {
    INSTANCE;
    ConcurrentHashMap<String,GraphDatabaseService> graphMap = new ConcurrentHashMap<String, GraphDatabaseService>();
    //GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
    public GraphDatabaseService getGraph(long customerId){
        String graphUrl = String.format("/usr/local/harmony/data/%d/harmony.db",customerId);
        if (!graphMap.containsKey(customerId)){
            GraphDatabaseService graph =  new EmbeddedGraphDatabase(graphUrl);
            graphMap.put(Long.toString(customerId), graph);
        }
        return graphMap.get(Long.toString(customerId));
    }
}
