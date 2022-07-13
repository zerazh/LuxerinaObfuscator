package me.luxerina.transformers;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import joptsimple.internal.Strings;
import me.luxerina.Obfuscator;
import me.luxerina.utils.StringUtils;

public class CrasherTransformer extends Transformer {
	
    private static final String EMPTY_STRINGS = Strings.repeat('\n', 50000);
	
    public CrasherTransformer(Obfuscator obf) {
		super(obf);
	}

	@Override
    public void visit(ClassNode classNode) {
        if (Modifier.isInterface(classNode.access)) return;

        if (classNode.signature == null) classNode.signature = StringUtils.crazyString(obf, 50);
        for (MethodNode method : classNode.methods) {
            if (method.invisibleAnnotations == null) method.invisibleAnnotations = new ArrayList<>();
            for (int i = 0; i < 50; i++) method.invisibleAnnotations.add(new AnnotationNode(EMPTY_STRINGS));
        }
    }

}
