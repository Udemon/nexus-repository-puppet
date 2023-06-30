package org.sonatype.nexus.repository.puppet.internal.stub;

import org.apache.commons.io.IOUtils;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.node.NodeAccess;

import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 0.1.0
 */
final class NodeAccessStub
  extends ComponentSupport
  implements NodeAccess {

  private final CompletableFuture<String> future = CompletableFuture.supplyAsync(this::compute);

  private String compute() {
    Optional<String> hostname = Optional.empty();
    try {
      Process process = Runtime.getRuntime().exec("hostname");
      process.waitFor(5, TimeUnit.SECONDS);
      if (process.exitValue() == 0) {
        try (InputStream in = process.getInputStream()) {
          hostname = Optional.ofNullable(IOUtils.toString(in, StandardCharsets.UTF_8));
        }
      }
    } catch (Exception e) { //NOSONAR
      log.debug("Failed retrieve hostname from external process", e);
    }

    if (hostname.isPresent()) {
      return hostname.get();
    }

    try {
      hostname = Optional.ofNullable(InetAddress.getLocalHost().getHostName());
    } catch (Exception e) { //NOSONAR
      log.debug("Failed to retrieve hostname from InetAddress", e);
    }

    log.error("Failed to determine hostname, using nodeId instead.");

    return hostname
        .map(String::trim)
        .orElse(getId());
  }

  @Override
  public String getId() {
    return "D3817";
  }

  @Override
  public String getClusterId() {
    return getId();
  }

  @Override
  public boolean isClustered() {
    return false;
  }

  @Override
  public Set<String> getMemberIds() {
    return Collections.singleton(getId());
  }

  @Override
  public boolean isOldestNode() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Map<String, String> getMemberAliases() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public CompletionStage<String> getHostName() {
    return future;
  }

  @Override
  public void start() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void stop() {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
