package org.data.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class IdentityMapper extends
		Mapper {
	private Text word = new Text();
	

	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		context.write(key, new Text(value.toString().replaceAll("\r|\n", "")));
	}
}