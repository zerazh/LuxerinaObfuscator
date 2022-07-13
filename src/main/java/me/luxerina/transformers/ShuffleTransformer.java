package me.luxerina.transformers;

import org.objectweb.asm.tree.ClassNode;

import me.luxerina.Obfuscator;

import java.util.Collections;

public class ShuffleTransformer extends Transformer {

    public ShuffleTransformer(Obfuscator obf) {
        super(obf);
    }

    @Override
    public void visit(ClassNode classNode) {
        Collections.shuffle(classNode.fields, random);
        Collections.shuffle(classNode.methods, random);
    }
}
