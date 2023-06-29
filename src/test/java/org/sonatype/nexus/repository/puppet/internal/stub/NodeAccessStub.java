package org.sonatype.nexus.repository.puppet.internal.stub;

import org.sonatype.nexus.common.node.NodeAccess;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 0.1.0
 */
final class NodeAccessStub implements NodeAccess {

  private final CompletableFuture<String> future = CompletableFuture.supplyAsync(this::compute);

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
