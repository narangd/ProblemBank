package person.sykim.problembank.data.adapter;

import java.util.ArrayList;

import sykim.person.editor.SourceLine;

public class SourceLineList extends ArrayList<SourceLine> {
    @Override
    public boolean add(SourceLine sourceLine) {
        if (sourceLine != null) {
            return super.add(sourceLine);
        } else {
            return false;
        }
    }
}
