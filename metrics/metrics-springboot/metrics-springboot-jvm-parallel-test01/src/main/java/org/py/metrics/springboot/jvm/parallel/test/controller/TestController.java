package org.py.metrics.springboot.jvm.parallel.test.controller;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/8/4 11:12 上午
 * @Description
 */
@RestController
public class TestController {

    public static final Counter requestTotal = Counter.build().name("counter_all").labelNames("project", "msg_pusher").help
            ("total request counter of msg-pusher").register();

    public static final Histogram histogram = Histogram.build().name("histogram_consuming").labelNames("project", "msg_pusher").help
            ("response consuming of msg_pusher").register();

    static final Counter requests = Counter.build()
            .name("requests_total").help("Total requests.")
            .labelNames("method").register();

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "id", defaultValue="1") Integer id) {
        requests.labels("get").inc();
        return String.valueOf("requests_total \t" + requests.labels("get").get());
    }




}
