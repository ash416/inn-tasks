package part1.lesson09.task01;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * The class demonstrates creating a file with a method from the console and its executing
 */
public class SomeClassDemonstrator {
    private static String className = "SomeClass";

    public static void main(String[] args) throws Exception {
        demonstrate();
    }

    private static void demonstrate() throws Exception {
        String data = Reader.read();
        WorkerImplementationCreator.create(data, className + ".java");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, className + ".java");
        useCustomClassLoader();
    }

    private static void useCustomClassLoader() throws Exception {
        ClassLoader classLoader = new SomeClassLoader(className);
        Class<?> someClass = classLoader.loadClass(className);
        Worker worker = (Worker) someClass.newInstance();
        worker.doWork();
    }
}
