package cloud.fogbow.common.core.stubs;

import cloud.fogbow.common.models.CloudToken;
import cloud.fogbow.common.core.models.securityrules.SecurityRule;
import cloud.fogbow.common.core.plugins.interoperability.SecurityRulePlugin;
import cloud.fogbow.common.core.models.orders.Order;

import java.util.List;

/**
 * This class is a stub for the SecurityRulePlugin interface used for tests only.
 * Should not have a proper implementation.
 */
public class StubSecurityRulePlugin implements SecurityRulePlugin {

    public StubSecurityRulePlugin(String confFilePath) {
    }

    @Override
    public String requestSecurityRule(SecurityRule securityRule, Order majorOrder,
                                      CloudToken localUserAttributes) {
        return null;
    }

    @Override
    public List<SecurityRule> getSecurityRules(Order majorOrder,
                                               CloudToken localUserAttributes) {
        return null;
    }

    @Override
    public void deleteSecurityRule(String securityRuleId, CloudToken token) {

    }
}
