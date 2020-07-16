import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConsumerClass {
    public static void main(String[] args) throws InputValidationException {
        ConsumerClass cc = new ConsumerClass();
        cc.readFromTopic();

    }

    public void readFromTopic() throws InputValidationException {
        List<String> messages = new ArrayList<>();

        StatusInput st_in = new StatusInput();
        Rules rules = new Rules();

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-first-consumer-group");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singleton("Hello-Kafka"));

        boolean flag = true;

        while (flag) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
//                System.out.println("Message received: " + record.value());
                if(record.value()=="exit"){
                    flag = false;
                }
                try {
                    st_in.input_parser(record.value());

                    int server_id = st_in.get_server_id();
                    Integer resource_status[] = st_in.get_resource_status();

                    String violation = rules.rule_set(resource_status);
                    System.out.println(rules.get_message(violation,server_id));
                }catch (InputValidationException e){
                    System.out.println(e.getMessage());
                }


//                messages.add(record.value());
            }
            consumer.commitAsync();
        }
    }
}
