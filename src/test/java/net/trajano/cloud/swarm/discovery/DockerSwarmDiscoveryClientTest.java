package net.trajano.cloud.swarm.discovery;

import net.trajano.cloud.swarm.client.DockerClient2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DockerSwarmDiscoveryClient.class})
class DockerSwarmDiscoveryClientTest {
    @MockBean
    private DockerClient2 dockerClient;

    @Autowired
    private DockerSwarmDiscoveryClient dockerSwarmDiscoveryClient;

    @Autowired
    private Environment environment;

    @Test
    void description() {
        assertThat(dockerSwarmDiscoveryClient).isNotNull();
    }

}