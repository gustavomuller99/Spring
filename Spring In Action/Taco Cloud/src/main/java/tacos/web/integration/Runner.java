package tacos.web.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private FileWriterGateway fileWriterGateway;
    private UpperCaseGateway upperCaseGateway;

    public Runner(FileWriterGateway fileWriterGateway, UpperCaseGateway upperCaseGateway) {
        this.fileWriterGateway = fileWriterGateway;
        this.upperCaseGateway = upperCaseGateway;
    }

    @Bean
    public MessageChannel outChannel() {
        return new DirectChannel();
    }

    @Override
    public void run(String... args) throws Exception {
        // fileWriterGateway.writeToFile("test.txt", "test");
        var result = upperCaseGateway.upperCase("test");
        System.out.println(result);
    }
}
