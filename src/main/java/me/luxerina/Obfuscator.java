package me.luxerina;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import me.luxerina.transformers.StringEncryptionTransformer;
import me.luxerina.transformers.Transformer;
import me.luxerina.utils.StreamUtils;

public class Obfuscator {
	
    public static Random random;
    public static List<ClassNode> classes = new ArrayList<>();
    public static List<Transformer> transformers = new ArrayList<>();
    public static List<ClassNode> newClasses = new ArrayList<>();
    public static HashMap<String, byte[]> addingImages = new HashMap<>();
	public static String name = "䴷protected䴷";
	
    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("input").withRequiredArg().required().ofType(File.class);
        parser.accepts("output").withRequiredArg().required().ofType(File.class);

        OptionSet options;

        try {
            options = parser.parse(args);
        } catch (OptionException ex) {
            System.out.println("Usage: obf --input <inputjar> --output <outputjar>");
            System.out.println(ex.getMessage());
            System.exit(1);
            return;
        }

        File inputFile = (File) options.valueOf("input");
        File outputFile = (File) options.valueOf("output");

        try {
            obfuscate(inputFile, outputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static void obfuscate(File inputFile, File outputFile) throws IOException {
        random = new Random();
        
        //transformers.add(new AccessTransformer(new Obfuscator()));

        try (JarFile inputJar = new JarFile(inputFile)) {
			try (JarOutputStream out = new JarOutputStream(new FileOutputStream(outputFile))) {
			    System.out.println("Reading jar...");
			    for (Enumeration<JarEntry> iter = inputJar.entries(); iter.hasMoreElements(); ) {
			        JarEntry entry = iter.nextElement();
			        try (InputStream in = inputJar.getInputStream(entry)) {
			            if (entry.getName().endsWith(".class")) {
			                ClassReader reader = new ClassReader(in);
			                ClassNode classNode = new ClassNode();
			                reader.accept(classNode, 0);
			                classes.add(classNode);
			            } else {
			                out.putNextEntry(new JarEntry(entry.getName()));
			                StreamUtils.copy(in, out);
			            }
			        }
			    }

			    Collections.shuffle(classes, random);

			    System.out.println("Transforming classes...");
			    for (Transformer transformer : transformers) {
			        System.out.println("Running " + transformer.getClass().getSimpleName() + "...");
			        classes.forEach(transformer::visit);
			    }
			    for (Transformer transformer : transformers) {
			        transformer.after();
			    }
			    
				if (StringEncryptionTransformer.check) {
					System.out.println("Adding images files...");
					for (String key : addingImages.keySet()) {
						JarEntry entry = new JarEntry(key);
						byte[] bytes = addingImages.get(key);
						out.putNextEntry(entry);
						writeToFile(out, bytes);
					}
				}
				
			    System.out.println("Writing classes...");
			    for (ClassNode classNode : classes) {
			        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			        classNode.accept(writer);
			        out.putNextEntry(new JarEntry(classNode.name + ".class"));
			        out.write(writer.toByteArray());
			    }
			    
			    System.out.println("Writing generated classes...");
			    for (ClassNode classNode : newClasses) {
			        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			        classNode.accept(writer);
			        out.putNextEntry(new JarEntry(classNode.name + ".class"));
			        out.write(writer.toByteArray());
			    }
			}
		}
    }
    
    public static void writeToFile(JarOutputStream zipOutputStream, byte[] bytes) {
        try {
            try {
                zipOutputStream.write(bytes);
            }
            finally {
                zipOutputStream.closeEntry();
            }
        }
        catch (Exception exception) {
        }
    }
    
    public Random getRandom() {
        return random;
    }

    public List<ClassNode> getClasses() {
        return classes;
    }

    public void addNewClass(ClassNode classNode) {
        newClasses.add(classNode);
    }

}