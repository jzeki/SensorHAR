package data;

public class SensorsSample {
    private SensorSample a;
    private SensorSample g;
    private SensorSample m;
    private SensorSample o;
    private Long t;
    private SensorsSample(Builder builder) {
        this.a = builder.a;
        this.g = builder.g;
        this.m = builder.m;
        this.setO(builder.o);
    }
    public SensorSample getO() {
        return o;
    }
    public void setO(SensorSample o) {
        this.o = o;
    }
    public Long getT() {
        return t;
    }
    public void setT(Long t) {
        this.t = t;
    }
    public SensorSample getA() {
        return a;
    }
    public void setA(SensorSample a) {
        this.a = a;
    }
    public SensorSample getG() {
        return g;
    }
    public void setG(SensorSample g) {
        this.g = g;
    }
    public SensorSample getM() {
        return m;
    }
    public void setM(SensorSample m) {
        this.m = m;
    }

    public static class Builder {
        private SensorSample a;
        private SensorSample g;
        private SensorSample m;
        private SensorSample o;
        public Builder a(SensorSample a) {
            this.a = a;
            return this;
        }
        public Builder g(SensorSample g) {
            this.g = g;
            return this;
        }
        public Builder m(SensorSample m) {
            this.m = m;
            return this;
        }
        public Builder o(SensorSample o) {
            this.o = o;
            return this;
        }
        public SensorsSample build() {
            return new SensorsSample(this);
        }
    }
}
