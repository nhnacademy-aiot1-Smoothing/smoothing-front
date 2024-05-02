package live.smoothing.front.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.approval-request-queue}")
    private String approvalRequestQueue;

    @Value("${rabbitmq.outlier-detection-queue}")
    private String outlierDetectionQueue;

    @Value("${rabbitmq.topic-exchange-name}")
    private String topicExchangeName;

    @Value("${rabbitmq.approval-request-routing-key}")
    private String approvalRequestRoutingKey;

    @Value("${rabbitmq.outlier-detection-routing-key}")
    private String outlierDetectionRoutingKey;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.user-name}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.host}")
    private String host;


    @Bean
    Queue approvalRequestQueue() {

        return new Queue(approvalRequestQueue);
    }

    @Bean
    Queue outlierDetectionQueue() {

        return new Queue(outlierDetectionQueue);
    }

    @Bean
    TopicExchange exchange() {

        return new TopicExchange(topicExchangeName);
    }


    @Bean
    Binding binding1(Queue approvalRequestQueue, TopicExchange exchange) {

        return BindingBuilder.bind(approvalRequestQueue).to(exchange).with(approvalRequestRoutingKey);
    }

    @Bean
    Binding binding2(Queue outlierDetectionQueue, TopicExchange exchange) {

        return BindingBuilder.bind(outlierDetectionQueue).to(exchange).with(outlierDetectionRoutingKey);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {

        return new Jackson2JsonMessageConverter();
    }
}
