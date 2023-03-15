
import java.util.Objects;

public class InstanceDec {
    public Instance i;
    public int c;

    public InstanceDec(Instance i, int c){
        this.i=i;
        this.c=c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstanceDec)) return false;
        InstanceDec instanceDec = (InstanceDec) o;
        return c == instanceDec.c && i.equals(instanceDec.i);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, c);
    }
}
