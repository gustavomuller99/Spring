package tacos.web.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> textInTransformer() {
        return text -> text.toUpperCase();
    }

    @Bean
    @Transformer(inputChannel = "inChannel", outputChannel = "outChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return text -> text.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler fileWritingMessageHandler = new FileWritingMessageHandler(new File("C:\\Users\\Gustavo Muller\\Documents\\files"));
        fileWritingMessageHandler.setExpectReply(false);
        fileWritingMessageHandler.setFileExistsMode(FileExistsMode.APPEND);
        fileWritingMessageHandler.setAppendNewLine(true);
        return fileWritingMessageHandler;
    }

//    @Bean
//    public IntegrationFlow fileWriterFlow() {
//        return IntegrationFlow
//                .from(MessageChannels.direct("textInChannel"))
//                .<String, String>transform(t -> t.toUpperCase())
//                .handle(Files
//                        .outboundAdapter(new File("C:\\Users\\Gustavo Muller\\Documents\\files"))
//                        .fileExistsMode(FileExistsMode.APPEND)
//                        .appendNewLine(true))
//                .get();
//    }
}
