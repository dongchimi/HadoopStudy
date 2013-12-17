package org.dongchimi.hadoop.chapter05;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DelayCountMapperWithCounter extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	private String workType;
	private final static IntWritable outputValue = new IntWritable(1);
	
	private Text outputKey = new Text();
	
	@Override
	public void setup(Context context) {
		workType = context.getConfiguration().get("workType");
	}
	
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() <= 0) return;
		
		String[] columns = value.toString().split(",");
		if (columns == null || columns.length < 1) return;
		
		// 출발지연 데이터 출력
		if (workType.equals("departure")) {
			if (!columns[15].equals("NA")) {
				int depDelayTime = Integer.parseInt(columns[15]);
				if (depDelayTime > 0) {
					outputKey.set(columns[0] + "," + columns[1]);
					context.write(outputKey, outputValue);
				}
				else if (depDelayTime == 0) {
					context.getCounter(DelayCounters.scheduled_departure).increment(1);
				}
				else if (depDelayTime < 0) {
					context.getCounter(DelayCounters.early_departure).increment(1);
				}
			}
			else {
				context.getCounter(DelayCounters.not_available_departure).increment(1);
			}
		}
		// 도착 지연 데이터 출력
		else if (workType.equals("arrival")) {
			if (!columns[14].equals("NA")) {
				int arrDelayTime = Integer.parseInt(columns[14]);
				if (arrDelayTime > 0) {
					outputKey.set(columns[0] + "," + columns[1]);
					context.write(outputKey, outputValue);
				}
				else if (arrDelayTime == 0) {
					context.getCounter(DelayCounters.scheduled_arrival).increment(1);
				}
				else if (arrDelayTime < 0) {
					context.getCounter(DelayCounters.early_arrival).increment(1);
				}
			}
		}
		else {
			context.getCounter(DelayCounters.not_available_arrival).increment(1);
		}
	}
}
