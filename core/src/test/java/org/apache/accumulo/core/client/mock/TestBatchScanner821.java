package org.apache.accumulo.core.client.mock;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map.Entry;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.BatchWriterConfig;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.IteratorSetting;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.junit.Test;

public class TestBatchScanner821 {
  
  @Test
  public void test() throws Exception {
    MockInstance inst = new MockInstance();
    Connector conn = inst.getConnector("root", "");
    conn.tableOperations().create("test");
    BatchWriter bw = conn.createBatchWriter("test", new BatchWriterConfig());
    for (String row : "A,B,C,D".split(",")) {
      Mutation m = new Mutation(row);
      m.put("cf", "cq", "");
      bw.addMutation(m);
    }
    bw.flush();
    BatchScanner bs = conn.createBatchScanner("test", Constants.NO_AUTHS, 1);
    IteratorSetting cfg = new IteratorSetting(100, TransformIterator.class);
    bs.addScanIterator(cfg);
    bs.setRanges(Collections.singletonList(new Range("A", "Z")));
    StringBuilder sb = new StringBuilder();
    String comma = "";
    for (Entry<Key,Value>  entry : bs) {
      sb.append(comma);
      sb.append(entry.getKey().getRow());
      comma = ",";
    }
    assertEquals("a,b,c,d", sb.toString());
  }
  
}
