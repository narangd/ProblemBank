package sykim.person.editor;

import java.util.HashSet;

public class NameSpaceManager {
    private static NameSpaceManager instance;
    public static NameSpaceManager getInstance() {
        if (instance == null) {
            instance = new NameSpaceManager();
        }
        return instance;
    }


    HashSet<String> nameMap = new HashSet<>();

    private NameSpaceManager() {

    }

    public void load(Function function) {
        clear();
        Program.getInstance().clear();
        function.onExecute();
        for (String name : Program.getInstance().memory.iteratorVariable()) {
            nameMap.add(name);
        }
        Program.getInstance().clear();
    }

    public void add(String name) {
        nameMap.add(name);
    }

    public boolean contains(String name) {
        return nameMap.contains(name);
    }

    public void clear() {
        nameMap.clear();
    }
}
