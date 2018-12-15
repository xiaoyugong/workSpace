/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mahout.Kmeans;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.ClassUtils;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Job extends AbstractJob {

	private static final Logger log = LoggerFactory.getLogger(Job.class);

	private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "data";

	private Job() {
	}

	public static void main(String[] args) throws Exception {
		
		if (args.length > 0) {
			log.info("Running with only user-supplied arguments");
			for (String s : args) {
				System.out.println(s);
			}
			ToolRunner.run(new Configuration(), new Job(), args);
		} else {
			log.info("Running with default arguments");
			Path output = new Path(
					"hdfs://172.18.200.135:8020/outOFgxy/outputKmeans");
			Configuration conf = new Configuration();
			HadoopUtil.delete(conf, output);
			run(conf, new Path("hdfs://172.18.200.135:8020/input/kmeansdata"),
					output, new EuclideanDistanceMeasure(), 6, 0.5, 10);
		}
	}

	public int run(String[] args) throws Exception {
		addInputOption();
		addOutputOption();
		addOption(DefaultOptionCreator.distanceMeasureOption().create());
		addOption(DefaultOptionCreator.numClustersOption().create());
		addOption(DefaultOptionCreator.t1Option().create());
		addOption(DefaultOptionCreator.t2Option().create());
		addOption(DefaultOptionCreator.convergenceOption().create());
		addOption(DefaultOptionCreator.maxIterationsOption().create());
		addOption(DefaultOptionCreator.overwriteOption().create());

		Map<String, List<String>> argMap = parseArguments(args);
		if (argMap == null) {
			return -1;
		}

		Path input = getInputPath();
		Path output = getOutputPath();
		HadoopUtil.delete(getConf(), output);
		String measureClass = getOption(DefaultOptionCreator.DISTANCE_MEASURE_OPTION);
		if (measureClass == null) {
			measureClass = SquaredEuclideanDistanceMeasure.class.getName();
		}
		double convergenceDelta = Double
				.parseDouble(getOption(DefaultOptionCreator.CONVERGENCE_DELTA_OPTION));
		int maxIterations = Integer
				.parseInt(getOption(DefaultOptionCreator.MAX_ITERATIONS_OPTION));
		if (hasOption(DefaultOptionCreator.OVERWRITE_OPTION)) {
			HadoopUtil.delete(getConf(), output);
		}
		DistanceMeasure measure = ClassUtils.instantiateAs(measureClass,
				DistanceMeasure.class);
		if (hasOption(DefaultOptionCreator.NUM_CLUSTERS_OPTION)) {
			int k = Integer
					.parseInt(getOption(DefaultOptionCreator.NUM_CLUSTERS_OPTION));
			run(getConf(), input, output, measure, k, convergenceDelta,
					maxIterations);
		} else {
			double t1 = Double
					.parseDouble(getOption(DefaultOptionCreator.T1_OPTION));
			double t2 = Double
					.parseDouble(getOption(DefaultOptionCreator.T2_OPTION));
			run(getConf(), input, output, measure, t1, t2, convergenceDelta,
					maxIterations);
		}
		return 0;
	}

	/**
	 * Run the kmeans clustering job on an input dataset using the given the
	 * number of clusters k and iteration parameters. All output data will be
	 * written to the output directory, which will be initially deleted if it
	 * exists. The clustered points will reside in the path
	 * <output>/clustered-points. By default, the job expects a file containing
	 * equal length space delimited data that resides in a directory named
	 * "testdata", and writes output to a directory named "output".
	 * 
	 * @param conf
	 *            the Configuration to use
	 * @param input
	 *            the String denoting the input directory path
	 * @param output
	 *            the String denoting the output directory path
	 * @param measure
	 *            the DistanceMeasure to use
	 * @param k
	 *            the number of clusters in Kmeans
	 * @param convergenceDelta
	 *            the double convergence criteria for iterations
	 * @param maxIterations
	 *            the int maximum number of iterations
	 */
	@SuppressWarnings("static-access")
	public static void run(Configuration conf, Path input, Path output,
			DistanceMeasure measure, int k, double convergenceDelta,
			int maxIterations) throws Exception {
		Path directoryContainingConvertedInput = new Path(output,
				DIRECTORY_CONTAINING_CONVERTED_INPUT);
		log.info("Preparing Input");
		InputDriver.runJob(input, directoryContainingConvertedInput,
				"org.apache.mahout.math.RandomAccessSparseVector");
		log.info("Running random seed to get initial clusters");
		Path clusters = new Path(output, "random-seeds");
		clusters = RandomSeedGenerator.buildRandom(conf,
				directoryContainingConvertedInput, clusters, k, measure);
		log.info("Running KMeans with k = {}", k);
		KMeansDriver.run(conf, directoryContainingConvertedInput, clusters,
				output, convergenceDelta, maxIterations, true, 0.0, false);
		// run ClusterDumper
		Path outGlob = new Path(output, "clusters-*-final");
		Path clusteredPoints = new Path(output, "clusteredPoints");
		log.info(
				"Dumping out clusters from clusters: {} and clusteredPoints: {}",
				outGlob, clusteredPoints);
		ClusterDumper clusterDumper = new ClusterDumper(outGlob,
				clusteredPoints);
		String[] args = new String[8];
		args[0] = "-i";
		args[1] = "hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/clusters-*-final";
		args[2] = "-o";
		args[3] = "hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/clusters-final";
		args[4] = "-p";
		args[5] = "hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/clusteredPoints";
		args[6] = "-of";
		args[7] = "TEXT";		
		clusterDumper.main(args);
	}

	/**
	 * Run the kmeans clustering job on an input dataset using the given
	 * distance measure, t1, t2 and iteration parameters. All output data will
	 * be written to the output directory, which will be initially deleted if it
	 * exists. The clustered points will reside in the path
	 * <output>/clustered-points. By default, the job expects the a file
	 * containing synthetic_control.data as obtained from
	 * http://archive.ics.uci.
	 * edu/ml/datasets/Synthetic+Control+Chart+Time+Series resides in a
	 * directory named "testdata", and writes output to a directory named
	 * "output".
	 * 
	 * @param conf
	 *            the Configuration to use
	 * @param input
	 *            the String denoting the input directory path
	 * @param output
	 *            the String denoting the output directory path
	 * @param measure
	 *            the DistanceMeasure to use
	 * @param t1
	 *            the canopy T1 threshold
	 * @param t2
	 *            the canopy T2 threshold
	 * @param convergenceDelta
	 *            the double convergence criteria for iterations
	 * @param maxIterations
	 *            the int maximum number of iterations
	 */
	public static void run(Configuration conf, Path input, Path output,
			DistanceMeasure measure, double t1, double t2,
			double convergenceDelta, int maxIterations) throws Exception {
		Path directoryContainingConvertedInput = new Path(output,
				DIRECTORY_CONTAINING_CONVERTED_INPUT);
		log.info("Preparing Input");
		InputDriver.runJob(input, directoryContainingConvertedInput,
				"org.apache.mahout.math.RandomAccessSparseVector");
		log.info("Running Canopy to get initial clusters");
		Path canopyOutput = new Path(output, "canopies");
		CanopyDriver.run(new Configuration(),
				directoryContainingConvertedInput, canopyOutput, measure, t1,
				t2, false, 0.0, false);
		log.info("Running KMeans");
		KMeansDriver.run(conf, directoryContainingConvertedInput, new Path(
				canopyOutput, Cluster.INITIAL_CLUSTERS_DIR + "-final"), output,
				convergenceDelta, maxIterations, true, 0.0, false);
		// run ClusterDumper
		ClusterDumper clusterDumper = new ClusterDumper(new Path(output,
				"clusters-*-final"), new Path(output, "clusteredPoints"));
		clusterDumper.printClusters(null);
	}
}
