package net.trajano.cloud.swarm.discovery;

import com.github.dockerjava.okhttp.OkHttpDockerCmdExecFactory;
import net.trajano.cloud.swarm.client.DockerClient2;
import net.trajano.cloud.swarm.client.DockerClientImpl2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientProvider {
    @Bean
    public DockerClient2 dockerClient() {
        return DockerClientImpl2.getInstance("tcp://localhost:2375")
            .withDockerCmdExecFactory(
                new OkHttpDockerCmdExecFactory()
            );
    }
}
