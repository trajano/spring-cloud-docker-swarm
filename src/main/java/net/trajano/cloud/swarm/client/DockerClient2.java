package net.trajano.cloud.swarm.client;

import com.github.dockerjava.api.DockerClient;

public interface DockerClient2 extends DockerClient {

    EventsCmd2 eventsCmd2();

}
