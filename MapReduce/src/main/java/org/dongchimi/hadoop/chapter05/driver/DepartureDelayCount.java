package org.dongchimi.hadoop.chapter05.driver;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.dongchimi.hadoop.chapter05.DelayCountReducer;
import org.dongchimi.hadoop.chapter05.DepartureDelayCountMapper;

public class DepartureDelayCount {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		if (args.length != 2) {
			System.err.println("Usage : DepartureDelayCount <input> <output>");
			System.exit(2);
		}
		Configuration conf = new Configuration();
		conf.set("mapred.child.java.opts", "-Xms256m -Xmx2g -XX:+UseSerialGC");
		conf.set("mapred.job.map.memory.mb", "4096");
		conf.set("mapred.job.reduce.memory.mb", "1024");
		conf.set("mapreduce.job.maps", "2");
		conf.set("mapreduce.job.reduces", "1");
		
		Job job = new Job(conf, "DepartureDelayCount");
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setJarByClass(DepartureDelayCount.class);
		job.setMapperClass(DepartureDelayCountMapper.class);
		job.setReducerClass(DelayCountReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.waitForCompletion(true);
	}
}
