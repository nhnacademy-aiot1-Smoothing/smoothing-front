package live.smoothing.front.notification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RabbitMqMessage {

    private String title;
    private String body;
}
