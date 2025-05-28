import java.io.IOException;
import java.nio.file.*;
import java.util.regex.*;

public class CommentRemover {
    public static void main(String[] args) throws IOException {
        String dirPath = "c:\\Users\\linar\\OneDrive\\Desktop\\Nisum\\Sprint 2\\Day 1";
        
        // Get all java files
        Files.walk(Paths.get(dirPath))
            .filter(path -> path.toString().endsWith(".java"))
            .forEach(path -> {
                try {
                    processFile(path);
                } catch (IOException e) {
                    System.err.println("Error processing " + path + ": " + e.getMessage());
                }
            });
    }
    
    private static void processFile(Path path) throws IOException {
        String content = Files.readString(path);
        
        // Remove filepath comment if present
        content = content.replaceAll("// filepath: [^\\n]*\\n", "");
        
        // Keep question comments (lines starting with // followed by a number and period)
        // Remove other single-line comments
        Pattern pattern = Pattern.compile("// (?!(\\d+\\.))[^\\n]*\\n");
        Matcher matcher = pattern.matcher(content);
        content = matcher.replaceAll("");
        
        // Write the modified content back to the file
        Files.writeString(path, content);
        System.out.println("Processed: " + path.getFileName());
    }
}
