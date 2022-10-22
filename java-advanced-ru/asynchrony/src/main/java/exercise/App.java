package exercise;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.concurrent.CompletionException;

public class App {

    private static Path getFullPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = App.unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt",
        "src/main/resources/output.txt");
        System.out.println(result.get());
        // END
    }

    public static CompletableFuture<Long> getDirectorySize(String dest) {
        CompletableFuture<Long> result = CompletableFuture.supplyAsync(() -> {
            Path fullPath = getFullPath(dest);
            return FileUtils.sizeOfDirectory(fullPath.toFile());
        });
        return result;
    }

    public static CompletableFuture<String> unionFiles(String source1, String source2, String output) throws Exception {
        CompletableFuture<String> firstSourceData = CompletableFuture.supplyAsync(() -> {
            String lines;
            try {
                lines = Files.readString(getFullPath(source1));
                        //Files.readAllLines(Paths.get("/Users/kmatveev/Hexlet/hexlet-assignments/java-advanced-ru/asynchrony/src/main/resources/"+source1), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return lines;
        });

        CompletableFuture<String> secondSourceData = CompletableFuture.supplyAsync(() -> {
            String lines;
            try {
                lines = Files.readString(getFullPath(source2));
                //Files.readAllLines(Paths.get("/Users/kmatveev/Hexlet/hexlet-assignments/java-advanced-ru/asynchrony/src/main/resources/"+source1), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return lines;
        });

        CompletableFuture<String> result = firstSourceData.thenCombine(secondSourceData, (first, second) -> {
                String joinedData = String.join(" ", first, second);
                byte[] strToBytes = joinedData.getBytes();
                try {
                    Files.write(getFullPath(output), strToBytes);
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
                return joinedData;
        }).exceptionally(e -> {
                System.out.println("Oops! Something went wrong - " + e.getMessage());
                return e.getMessage();
        });

        return result;
    }
}

