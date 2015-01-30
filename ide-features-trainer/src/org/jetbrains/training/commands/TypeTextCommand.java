package org.jetbrains.training.commands;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jdom.Element;
import org.jetbrains.training.ActionsRecorder;
import org.jetbrains.training.Command;
import org.jetbrains.training.Lesson;
import org.jetbrains.training.graphics.DetailPanel;

/**
 * Created by karashevich on 30/01/15.
 */
public class TypeTextCommand extends Command {

    public TypeTextCommand(){
        super(Command.CommandType.TYPETEXT);
    }

    @Override
    public void execute(Element element, final Lesson lesson, final Editor editor, final AnActionEvent e, Document document, String target, final DetailPanel infoPanel) throws InterruptedException {

        updateDescription(element, infoPanel, editor);
        updateButton(element, infoPanel, editor);


        final String finalText = (element.getContent().isEmpty() ? "" : element.getContent().get(0).getValue());
        boolean isTyping = true;
        final int[] i = {0};
        final int initialOffset = editor.getCaretModel().getOffset();

        while (isTyping) {
            Thread.sleep(20);
            final int finalI = i[0];
            WriteCommandAction.runWriteCommandAction(e.getProject(), new Runnable() {
                @Override
                public void run() {
                    editor.getDocument().insertString(finalI + initialOffset, finalText.subSequence(i[0], i[0] + 1));
                    editor.getCaretModel().moveToOffset(finalI + 1 + initialOffset);
                }
            });
            isTyping = (++i[0] < finalText.length());
        }
    }

}