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

#### 2.1、SimpleTrigger Misfire Instructions
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
### 三、CronTrigger
**CronTrigger** is often more useful than SimpleTrigger, if you need a job-firing schedule that recurs based on calendar-like notions, rather than on the exactly specified intervals of SimpleTrigger.（CronTriggers往往比SimpleTrigger更有用，如果您需要基于日历的概念，而非SimpleTrigger完全指定的时间间隔，复发的发射工作的时间表。）
With CronTrigger, you can specify firing-schedules such as “every Friday at noon”, or “every weekday and 9:30 am”, or even “every 5 minutes between 9:00 am and 10:00 am on every Monday, Wednesday and Friday during January”.

#### 3.1、Cron Expressions
Cron-Expressions are used to configure instances of CronTrigger. Cron-Expressions are strings that are actually made up of seven sub-expressions, that describe individual details of the schedule. These sub-expression are separated with white-space, and represent:
1. Seconds
2. Minutes
3. Hours
4. Day-of-Month
5. Month
6. Day-of-Week
7. Year (optional field)

An example of a complete cron-expression is the string “0 0 12 ? * WED” - which means “every Wednesday at 12:00:00 pm”.
（例  "0 0 12 ? * WED" 在每星期三下午12:00 执行）

每一个字段都有一套可以指定有效值，如

**Seconds (秒)**         ：可以用数字0－59 表示，

**Minutes(分)**          ：可以用数字0－59 表示，

**Hours(时)**             ：可以用数字0-23表示,

**Day-of-Month(天)** ：可以用数字1-31 中的任一一个值，但要注意一些特别的月份

**Month(月)**            ：可以用0-11 或用字符串  “JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV and DEC” 表示

**Day-of-Week(每周)**：可以用数字1-7表示（1 ＝ 星期日）或用字符口串“SUN, MON, TUE, WED, THU, FRI and SAT”表示

**“/”**：为特别单位，表示为“每”如“0/15”表示每隔15分钟执行一次,“0”表示为从“0”分开始, “3/20”表示表示每隔20分钟执行一次，“3”表示从第3分钟开始执行

**“?”**：表示每月的某一天，或第周的某一天

**“L”**：用于每月，或每周，表示为每月的最后一天，或每个月的最后星期几如“6L”表示“每月的最后一个星期五”

**“W”**：表示为最近工作日，如“15W”放在每月（day-of-month）字段上表示为“到本月15日最近的工作日”

**“#”**：是用来指定“的”每月第n个工作日,例 在每周（day-of-week）这个字段中内容为"6#3" or "FRI#3" 则表示“每月第三个星期五”

### 3.2、CronTrigger Misfire Instructions
**Misfire Instruction Constants of CronTrigger**

    MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
    MISFIRE_INSTRUCTION_DO_NOTHING
    MISFIRE_INSTRUCTION_FIRE_NOW