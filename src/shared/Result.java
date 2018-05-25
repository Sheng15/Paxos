package shared;

import java.io.Serializable;

public abstract class Result implements Serializable {
    private Result() {}

    public static final class Ok extends Result {
        private int index;

        public Ok(int index){
            this.index = index;
        }

        public int getIndex(){return  index;}
    }

    public static final class JoinDenied extends Result {}
}
