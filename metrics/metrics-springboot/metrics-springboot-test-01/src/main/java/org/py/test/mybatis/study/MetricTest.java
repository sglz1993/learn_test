package org.py.test.mybatis.study;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import io.prometheus.client.*;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * Counter
 * Counters go up, and reset when the process restarts.
 * @see io.prometheus.client.Counter
 * import io.prometheus.client.Counter;
 * class YourClass {
 *   static final Counter requests = Counter.build()
 *      .name("requests_total").help("Total requests.").register();
 *
 *   void processRequest() {
 *     requests.inc();
 *     // Your code here.
 *   }
 * }
 * Gauge
 * Gauges can go up and down.
 *
 * @see io.prometheus.client.Gauge
 *
 * class YourClass {
 *   static final Gauge inprogressRequests = Gauge.build()
 *      .name("inprogress_requests").help("Inprogress requests.").register();
 *
 *   void processRequest() {
 *     inprogressRequests.inc();
 *     // Your code here.
 *     inprogressRequests.dec();
 *   }
 * }
 * There are utilities for common use cases:
 *
 * gauge.setToCurrentTime(); // Set to current unixtime.
 * As an advanced use case, a Gauge can also take its value from a callback by using the setChild() method. Keep in mind that the default inc(), dec() and set() methods on Gauge take care of thread safety, so when using this approach ensure the value you are reporting accounts for concurrency.
 *
 * Summary
 * Summaries track the size and number of events.
 * @see io.prometheus.client.Summary
 *
 * class YourClass {
 *   static final Summary receivedBytes = Summary.build()
 *      .name("requests_size_bytes").help("Request size in bytes.").register();
 *   static final Summary requestLatency = Summary.build()
 *      .name("requests_latency_seconds").help("Request latency in seconds.").register();
 *
 *   void processRequest(Request req) {
 *     Summary.Timer requestTimer = requestLatency.startTimer();
 *     try {
 *       // Your code here.
 *     } finally {
 *       receivedBytes.observe(req.size());
 *       requestTimer.observeDuration();
 *     }
 *   }
 * }
 * There are utilities for timing code and support for quantiles. Essentially quantiles aren't aggregatable and add some client overhead for the calculation.
 *
 * class YourClass {
 *   static final Summary requestLatency = Summary.build()
 *     .quantile(0.5, 0.05)   // Add 50th percentile (= median) with 5% tolerated error
 *     .quantile(0.9, 0.01)   // Add 90th percentile with 1% tolerated error
 *     .name("requests_latency_seconds").help("Request latency in seconds.").register();
 *
 *   void processRequest(Request req) {
 *     requestLatency.time(new Runnable() {
 *       public abstract void run() {
 *         // Your code here.
 *       }
 *     });
 *
 *
 *     // Or the Java 8 lambda equivalent
 *     requestLatency.time(() -> {
 *       // Your code here.
 *     });
 *   }
 * }
 * Histogram
 * Histograms track the size and number of events in buckets. This allows for aggregatable calculation of quantiles.
 * @see io.prometheus.client.Histogram
 *
 * class YourClass {
 *   static final Histogram requestLatency = Histogram.build()
 *      .name("requests_latency_seconds").help("Request latency in seconds.").register();
 *
 *   void processRequest(Request req) {
 *     Histogram.Timer requestTimer = requestLatency.startTimer();
 *     try {
 *       // Your code here.
 *     } finally {
 *       requestTimer.observeDuration();
 *     }
 *   }
 * }
 * The default buckets are intended to cover a typical web/rpc request from milliseconds to seconds. They can be overridden with the buckets() method on the Histogram.Builder.
 *
 * There are utilities for timing code:
 *
 * class YourClass {
 *   static final Histogram requestLatency = Histogram.build()
 *      .name("requests_latency_seconds").help("Request latency in seconds.").register();
 *
 *   void processRequest(Request req) {
 *     requestLatency.time(new Runnable() {
 *       public abstract void run() {
 *         // Your code here.
 *       }
 *     });
 *
 *
 *     // Or the Java 8 lambda equivalent
 *     requestLatency.time(() -> {
 *       // Your code here.
 *     });
 *   }
 * }
 * Labels
 * All metrics can have labels, allowing grouping of related time series.
 *
 * See the best practices on naming and labels.
 *
 * Taking a counter as an example:
 *
 * class YourClass {
 *   static final Counter requests = Counter.build()
 *      .name("my_library_requests_total").help("Total requests.")
 *      .labelNames("method").register();
 *
 *   void processGetRequest() {
 *     requests.labels("get").inc();
 *     // Your code here.
 *   }
 * }
 * Registering Metrics
 * The best way to register a metric is via a static final class variable as is common with loggers.
 *
 * static final Counter requests = Counter.build()
 *    .name("my_library_requests_total").help("Total requests.").labelNames("path").register();
 * Using the default registry with variables that are static is ideal since registering a metric with the same name is not allowed and the default registry is also itself static. You can think of registering a metric, more like registering a definition (as in the TYPE and HELP sections). The metric 'definition' internally holds the samples that are reported and pulled out by Prometheus. Here is an example of registering a metric that has no labels.
 *
 * class YourClass {
 *   static final Gauge activeTransactions = Gauge.build()
 *      .name("my_library_transactions_active")
 *      .help("Active transactions.")
 *      .register();
 *
 *   void processThatCalculates(String key) {
 *     activeTransactions.inc();
 *     try {
 *         // Perform work.
 *     } finally{
 *         activeTransactions.dec();
 *     }
 *   }
 * }
 * To create timeseries with labels, include labelNames() with the builder. The labels() method looks up or creates the corresponding labelled timeseries. You might also consider storing the labelled timeseries as an instance variable if it is appropriate. It is thread safe and can be used multiple times, which can help performance.
 *
 * class YourClass {
 *   static final Counter calculationsCounter = Counter.build()
 *      .name("my_library_calculations_total").help("Total calls.")
 *      .labelNames("key").register();
 *
 *   void processThatCalculates(String key) {
 *     calculationsCounter.labels(key).inc();
 *     // Run calculations.
 *   }
 * }
 */
public class MetricTest {

    /**
     * @see Counter
     */
    static final Counter requests = Counter.build().name("requests_total_counter").help("Total requests.").labelNames("method").register();

    public void processGetRequestCounter() {
        requests.labels("get").inc();
//        requests.labels("get").inc(3);
        // Your code here.
    }

    //******************************************************************************************************************************************

    /**
     * @see Gauge
     */
    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests_gauge").help("Inprogress requests.")
            .labelNames("method").register();

    public void incProcessGetRequestGauge() {
        inprogressRequests.labels("get").inc();
        // Your code here.
    }

    public void decProcessGetRequestGauge() {
        inprogressRequests.labels("get").dec();
    }

    //******************************************************************************************************************************************

    /**
     * @see Histogram
     */
    static final Histogram histogram = Histogram.build()
            .name("inprogress_requests_histogram").help("Inprogress requests.")
            .labelNames("method").register();

    public void processGetRequestHistogram() {
        histogram.labels("get").observe(1);
        // Your code here.
    }

    //******************************************************************************************************************************************

    /**
     * @see Summary
     * @deprecated 不推荐，暂时还不了解 {@link Summary#maxAgeSeconds} 和 {@link Summary#ageBuckets} 的用法, 下面注释部分取消有异常
     * 参考：maxAgeSeconds会指定一个bucket多久之后会重置，ageBuckets指定在我们的summary滑动窗口中有多少buckets。
     */
    static final Summary summary = Summary.build().quantile(.1, 0.02).quantile(.6, .1).quantile(.99, .1)/*.ageBuckets(2).maxAgeSeconds(2)*/
            .name("inprogress_requests_summary").help("Inprogress requests.")
            .labelNames("method").register();

    List<Double> nums = Lists.newArrayList();
    public void processGetRequestSummary() {
//        summary.labels("get").observe(1);
        double v = new Random().nextDouble();
//        System.out.println(v);
        nums.add(v);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        summary.labels("get").observe(v);
//        summary.labels("get").observe(1);
        // Your code here.
    }

    @Test
    public void testSummary() {
        MetricTest metricTest = new MetricTest();
        for (int i = 0; i < 20; i++) {
            metricTest.processGetRequestSummary();
        }
        List<Collector.MetricFamilySamples> collect = summary.collect();
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(collect));
        System.out.println("*****************************************************************************************************************************");
        int index = 0;
        metricTest.nums.sort(Double::compareTo);
        for (Double num:metricTest.nums
             ) {
            System.out.println(String.format("%s\t%s", ++index, num));
        }
    }

    //******************************************************************************************************************************************

    @Test
    public void simpleTestRandom() {
        for (int i = 0; i < 10; i++) {
            double v = new Random().nextDouble();
            System.out.println(v);
        }
        System.out.println(1.1 % 1);
        System.out.println(1.1 / 1.2);
    }

}
