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
    
If you add setter methods to your job class that correspond to the names of keys in the JobDataMap (such as a setJobSays(String val) method for the data in the example above), then Quartz’s default JobFactory implementation will automatically call those setters when the job is instantiated, thus preventing the need to explicitly get the values out of the map within your execute method.

Triggers can also have JobDataMaps associated with them. This can be useful in the case where you have a Job that is stored in the scheduler for regular/repeated use by multiple Triggers, yet with each independent triggering, you want to supply the Job with different data inputs.

The JobDataMap that is found on the JobExecutionContext during Job execution serves as a convenience. It is a merge of the JobDataMap found on the JobDetail and the one found on the Trigger, with the values in the latter overriding any same-named values in the former.

如果你在job类中，为JobDataMap中存储的数据的key增加set方法（如在上面示例中，增加setJobSays(String val)方法），那么Quartz的默认JobFactory实现在job被实例化的时候会自动调用这些set方法，这样你就不需要在execute()方法中显式地从map中取数据了。

在Job执行时，JobExecutionContext中的JobDataMap为我们提供了很多的便利。它是JobDetail中的JobDataMap和Trigger中的JobDataMap的并集，但是如果存在相同的数据，则后者会覆盖前者的值。

下面的示例，在job执行时，从JobExecutionContext中获取合并后的JobDataMap：

    public class DumbJob implements Job {

        public DumbJob() {
        }

        public void execute(JobExecutionContext context)
          throws JobExecutionException
        {
            JobKey key = context.getJobDetail().getKey();

            JobDataMap dataMap = context.getMergedJobDataMap();  // Note the difference from the previous example

            String jobSays = dataMap.getString("jobSays");
            float myFloatValue = dataMap.getFloat("myFloatValue");
            ArrayList state = (ArrayList)dataMap.get("myStateData");
            state.add(new Date());

            System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
        }
    }
    
如果你希望使用JobFactory实现数据的自动“注入”，则示例代码为：

    public class DumbJob implements Job {

        String jobSays;
        float myFloatValue;
        ArrayList state;

        public DumbJob() {
        }

        public void execute(JobExecutionContext context)
          throws JobExecutionException
        {
            JobKey key = context.getJobDetail().getKey();

            JobDataMap dataMap = context.getMergedJobDataMap();  // Note the difference from the previous example

            state.add(new Date());

            System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
        }

        public void setJobSays(String jobSays) {
            this.jobSays = jobSays;
        }

        public void setMyFloatValue(float myFloatValue) {
            myFloatValue = myFloatValue;
        }

        public void setState(ArrayList state) {
            state = state;
        }
    }
    
#### 参考资料
http://ifeve.com/quartz-tutorial-job-jobdetail/