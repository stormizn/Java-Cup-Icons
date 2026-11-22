package com.stormizn.cupicons;

import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.Nullable;

/**
 * Decides whether a Java file should use the coffee-cup icon.
 *
 * <p>Only top-level classes and records qualify. Interfaces, enums, and annotation types keep
 * the icons supplied by the active icon theme (for example Atom Material Icons).</p>
 */
public final class JavaCupDetector {
    private JavaCupDetector() {
    }

    public static boolean shouldUseCupIcon(@Nullable PsiFile file) {
        if (!(file instanceof PsiJavaFile javaFile)) {
            return false;
        }
        try {
            for (PsiClass psiClass : javaFile.getClasses()) {
                if (isClassOrRecord(psiClass)) {
                    return true;
                }
            }
        } catch (IndexNotReadyException ignored) {
            // During indexing, fall back to the theme's icon. The tree/tab will refresh later.
        }
        return false;
    }

    public static boolean shouldUseCupIcon(@Nullable PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }
        // Only top-level declarations; a nested class inside an interface must not affect the file.
        PsiElement parent = psiClass.getParent();
        if (!(parent instanceof PsiJavaFile)) {
            return false;
        }
        return isClassOrRecord(psiClass);
    }

    private static boolean isClassOrRecord(PsiClass psiClass) {
        return psiClass.isRecord()
                || (!psiClass.isInterface() && !psiClass.isEnum() && !psiClass.isAnnotationType());
    }
}
