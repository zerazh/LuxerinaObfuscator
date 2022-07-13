package me.luxerina.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import me.luxerina.Obfuscator;
import me.luxerina.utils.RandomUtils;

public class JunkFieldTransformer extends Transformer {

    public JunkFieldTransformer(Obfuscator Obf) {
        super(Obf);
    }

    @Override
    public void visit(ClassNode classNode) {
        if ((classNode.access & Opcodes.ACC_INTERFACE) != 0) return;

        for (int i = random.nextInt(3) + 1; i >= 0; i--) {
            ClassNode target = RandomUtils.choice(random, obf.getClasses());
            if ((target.access & Opcodes.ACC_INTERFACE) != 0) continue;

            String name = Obfuscator.name + Math.abs(random.nextLong());
            target.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, name, "L" + classNode.name + ";", null, null));
        }
    }
}
