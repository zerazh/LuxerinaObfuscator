package me.luxerina.transformers;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import me.luxerina.Obfuscator;

public class FinalRemoverTransformer extends Transformer {

    public FinalRemoverTransformer(Obfuscator Obf) {
        super(Obf);
    }

	@Override
	public void visit(ClassNode classNode) {
		for (FieldNode fn : classNode.fields) {
			if ((fn.access | ACC_FINAL) != 0)
				fn.access &= ~ACC_FINAL;
		}
	}
}
