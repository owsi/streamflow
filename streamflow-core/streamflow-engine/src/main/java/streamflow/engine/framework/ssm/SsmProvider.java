package streamflow.engine.framework.ssm;

public interface SsmProvider {
    public String getSsmParameter(String path, String defltValue);
}
