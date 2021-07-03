### GC和堆内存的总结

### 1、GC 英文名称Garbage Collection 中文名称为“垃圾收集”，通常我们称它为“垃圾回收”，这样更顺口。

### 2、GC操作的是堆内存的年轻代区、Eden区、from区、to区 、老年代区。from区和to区称为存活区。

### 3、GC的清除算法有：

	*标记清除算法。
	*标记-清除-整理算法，来跟踪所有的可达对象(即存活对象)，确保所有不可达对象(non-reachable objects)占用的内存都能被重用。其中包含两步:
	   Marking (标记): 遍历所有的可达对象，并在本地内存(native)中分门别类记下。
	   Sweeping (清除): 这一步保证了，不可达对象所占用的内存，在之后进行内存分配时可以重用。将所有被标记的对象(存活对象)，迁移到内存空间的起始处，消除了标记-清除算法的缺点。
	   相应的缺点就是GC暂停时间会增加，因为需要将所有对象复制到另一个地方，然后修改指向这些对象的引用。
	*标记-复制算法(Mark and Copy) 和 标记-整理算法(Mark and Compact) 十分相似: 两者都会移动所有存活的对象。
	   区别在于，标记-复制算法是将内存移动到另外一个空间: 存活区。标记-复制方法的优点在于: 标记和复制可以同时进行。缺点则是需要一个额外的内存区间，来存放所有的存活对象。
### 4、GC具体实现：

## 串行GC -XX:+UseSerialGC
```log
	java -XX:+UseSerialGC -Xms512m -Xmx512m  -Xloggc:gc.demo.log -XX:+PrintGCDetails  -XX:+PrintGCDateStamps GCLogAnalysis
```
	串行GC对年轻代使用 mark-copy（标记-复制） 算法，对老年代使用 mark-sweep-compact（标记清除-整理） 算法
	两者都是单线程的GC，不能并行处理，会触发全线暂停（STW），停止所有的应用线程。
	总结：适合单核CPU，项目几百M堆内存的JVM。

## 并行GC
```log
	java -XX:+UseParallelGC -Xms512m -Xmx512m  -Xloggc:gc.demo.log -XX:+PrintGCDetails  -XX:+PrintGCDateStamps GCLogAnalysis
```
	年轻代使用 标记-复制（mark-copy）算法 ，老年代使用 标记-清除整理（mark-sweep-compact）算法 。
	两者会触发全线STW，标记和复制/整理 使用多线程，GC时间大幅度减少。
	总结：暂停较串行GC更短，适合多核服务器，大吞吐量。

## CMS GC
```log
	java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m  -Xloggc:gc.demo.log -XX:+PrintGCDetails  -XX:+PrintGCDateStamps GCLogAnalysis
```
	CMS GC的官方名称为 “Mostly Concurrent Mark and Sweep Garbage Collector”（最大并发-标记-清除-垃圾收集器）。
	其对年轻代采用并行 STW方式的 mark-copy (标记-复制)算法 ，对老年代主要使用并发 mark-sweep (标记-清除)算法
	特点：不对老年区进行整理，使用空闲列表来管理内存空间的回收。
	标记-清楚阶段，大部分工作和应用现场可以并发执行。默认情况下，CMS并发线程等于CPU核心数的1/4。
	总结：适用多核服务器，有效降低系统延迟，有限CPU的情况下，吞度量笔并行GC差一些，大部分系统两者的吞吐和延迟相差不大。

## G1 GC
```log
	java -XX:+UseG1GC -Xms512m -Xmx512m  -Xloggc:gc.demo.log -XX:+PrintGCDetails  -XX:+PrintGCDateStamps GCLogAnalysis
```
	总结：
	STW停顿的时间和分布，可预期且可配置的。
	G1适合大内存，低延迟的场景。
	可以设置每次执行GC操作的暂停时间，单位是毫秒，默认值是200毫秒：-XX:+UseG1GC -XX:MaxGCPauseMillis=50    

## 针对串行GC、并行GC、CMS GC、G1 GC 的总结：
	单核服务器，优选串行GC。
	吞吐量优先，CUP资源有限，优先并发GC。
	低延迟，资源有限，优先CMS GC。
	4G或者大于，希望整体可控，用G1 GC。特别是超过8G，非常推荐G1 GC。

## 扩展：需要注意的jdk版本默认。
	JDK 8默认是并行GC。
	JDK 9以后的版本默认的是G1 GC，同时，ParNew + SerialOld 这种组合不被支持。






