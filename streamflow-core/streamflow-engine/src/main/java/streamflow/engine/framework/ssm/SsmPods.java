package streamflow.engine.framework.ssm;

public class SsmPods {
    public static SsmPod podProp(String name) {
        return new SsmPodImpl(name);
    }
}
