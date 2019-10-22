import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CronTrigger {
    public static void main(String[] args){
        //初始化job
        JobDetail job = JobBuilder.newJob(SimpleMail.class)// 创建 jobDetail 实例，绑定 Job 实现类
                .withIdentity("ccy", "group1")//指明job名称、所在组名称
                .build();
        //定义规则
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("ccy", "group1")//triggel名称、组
                .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))//每隔5s执行
                .build();
        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            System.out.println("start job...");
            //把作业和触发器注册到任务调度中
            scheduler.scheduleJob(job, trigger);
            //启动
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}