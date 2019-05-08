package part1.lesson08.task01.way02;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class is serializer. It uses reflection.
 */
public class Serializer {

    private static final List<String> primitiveTypes = new ArrayList<>(Arrays.asList("byte", "char", "short", "int", "long",
            "float", "double",
            "boolean", "void",
            "java.lang.String"));

    public static void serialize(Object object, String file) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writeObject(object, writer);
            writer.flush();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String className = reader.readLine();
            return readObject(className, reader);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | NoSuchFieldException | InstantiationException e) {
            e.printStackTrace();
        }
        return  null;
    }


    private static void writeObject(Object object, BufferedWriter writer) throws IOException, IllegalAccessException {
        Class objectClass = object.getClass();
        writer.write(objectClass.getTypeName());
        writer.newLine();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            writePrimitiveField(object, field, writer);
        }
    }

    private static void writePrimitiveField(Object object, Field field, BufferedWriter writer) throws IOException, IllegalAccessException {
        writer.write("{");
        writer.newLine();
        writer.write(field.getName());
        writer.newLine();
        if (!primitiveTypes.contains(field.getType().getName())) {
            writeObject(field.get(object), writer);
        } else {
            writer.write(field.getType().getName());
            writer.newLine();
            writer.write(field.get(object).toString());
            writer.newLine();
        }
        writer.write("}");
        writer.newLine();
        writer.flush();
    }



    private static Object readObject(String className, BufferedReader reader) throws IOException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        Class object = Class.forName(className);
        Object instance = object.newInstance();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("{")) {
                String name = reader.readLine();
                String type = reader.readLine();
                Field field = object.getDeclaredField(name);
                if (primitiveTypes.contains(type)) {
                    field.setAccessible(true);
                    field.set(instance, castToPrimitiveType(type, reader.readLine()));
                } else {
                    field.setAccessible(true);
                    field.set(instance, readObject(type, reader));
                }
            }
        }
        return instance;
    }

    private static Object castToPrimitiveType(String type, String value) {
        switch (type) {
            case "short":
                return new Short(value);
            case "int":
                return new Integer(value);
            case "long":
                return new Long(value);
            case "float":
                return new Float(value);
            case "double":
                return new Double(value);
            case "boolean":
                return Boolean.valueOf(value);
            default:
                return value;
        }
    }
}
