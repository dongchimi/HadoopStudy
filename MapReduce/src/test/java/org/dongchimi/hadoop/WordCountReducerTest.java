package org.dongchimi.hadoop;

import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.junit.Test;

public class WordCountReducerTest {
	
	@Test
	public void testReducer() {
		ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver = new ReduceDriver();
		reduceDriver.withReducer(new WordCountReducer());
		reduceDriver.withInputKey(new Text("World"));
		reduceDriver.withInputValues(Arrays.asList(new IntWritable(1), new IntWritable(1)));
		
		reduceDriver.withOutput(new Text("World"), new IntWritable(2));
		reduceDriver.runTest();
	}
}
