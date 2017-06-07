Trigger
========================
### 一、Common Trigger Attributes
Here is a listing of properties common to all trigger types:

1. **The “jobKey”** property indicates the identity of the job that should be executed when the trigger fires.
2. **The “startTime”** property indicates when the trigger’s schedule first comes into affect. The value is a 
    java.util.Date object that defines a moment in time on a given calendar date. For some trigger types, 
    the trigger will actually fire at the start time, for others it simply marks the time that the schedule 
    should start being followed. This means you can store a trigger with a schedule such as “every 5th 
    day of the month” during January, and if the startTime property is set to April 1st, it will be a few 
    months before the first firing.  
3. **The “endTime”** property indicates when the trigger’s schedule should no longer be in effect. In other words, 
    a trigger with a schedule of “every 5th day of the month” and with an end time of July 1st will fire for 
    it’s last time on June 5th.
4. **Priority** when you have many Triggers (or few worker threads in your Quartz thread pool), Quartz may not 
    have enough resources to immediately fire all of the Triggers that are scheduled to fire at the same time. 
    In this case, you may want to control which of your Triggers get first crack at the available Quartz worker threads.
5. **Misfire Instructions** A misfire occurs if a persistent trigger “misses” its firing time because of the 
    scheduler being shutdown, or because there are no available threads in Quartz’s thread pool for executing the job. 
    The different trigger types have different misfire instructions available to them. 
6. **Calendars** Quartz Calendar objects (not java.util.Calendar objects) can be associated with triggers at the 
    time the trigger is defined and stored in the scheduler. Calendars are useful for excluding blocks of time 
    from the the trigger’s firing schedule.
    
### 二、SimpleTrigger
**SimpleTrigger** should meet your scheduling needs if you need to have a job execute exactly once at a specific moment in time, or at a specific moment in time followed by repeats at a specific interval. For example, if you want the trigger to fire at exactly 11:23:54 AM on January 13, 2015, or if you want it to fire at that time, and then fire five more times, every ten seconds.

**the properties of a SimpleTrigger include**: a start-time, and end-time, a repeat count, and a repeat interval. 
（SimpleTrigger的属性包括：开始时间、结束时间、重复次数以及重复的间隔。）

### 三、SimpleTrigger Misfire Instructions
SimpleTrigger has several instructions that can be used to inform Quartz what it should do when a misfire occurs.

**Misfire Instruction Constants of SimpleTrigger：**

    MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
    MISFIRE_INSTRUCTION_FIRE_NOW
    MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
    MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
    
所有的trigger都有一个Trigger.MISFIRE_INSTRUCTION_SMART_POLICY策略可以使用，该策略也是所有trigger的默认策略。

如果使用smart policy，SimpleTrigger会根据实例的配置及状态，在所有MISFIRE策略中动态选择一种Misfire策略。