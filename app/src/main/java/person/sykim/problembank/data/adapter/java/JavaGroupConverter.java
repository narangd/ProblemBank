package person.sykim.problembank.data.adapter.java;

import person.sykim.problembank.data.adapter.LanguageAdapter;
import person.sykim.problembank.data.adapter.SourceLineList;
import person.sykim.problembank.data.editor.Group;
import person.sykim.problembank.data.editor.SourceLine;

class JavaGroupConverter extends LanguageAdapter<Group> {
    @Override
    public SourceLine begin(Group group) {
        return new SourceLine("  private static class "+group.getName()+" {");
    }

    @Override
    public SourceLine body(Group group, SourceLineList lines) {
        return null;
    }

    @Override
    public SourceLine end(Group group) {
        return new SourceLine("  }");
    }
}
