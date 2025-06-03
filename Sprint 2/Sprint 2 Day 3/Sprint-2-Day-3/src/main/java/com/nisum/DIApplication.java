package com.nisum;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.nisum.model.TextEditor;
import com.nisum.model.Document;

/**
 * Main application class to demonstrate dependency injection.
 * This class shows both constructor and setter injection using Spring.
 * It also demonstrates SOLID principles in action.
 */
public class DIApplication {
    public static void main(String[] args) {
        // Load the Spring configuration file
        ApplicationContext context =
            new ClassPathXmlApplicationContext("classpath:WEB-INF/spring/servlet-context.xml");

        // Constructor injection example
        TextEditor editorConstructor = (TextEditor) context.getBean("textEditorConstructor");
        System.out.println("\nUsing constructor injected TextEditor:");
        editorConstructor.spellCheck("Constructor injection test");

        // Setter injection example
        TextEditor editorSetter = (TextEditor) context.getBean("textEditorSetter");
        System.out.println("\nUsing setter injected TextEditor:");
        editorSetter.spellCheck("Setter injection test");

        // Full dependency injection example (with DocumentDAO)
        // This demonstrates Dependency Inversion Principle in action
        TextEditor completeEditor = (TextEditor) context.getBean("completeTextEditor");
        System.out.println("\nUsing complete TextEditor with DocumentDAO:");
        Document doc = completeEditor.createDocument("Test Document", "This is a test document created using the DAO layer.");
        System.out.println("Created document: " + doc);
    }
}
