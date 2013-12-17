package org.dongchimi.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.junit.Test;

public class WordCountMapperTest {

	@Test
	public void testMap() {
		Text value = new Text("Hello World Bye World");
		
		MapDriver<LongWritable, Text, Text, IntWritable> mapDriver = new MapDriver();
		mapDriver.withMapper(new WordCountMapper());
		mapDriver.withInputValue(value);
		
		mapDriver.withOutput(new Text("Hello"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.withOutput(new Text("Bye"), new IntWritable(1));
		mapDriver.withOutput(new Text("World"), new IntWritable(1));
		mapDriver.runTest();
	}
}
