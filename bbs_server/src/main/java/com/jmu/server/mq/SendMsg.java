package com.jmu.server.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmu.server.config.RabbitConfig;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/3/14
 * @since 1.0
 */
@Component
public class SendMsg {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 增加浏览量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    public void addView(List<Long> content) {
        amqpTemplate.convertAndSend(RabbitConfig.ADD_VIEW_EXCHANGE, RabbitConfig.ADD_VIEW_BIND_KEY, content);
    }

    /**
     * 增加收藏量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    public void addFavorite(Long id) {
        amqpTemplate.convertAndSend(RabbitConfig.ADD_FAVORITE_EXCHANGE, RabbitConfig.ADD_FAVORITE_BIND_KEY, id);

    }

    /**
     * 减少收藏量
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    public void subtractFavorite(Long id) {
        amqpTemplate.convertAndSend(RabbitConfig.SUBTRACT_FAVORITE_EXCHANGE, RabbitConfig.ADD_FAVORITE_BIND_KEY, id);

    }

    /**
     * 增加点赞数
     *
     * @author zhoujinmu
     * @date 2020/3/23
     * @since 1.0
     */
    public void addLike(Long id) {
        amqpTemplate.convertAndSend(RabbitConfig.ADD_LIKE_EXCHANGE, RabbitConfig.ADD_LIKE_BIND_KEY, id);

    }
}