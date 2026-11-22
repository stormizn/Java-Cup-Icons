package com.stormizn.cupicons;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Overrides the PSI icon used by the Project View and editor tabs.
 */
public final class JavaCupIconProvider extends IconProvider {
    @Override
    public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
        if (element instanceof PsiClass psiClass) {
            return JavaCupDetector.shouldUseCupIcon(psiClass) ? CupIcons.CUP : null;
        }
        if (element instanceof PsiJavaFile javaFile) {
            return JavaCupDetector.shouldUseCupIcon(javaFile) ? CupIcons.CUP : null;
        }
        return null;
    }
}
