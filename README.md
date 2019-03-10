# Spring Cloud Docker Swarm

This provide:
* a `DiscoveryClient` that works with Docker Swarm Services
* an `ApplicationContextInitializer` that would enable `docker` profile if it detects that the container is running in a Docker environment