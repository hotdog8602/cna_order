package local;

import local.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Ship(@Payload Shipped shipped) {
        // 재고량 수정
        if (shipped.isMe()) {
            System.out.println("##### listener Ship : " + shipped.toJson());
            Optional<Order> orderOptional = orderRepository.findById(shipped.getOrderId());
            //kafka에 떠있는 order를 가져온다.
            Order order = orderOptional.get();

            System.out.println("orderOptional" + orderOptional);
            System.out.println("order" + order.getStatus());
            System.out.println("shipped.getStatus()" + shipped.getStatus());
            //해당 Ship의 상태를 가져와 셋팅 한다.
            order.setStatus(shipped.getStatus());

            //저장한다.(기존에 있으면 update, 없으면 Insert)
            orderRepository.save(order);
        }
    }
}
