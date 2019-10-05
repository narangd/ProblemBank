package sykim.person.editor;

public class Program {

    private static Program instance;

    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }

    private Program() {
    }

    public MemoryManager memory = new MemoryManager();

    public void clear() {
        memory.clear();
    }
}
