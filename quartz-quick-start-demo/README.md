The Quartz API
======================
### 一、Quartz的核心接口:

    Scheduler - the main API for interacting with the scheduler.  
    Job - an interface to be implemented by components that you wish to have executed by the scheduler.  
    JobDetail - used to define instances of Jobs.  
    Trigger - a component that defines the schedule upon which a given Job will be executed.  
    JobBuilder - used to define/build JobDetail instances, which define instances of Jobs.  
    TriggerBuilder - used to define/build Trigger instances. 

### 二、Job
```
packageorg.quartz;   
publicinterface Job {  
    public void execute(JobExecutionContext context) throwsJobExecutionException;  
}  
```

    When the Job’s trigger fires (more on that in a moment), the execute(..) method is invoked by one of the 
    scheduler’s worker threads.The JobExecutionContext object that is passed to this method provides the job 
    instance with information about its “run-time” environment - a handle to the Scheduler that executed it,
    a handle to the Trigger that triggered the execution, the job’s JobDetail object, and a few other items. 

中文意思是：  
当Job的trigger触发时，Job的execute(..)方法就会被调度器调用。被传递到这个方法里来的
JobExecutionContext对象提供了带有job运行时的信息：执行它的调度器句柄、触发它的触发器句柄、
job的JobDetail对象和一些其他的项。  

在Quartz中，每次Scheduler执行Job时，在调用其execute()方法之前，它需要先根据JobDetail提供的Job类型创建一个Job
class的实例，在任务执行完以后，Job class的实例会被丢弃，Jvm的垃圾回收器会将它们回收。因此编写Job的具体实现时，需要注意：  
1. 它必须具有一个无参数的构造函数；
2. 它不应该有静态数据类型，因为每次Job执行完以后便被回收，因此在多次执行时静态数据没法被维护。

### 三、JobDetail

	public void startSchedule() {
		try {		
			// 1、创建一个JobDetail实例，指定Quartz
			JobDetail jobDetail = JobBuilder.newJob(NewJob.class)	// 任务执行类	
			.withIdentity("job1_1", "jGroup1")// 任务名，任务组			
			.build();	
			//2、创建Trigger
			SimpleScheduleBuilder builder=SimpleScheduleBuilder.simpleSchedule()
			.withIntervalInSeconds(5)		//设置间隔执行时间		
			.repeatSecondlyForTotalCount(5);//设置执行次数
			
			Trigger trigger=TriggerBuilder.newTrigger().withIdentity(
					"trigger1_1","tGroup1").startNow().withSchedule(builder).build();
			//3、创建Scheduler
			Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();	
			//4、调度执行
			scheduler.scheduleJob(jobDetail, trigger);	
			scheduler.start();	
		} catch (SchedulerException e) {
			e.printStackTrace();
		}	
	}
传给scheduler的是一个JobDetail实例，而不是Job,因此在创建JobDetail时，将要执行的job的类名传给了JobDetail，
所以scheduler就知道了要执行何种类型的job；每次当scheduler执行job时，在调用其execute(…)方法之前会创建该类
的一个新的实例；执行完毕，对该实例的引用就被丢弃了，实例会被垃圾回收；这种执行策略带来的一个后果是，job必须
有一个无参的构造函数（当使用默认的JobFactory时）；另一个后果是，在job类中，不应该定义有状态的数据属性，
因为在job的多次执行中，这些属性的值不会保留。

### 四、Trigger

    Trigger objects are used to trigger the execution (or ‘firing’) of jobs. When you wish to schedule a job, 
    you instantiate a trigger and ‘tune’ its properties to provide the scheduling you wish to have. Triggers 
    may also have a JobDataMap associated with them - this is useful to passing parameters to a Job that are 
    specific to the firings of the trigger. Quartz ships with a handful of different trigger types, but the 
    most commonly used types are SimpleTrigger and CronTrigger.
中文意思是：  
Trigger对象是用来触发执行Job的。当调度一个job时，我们实例一个触发器然后调整它的属性来满足job执行的条件。触发器也有一个
和它相关的JobDataMap，它是用来给被触发器触发的job传参数的。Quartz有一些不同的触发器类型，不过，用得最多的是
SimpleTrigger和CronTrigger。  
如果我们需要在给定时刻执行一次job或者在给定时刻触发job随后间断一定时间不停的执行的话，SimpleTrigger是个简单的解决办法；
如果我们想基于类似日历调度的触发job的话，比如说，在每个星期五的中午或者在每个月第10天的10：15触发job时，CronTrigger是很有用的。