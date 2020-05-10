package com.jmu.client.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoujinmu (jinmu.zhou@ucarinc.com)
 * @date 2020/3/14
 * @since 1.0
 */
@Configuration
public class RabbitConfig {

    // 增加浏览量
    public final static String ADD_VIEW_QUEUE = "add-view-queue";
    public final static String ADD_VIEW_EXCHANGE = "add-view-exchange";
    public static final String ADD_VIEW_BIND_KEY = "add-view-bind-key";

    // 增加收藏
    public final static String ADD_FAVORITE_QUEUE = "add-favorite-queue";
    public final static String ADD_FAVORITE_EXCHANGE = "add-favorite-exchange";
    public static final String ADD_FAVORITE_BIND_KEY = "add-favorite-bind-key";

    // 减少收藏
    public final static String SUBTRACT_FAVORITE_QUEUE = "subtract-favorite-queue";
    public final static String SUBTRACT_FAVORITE_EXCHANGE = "subtract-favorite-exchange";
    public static final String SUBTRACT_FAVORITE_BIND_KEY = "subtract-favorite-bind-key";

    // 增加点赞
    public final static String ADD_LIKE_QUEUE = "add-like-queue";
    public final static String ADD_LIKE_EXCHANGE = "add-like-exchange";
    public static final String ADD_LIKE_BIND_KEY = "add-like-bind-key";


    @Bean
    Queue queue() {
        return new Queue(ADD_VIEW_QUEUE, false);
    }

    /**
     * 定义交换机
     *
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(ADD_VIEW_EXCHANGE);
    }

    /**
     * 定义绑定
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ADD_VIEW_BIND_KEY);
    }

    @Bean
    MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
