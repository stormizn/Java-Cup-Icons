package com.stormizn.cupicons;

import com.intellij.ide.FileIconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Supplies the cup icon for Java class/record files outside of the Project View, most notably
 * editor tabs and the Recent Files popup.
 */
public final class JavaCupFileIconProvider implements FileIconProvider {
    @Override
    public @Nullable Icon getIcon(@NotNull VirtualFile file, int flags, @Nullable Project project) {
        if (project == null || project.isDisposed() || !file.isValid()) {
            return null;
        }
        if (!"java".equalsIgnoreCase(file.getExtension())) {
            return null;
        }

        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        return JavaCupDetector.shouldUseCupIcon(psiFile) ? CupIcons.CUP : null;
    }
}
