JobDataMap
=======================
官方文档：

    The JobDataMap can be used to hold any amount of (serializable) data objects which you wish to have made 
    available to the job instance when it executes. JobDataMap is an implementation of the Java Map interface,
    and has some added convenience methods for storing and retrieving data of primitive types.
    
中文翻译：

    JobDataMap中可以包含不限量的（序列化的）数据对象，在job实例执行的时候，可以使用其中的数据；JobDataMap是Java Map接口
    的一个实现，额外增加了一些便于存取基本类型的数据的方法。
    
#### 使用
putting data into the JobDataMap while defining/building the JobDetail, prior to adding the job to the scheduler:
 
    // define the job and tie it to our DumbJob class
    JobDetail job = newJob(DumbJob.class)
      .withIdentity("myJob", "group1") // name "myJob", group "group1"
      .usingJobData("jobSays", "Hello World!")
      .usingJobData("myFloatValue", 3.141f)
      .build();
      
Here’s a quick example of getting data from the JobDataMap during the job’s execution:

    public class DumbJob implements Job {

        public DumbJob() {
        }
    
        public void execute(JobExecutionContext context)
          throws JobExecutionException
        {
          JobKey key = context.getJobDetail().getKey();
    
          JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    
          String jobSays = dataMap.getString("jobSays");
          float myFloatValue = dataMap.getFloat("myFloatValue");
    
          System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
        }
    }