package com.jmu.client.mq;

import com.jmu.client.config.RabbitConfig;
import com.jmu.client.mapper.ArticleInfoMapper;
import com.jmu.client.module.article.mapper.ArticleInfoExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/3/14
 * @since 1.0
 */
@Component
@Slf4j
public class ReceiveMsg {

    @Autowired
    private ArticleInfoExtMapper articleInfoExtMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    /**
     * === 在RabbitMQ上创建queue,exchange,binding 方法二：直接在@RabbitListener声明 begin ===
     * 接收
     *
     * @param content
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConfig.ADD_VIEW_QUEUE + "3", durable = "true", autoDelete = "true"),
                    exchange = @Exchange(value = RabbitConfig.ADD_VIEW_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitConfig.ADD_VIEW_BIND_KEY)
    )
    public void addArticleView(List<Long> content) {
        log.info("[ReceiveMsg] addArticleView msg: " + content);
        int result = articleInfoExtMapper.addArticleView(content);
        if (result <= 0) {
            log.error("增加文章浏览量的mq消费失败！");
        }
    }

    /**
     * 增加文章收藏量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConfig.ADD_FAVORITE_QUEUE + "3", durable = "true", autoDelete = "true"),
                    exchange = @Exchange(value = RabbitConfig.ADD_FAVORITE_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitConfig.ADD_FAVORITE_BIND_KEY)
    )
    public void addFavorite(Long content) {
        log.info("[ReceiveMsg] addFavorite content:{} ", content);
        int result = articleInfoMapper.updateFavoriteNum(content, 1);
        if (result <= 0) {
            log.error("增加文章收藏量的mq消费失败！");
        }
    }

    /**
     * 减少文章收藏量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConfig.SUBTRACT_FAVORITE_QUEUE + "3", durable = "true", autoDelete = "true"),
                    exchange = @Exchange(value = RabbitConfig.SUBTRACT_FAVORITE_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitConfig.SUBTRACT_FAVORITE_BIND_KEY)
    )
    public void subtractFavorite(Long content) {
        log.info("[ReceiveMsg] subtractFavorite content:{} ", content);
        int result = articleInfoMapper.updateFavoriteNum(content, -1);
        if (result <= 0) {
            log.error("减少文章收藏量的mq消费失败！");
        }
    }

    /**
     * 增加文章点赞量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConfig.ADD_LIKE_QUEUE + "3", durable = "true", autoDelete = "true"),
                    exchange = @Exchange(value = RabbitConfig.ADD_LIKE_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = RabbitConfig.ADD_LIKE_BIND_KEY)
    )
    public void addLike(Long content) {
        log.info("[ReceiveMsg] addLike content:{} ", content);
        int result = articleInfoMapper.updateLikeNum(content, 1);
        if (result <= 0) {
            log.error("增加文章点赞量的mq消费失败！");
        }
    }


}
