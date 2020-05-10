package com.jmu.server.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jmu.server.module.blackList.service.BlackListService;
import com.jmu.server.module.systemArticle.service.SystemArticleService;

/**
 * @author zhoujinmu
 * @date 2020/4/22
 * @since 1.0
 */
@Component
@Slf4j
public class Jobs {

    @Autowired
    private BlackListService blackListService;
    @Autowired
    private SystemArticleService systemArticleService;

    /**
     * 增加用户信誉度
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @Scheduled(cron = "0 0 0 ? * ?")
    public void addCredibility() {
        log.info("黑名单系统cron执行....");
        blackListService.dealCronJob();
        log.info("黑名单系统cron执行完成.");
    }

    /**
     * 发表文章
     *
     * @author zhoujinmu
     * @date 2020/4/22
     * @since 1.0
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void cronJob() {
        log.info("发布文章cron执行....");
        systemArticleService.send();
        log.info("发布文章cron执行完成.");
    }
}
