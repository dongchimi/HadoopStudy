package org.dongchimi.hadoop.chapter05;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ArrivalDelayCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	private final static IntWritable outputValue = new IntWritable(1);
	
	private Text outputKey = new Text();
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() > 0) {
			String[] columns = value.toString().split(",");
			if (columns != null && columns.length > 0) {
				outputKey.set(columns[0] + ", " + columns[1]);
				if (!columns[14].equals("NA")) {
					int arrDelayTime = Integer.parseInt(columns[14]);
					if (arrDelayTime > 0) {
						context.write(outputKey, outputValue);
					}
				}
			}
		}
	}
}
