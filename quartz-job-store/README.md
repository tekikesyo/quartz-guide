Job Stores
====================
    JobStore’s are responsible for keeping track of all the “work data” that you give to the scheduler: jobs, triggers, calendars, etc. 
    （JobStroe 的作用是保存所有你给 scheduler 的“工作数据”：Job、Trigger、Calendar 等等。）
### 一、RAMJobStore

RAMJobStore is the simplest JobStore to use, it is also the most performant (in terms of CPU time). RAMJobStore gets its name in the obvious way: it keeps all of its data in RAM.
The drawback is that when your application ends (or crashes) all of the scheduling information is lost
（RAMJobStore 是使用上最简单的 JobStore，它也拥有最高的性能（从 CPU 时间来计算）。从 RAMJobStore 的名字来看：它将保存所有数据在 RAM 中。这也是为什么它最轻快并且配置最简单。但是缺点是，当你的应用结束或崩溃，那么所有的 scheduler 数据将会丢失）
**Configuring Quartz to use RAMJobStore**
    
    org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore
    
### 二、JDBCJobStore

JDBCJobStore 将所有的数据通过 JDBC 保存到数据库中。因此，配置上比 RAMJobStore 稍微复杂一些，它的速度也比较慢。然而，这个性能的落后并不是特别大，尤其是当你在创建数据库表的时候为主键创建了索引的情况下。

你必须先为 Quartz 创建一组数据库表。创建表的脚本在 Quartz 发布包的“docs/dbTables”目录下。  
有一点需要注意，在这些脚本中，所有的数据库表都有“QRTZ_”前缀（例如表“QRTZ_TRIGGERS”和“QRTZ_JOB_DETAIL”）。实际上，你可以使用你喜欢的任意前缀，只需要在 Quartz 属性文件中告诉 JDBCJobStroe 使用哪个前缀即可。如果你需要在同一个数据库中为多个 scheduler 实例使用不同的数据库表保存数据，那么使用不同的前缀将会非常有用。

一旦数据库表创建好，在使用 JDBCJobStore 之前，还需要做一个决定。你需要决定你的应用程序要使用哪种类型的事务机制。如果你不需要绑定你的 scheduler 命令（例如增加和删除 Trigger）到其它的事务，那么 JosStore 可以通过 JobStoreTX 管理事务（这也是最常用的选择）

如果你需要 Quzrtz 协同其它事务一起工作（例如，在 Java EE 应用服务器中），那么你需要使用 JobStoreCMT——这样，Quartz 将会让应用程序服务器来管理事务。
最后一点是如何创建 DataSource，好让 JDBCJobStore 从你的数据库中获取连接。.DataSource 在 Quartz 属性文件中，有几种不同的配置方法。一种方式是让 Quartz 自己创建并管理 DataSource —— 这需要提供数据库的所有连接信息。另一种方式是让 Quartz 使用应用程序服务器提供的 DataSource —— 提供给 JobStore DataSource 的 JNDI 名称。详细的配置可以参考“docs/config” 目录下的配置文件。

要使用 JDBCJobStore（假设你正在使用 StdSchedulerFactory），首先需要设置 Quartz 配置项，让 JobStore 类的属性是 org.quartz.impl.jdbcjobstore.JobStoreTX 或 org.quartz.impl.jdbcjobstore.JobStoreCMT —— 具体选择哪个值取决于上面的描述。

配置 Quartz 使用 org.quartz.impl.jdbcjobstore.JobStoreTX：

    org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
    
接下来，你需要设置 JogStore 使用的 DriverDelegate。DriverDelegate 的作用是为特定的数据库执行 JDBC 的工作。StdJDBCDelegate 使用标准 JDBC 代码（和 SQL 语句）来做这个工作，如果没有其它合适的 delegate 适用于你的数据库，那么你可以试试使用 StdJDBCDelegate。其它的 delegate 可以在 “org.quartz.impl.jdbcjobstore” 包，或者在它的子包中找到。其它 delegate 包括  DB2v6Delegate （DB2 版本 6 或早期版本）、 HSQLDBDelegate （HSQLDB）、MSSQLDelegate（Microsoft SQLServer）、PostgreSQLDelegate （PostgreSQL）、WeblogicDelegate（使用 Weblogic 创建的 JDBC 驱动）、 OracleDelegate （Oracle）、等等。

一旦选择好使用哪个 delegate，那么就可以在属性文件中设置它的类名。

    org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

接下来，需要配置 JobStore 使用的数据库表前缀（前面讨论过）：

    org.quartz.jobStore.tablePrefix = QRTZ_
