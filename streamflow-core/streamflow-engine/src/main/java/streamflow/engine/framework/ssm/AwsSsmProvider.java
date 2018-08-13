package streamflow.engine.framework.ssm;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsSsmProvider implements SsmProvider {
    private static final Logger logger = LoggerFactory.getLogger(AwsSsmProvider.class);

    private final AWSSimpleSystemsManagement ssm;

    public AwsSsmProvider() {
        ssm = getSsm();
    }

    // Simple helper to satisfy the final keyword on ssm
    // finals have to be initialized only once and can not fail
    private AWSSimpleSystemsManagement getSsm() {
        try {
            return AWSSimpleSystemsManagementClientBuilder.standard().withCredentials(new AWSCredentialsProviderChain()).build();
        } catch (Throwable t) {
            logger.warn("Unable to create AWS SSM client", t);
        }
        return null;
    }

    @Override
    public String getSsmParameter(String path, String defltValue) {
        if (ssm != null ) {
            try {
                GetParameterResult result = ssm.getParameter(new GetParameterRequest().withName(path).withWithDecryption(true));
                return result.getParameter().getValue();
            } catch (Throwable t) {
                logger.warn("Using default value for key " + path, t);
            }
        }
        return defltValue;
    }
}
