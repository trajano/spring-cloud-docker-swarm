package net.trajano.cloud.swarm.discovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.okhttp.OkHttpDockerCmdExecFactory;
import net.trajano.cloud.swarm.client.*;
import org.junit.jupiter.api.Test;

class DockerClientTest {

    @Test
    void foo() throws Exception {
        final DockerClient2 dockerClient = DockerClientImpl2.getInstance("tcp://localhost:2375")
            .withDockerCmdExecFactory(
                new OkHttpDockerCmdExecFactory()
            );
        final EventsCmd2 ljservice = dockerClient.eventsCmd2()
            .withEventTypeFilter(EventType2.SERVICE);
        System.out.println(ljservice.getFilters());
        ObjectMapper objectMapper = new ObjectMapper();
        ljservice
            .exec(new ResultCallbackTemplate<ResultCallback<Event>, Event>() {

                @Override
                public void onNext(Event event) {
                    System.out.println(event.getAction());
                    String serviceID = event.getActor().getId();
                    final Service service = dockerClient.inspectServiceCmd(serviceID).exec();
                    System.out.println(service);
//                    try {
//                        objectMapper.writer().writeValue(System.out, service);
//                        System.out.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onComplete() {
                    System.out.println("complete");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println("error" + e);
                }
            });

        System.out.println(dockerClient.listImagesCmd().exec());
        Thread.sleep(10000);
    }

    @Test
    void net() throws Exception {
        final DockerClient2 dockerClient = DockerClientImpl2.getInstance("tcp://localhost:2375")
            .withDockerCmdExecFactory(
                new OkHttpDockerCmdExecFactory()
            );
        System.out.println(dockerClient.inspectNetworkCmd().withNetworkId("ljservice").exec());
    }

    @Test
    public void filterBuilder() {
        FiltersBuilder2 fb = new FiltersBuilder2();
        System.out.println(fb.withEventTypes(EventType2.SERVICE, EventType2.SECRET).build());
    }
}