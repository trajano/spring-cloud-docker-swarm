package net.trajano.cloud.swarm.profile;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Enable the {@code docker} profile if it detects it is running inside a Docker
 * environment.
 *
 * @see <a href="https://stackoverflow.com/a/20012536/242042">How to determine
 *      if a process runs inside lxc/Docker?</a>
 */
public class DockerProfileApplicationListener implements
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    /**
     * Profile name.
     */
    private static final String PROFILE_NAME = "docker";

    /**
     * Pattern to locate in {@code /proc/1/cgroup} to determine if the application
     * is running in a Docker environment.
     */
    private static final Pattern DOCKER_CHECK_RE = Pattern.compile("^1:[^:]+:/docker/[0-9a-f]+");

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        if (!Arrays.asList(event.getEnvironment().getActiveProfiles()).contains(PROFILE_NAME) && isRunningInDockerContainer()) {
            event.getEnvironment().addActiveProfile(PROFILE_NAME);
        }
    }

    /**
     * Checks if the application is running inside a Docker container. Determination
     * of the Docker environment is done through checking {@code /proc/1/cgroup} to
     * see if the text {@code /docker/} is present in {@code 1:}.
     *
     * @return {@code true} if running inside a Docker container
     */
    private boolean isRunningInDockerContainer() {

        try (final Stream<String> matched = Files.newBufferedReader(Paths.get("/proc/1/cgroup"))
            .lines()
            .filter(c -> DOCKER_CHECK_RE.matcher(c).matches())) {
            return matched.findFirst().isPresent();
        } catch (IOException e) {
            return false;
        }
    }
}
