package org.sonatype.nexus.repository.puppet.internal.stub;

import org.sonatype.nexus.security.ClientInfo;
import org.sonatype.nexus.security.ClientInfoProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 0.1.0
 */
final class ClientInfoProviderStub implements ClientInfoProvider {

  private final ThreadLocal<String> remoteIp = new ThreadLocal<>();

  private final ThreadLocal<String> userId = new ThreadLocal<>();

  @Override
  public ClientInfo getCurrentThreadClientInfo() {
    return ClientInfo.builder()
        .userId("h3lly3a!")
        .remoteIP("127.0.0.1")
        .userAgent("JUnit/4.12")
        .build();
  }

  @Override
  public void setClientInfo(final String remoteIp, final String userId) {
    this.remoteIp.set(checkNotNull(remoteIp));
    this.userId.set(checkNotNull(userId));
  }

  @Override
  public void unsetClientInfo() {
    remoteIp.remove();
    userId.remove();
  }
}
