package org.py.redisson.hello;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;

public class HelloRedissonTest {



    @Test
    public void useSentinelServers() {
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName("mymaster").addSentinelAddress("").addSentinelAddress("");
        RedissonClient client = Redisson.create(config);
        RMap<Object, Object> map = client.getMap("key");
        System.out.println(JSON.toJSONString(map, true));
        RLock mylock = client.getLock("mylock");
//        mylock = new RedissonRedLock(mylock);
        mylock.lock();
        mylock.lock();
        System.out.println(JSON.toJSONString(mylock, true));
        mylock.unlock();
        mylock.unlock();
    }


    @Test
    public void useSingleServer() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");
        RedissonClient client = Redisson.create(config);
        RMap<Object, Object> map = client.getMap("key");
        System.out.println(JSON.toJSONString(map, true));
        RLock mylock = client.getLock("mylock");
//        mylock = new RedissonRedLock(mylock);
        mylock.lock();
        mylock.lock();
        System.out.println(JSON.toJSONString(mylock, true));
        mylock.unlock();
        mylock.unlock();
    }

    /**
     * https://www.lingjie.tech/article/2021-01-31/29
     * 解决：
     *         <!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
     *         <dependency>
     *             <groupId>io.netty</groupId>
     *             <artifactId>netty-all</artifactId>
     *             <version>4.1.59.Final</version>
     *         </dependency>
     *
     * [2021-03-03 11:43:22.707+0800] [WARN ] [] [io.netty.resolver.dns.DnsServerAddressStreamProviders].<clinit>:70 Unable to load io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider, fallback to system defaults. This may result in incorrect DNS resolutions on MacOS.
     * java.lang.ClassNotFoundException: io.netty.resolver.dns.macos.MacOSDnsServerAddressStreamProvider
     * 	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
     * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
     * 	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
     * 	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
     * 	at java.lang.Class.forName0(Native Method)
     * 	at java.lang.Class.forName(Class.java:348)
     * 	at io.netty.resolver.dns.DnsServerAddressStreamProviders$1.run(DnsServerAddressStreamProviders.java:50)
     * 	at java.security.AccessController.doPrivileged(Native Method)
     * 	at io.netty.resolver.dns.DnsServerAddressStreamProviders.<clinit>(DnsServerAddressStreamProviders.java:46)
     * 	at org.redisson.connection.MasterSlaveConnectionManager.<init>(MasterSlaveConnectionManager.java:203)
     * 	at org.redisson.connection.MasterSlaveConnectionManager.<init>(MasterSlaveConnectionManager.java:155)
     * 	at org.redisson.connection.SingleConnectionManager.<init>(SingleConnectionManager.java:34)
     * 	at org.redisson.config.ConfigSupport.createConnectionManager(ConfigSupport.java:200)
     * 	at org.redisson.Redisson.<init>(Redisson.java:64)
     * 	at org.redisson.Redisson.create(Redisson.java:104)
     * 	at org.py.redisson.hello.HelloRedissonTest.test(HelloRedissonTest.java:20)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.lang.reflect.Method.invoke(Method.java:498)
     * 	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
     * 	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
     * 	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
     * 	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
     * 	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
     * 	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
     * 	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
     * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
     * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
     * 	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
     * 	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
     * 	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
     * 	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
     * 	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
     * 	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
     * 	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
     * 	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
     * 	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:69)
     * 	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
     * 	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:220)
     * 	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:53)
     */


}
