FROM paketobuildpacks/run:base-cnb

# custom start
USER root

ARG package_args='--no-install-recommends'

# https://github.com/grpc-ecosystem/grpc-health-probe/releases
ARG grpc_health_probe_version='v0.4.6'

RUN echo "debconf debconf/frontend select noninteractive" | debconf-set-selections && \
  export DEBIAN_FRONTEND=noninteractive && \
  apt-get -y $package_args update && \
  apt-get -y $package_args install curl && \
  apt-get clean && \
  mkdir -p /opt/newrelic && \
  cd /usr/local/src && \
  curl -OL "https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${grpc_health_probe_version}/grpc_health_probe-linux-amd64" && \
  chmod +x grpc_health_probe-linux-amd64 && \
  mv grpc_health_probe-linux-amd64 /usr/local/bin/grpc_health_probe && \
  rm -rf \
    /usr/share/man/* /usr/share/info/* \
    /usr/share/groff/* /usr/share/lintian/* /usr/share/linda/* \
    /var/lib/apt/lists/* /tmp/*

COPY ./newrelic/newrelic.jar /opt/newrelic/newrelic.jar
COPY ./newrelic/newrelic.yml /opt/newrelic/newrelic.yml

# custom end
USER cnb
