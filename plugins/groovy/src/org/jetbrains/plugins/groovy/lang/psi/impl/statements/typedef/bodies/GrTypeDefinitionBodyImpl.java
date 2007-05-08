package org.jetbrains.plugins.groovy.lang.psi.impl.statements.typedef.bodies;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrField;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariable;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariableDeclarations;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinitionBody;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod;
import org.jetbrains.plugins.groovy.lang.psi.impl.GroovyPsiElementImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dmitry.Krasilschikov
 * @date: 04.05.2007
 */
public class GrTypeDefinitionBodyImpl extends GroovyPsiElementImpl implements GrTypeDefinitionBody {
  public GrTypeDefinitionBodyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public String toString() {
    return "Type definition body";
  }

  public GrStatement[] getStatements() {
    return findChildrenByClass(GrStatement.class);
  }

  public GrField[] getFields() {
    GrVariableDeclarations[] declarations = findChildrenByClass(GrVariableDeclarations.class);
    if (declarations.length == 0) return GrField.EMPTY_ARRAY;
    List<GrField> result = new ArrayList<GrField>();
    for (GrVariableDeclarations declaration : declarations) {
      GrVariable[] variables = declaration.getVariables();
      for (GrVariable variable : variables) {
        result.add((GrField) variable);
      }
    }
    return result.toArray(GrField.EMPTY_ARRAY);
  }

  public GrMethod[] getMethods() {
    return findChildrenByClass(GrMethod.class);
  }
}
