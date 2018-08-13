package streamflow.engine.framework.ssm;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

public class SsmEnvImpl implements SsmEnv, Serializable {

    private final String value;

    public SsmEnvImpl(String value) {
        this.value = checkNotNull(value, "name");

    }
    public String value() {
        return this.value;
    }

    public int hashCode() {
        // This is specified in java.lang.Annotation.
        return (127 * "value".hashCode()) ^ value.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof SsmPod)) {
            return false;
        }

        SsmPod other = (SsmPod) o;
        return value.equals(other.value());
    }

    public String toString() {
        return "@" + SsmEnv.class.getName() + "(value=" + value + ")";
    }

    public Class<? extends Annotation> annotationType() {
        return SsmEnv.class;
    }

    private static final long serialVersionUID = 0;
}
