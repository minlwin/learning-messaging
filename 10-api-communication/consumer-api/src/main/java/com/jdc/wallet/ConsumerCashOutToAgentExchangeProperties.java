package com.jdc.wallet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.amqp.wallet.agent.cashout")
public class ConsumerCashOutToAgentExchangeProperties {

	private String id;
}
