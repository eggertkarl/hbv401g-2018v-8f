import java.util.HashMap;

public interface Initializer<T> {
    // The create function passes values from a HashMap to the constructor for class T.
    public T create(HashMap<String, Object> args);
}
