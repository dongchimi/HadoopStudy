package org.dongchimi.hadoop.chapter05;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DelayCountMapperWithMultipleOutputs extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	private static final IntWritable outputValue = new IntWritable(1);

	private Text outputKey = new Text();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		if (key.get() <= 0)
			return;

		String[] columns = value.toString().split(",");
		if (columns == null || columns.length < 1)
			return;

		// 출발지연 데이터 출력
		if (!columns[15].equals("NA")) {
			int depDelayTime = Integer.parseInt(columns[15]);
			if (depDelayTime > 0) {
				outputKey.set("D," + columns[0] + "," + columns[1]);
				context.write(outputKey, outputValue);
			} else if (depDelayTime == 0) {
				context.getCounter(DelayCounters.scheduled_departure)
						.increment(1);
			} else if (depDelayTime < 0) {
				context.getCounter(DelayCounters.early_departure).increment(1);
			}
		} else {
			context.getCounter(DelayCounters.not_available_departure)
					.increment(1);
		}
		// 도착 지연 데이터 출력
		if (!columns[14].equals("NA")) {
			int arrDelayTime = Integer.parseInt(columns[14]);
			if (arrDelayTime > 0) {
				outputKey.set("A," + columns[0] + "," + columns[1]);
				context.write(outputKey, outputValue);
			} else if (arrDelayTime == 0) {
				context.getCounter(DelayCounters.scheduled_arrival)
						.increment(1);
			} else if (arrDelayTime < 0) {
				context.getCounter(DelayCounters.early_arrival).increment(1);
			}
		} else {
			context.getCounter(DelayCounters.not_available_arrival)
					.increment(1);
		}

	}
}
