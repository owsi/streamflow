package streamflow.engine.framework.ssm;

public class SsmEnvs {
    public static SsmEnv envProp(String name) {
        return new SsmEnvImpl(name);
    }
}
