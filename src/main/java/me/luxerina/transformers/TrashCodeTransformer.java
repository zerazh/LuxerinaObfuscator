package me.luxerina.transformers;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import me.luxerina.Obfuscator;

public class TrashCodeTransformer extends Transformer {

    public TrashCodeTransformer(Obfuscator Obf) {
        super(Obf);
    }

	@Override
	public void visit(ClassNode classNode) {
		for (MethodNode mn : classNode.methods) {
			for (FieldNode fn : classNode.fields) {
				if (fn == null && mn == null)
					return;

				if (mn == null) {
					if ((fn.access & ACC_DEPRECATED) == 0)
						fn.access |= ACC_DEPRECATED;
				} else {
					if ((mn.access & ACC_DEPRECATED) == 0)
						mn.access |= ACC_DEPRECATED;
				}
			}
		}
	}
}
