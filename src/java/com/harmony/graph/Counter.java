package com.harmony.graph;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: marut
 * Date: 30/03/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Document(collection = "Counter")
public class Counter{

    private String name;

    private long sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}