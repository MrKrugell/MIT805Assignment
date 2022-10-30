import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class ZoneCount{

	public static class ZoneMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	  public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

			  String valueString = value.toString();
			  String[] ZoneData = valueString.split(",");
			  output.collect(new Text(ZoneData[7]), new IntWritable(1));
    }
  }

  
  public class ZoneReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text t_key, Iterator<IntWritable> values, OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException {
			Text key = t_key;
			int frequencyOfZone = 0;
			while (values.hasNext()) {
				// replace type of value with the actual type of our value
				IntWritable value = (IntWritable) values.next();
				frequencyOfZone += value.get();
				
			}
			output.collect(key, new IntWritable(frequencyOfZone));
		}
	}

  public static void main(String[] args) {
		JobClient my_client = new JobClient();
		// Create a configuration object for the job
		JobConf job_conf = new JobConf(ZoneCount.class);

		// Set a name of the Job
		job_conf.setJobName("HRZoneTimes");

		// Specify data type of output key and value
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(IntWritable.class);

		// Specify names of Mapper and Reducer Class
		job_conf.setMapperClass(ZoneMapper.class);
		job_conf.setReducerClass(ZoneReducer.class);

		// Specify formats of the data type of Input and output
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);

		// Set input and output directories using command line arguments, 
		//arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.
		
		FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));

		my_client.setConf(job_conf);
		try {
			// Run the job 
			JobClient.runJob(job_conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	    

  }
