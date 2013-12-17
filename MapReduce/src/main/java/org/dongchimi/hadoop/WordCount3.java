package org.dongchimi.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.LongSumReducer;
import org.apache.hadoop.mapred.lib.TokenCountMapper;

public class WordCount3 {

	 public static void main(String[] args) throws IOException {
		    // 1. configuration Mapper & Reducer of Hadoop
		    JobConf conf = new JobConf(WordCount3.class);
		    conf.setJobName("wordcount3");
		    
		    // 2. final output key type & value type
		    conf.setOutputKeyClass(Text.class);
		    conf.setOutputValueClass(LongWritable.class);
		    
		    // 3. in/output format 
		    conf.setMapperClass(TokenCountMapper.class);
		    conf.setCombinerClass(LongSumReducer.class);
		    conf.setReducerClass(LongSumReducer.class);
		    
		    // 4. set the path of file for read files
		    //    input path : args[0]
		    //    output path : args[1]
		    FileInputFormat.setInputPaths(conf, new Path(args[0]));
		    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		    
		    // 5. run job
		    JobClient client = new JobClient();
		    client.setConf(conf);
		    JobClient.runJob(conf);
		  }
}
