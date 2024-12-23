package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTokenizerTest {
    @Test
    public void testTokenizer() throws FileNotFoundException {
        Reader reader = new FileReader("stopwords.txt");
        TextTokenizer textTokenizer = new TextTokenizer(reader);

        List<String> result = new ArrayList<>();
        result.add("b");
        result.add("c");
        String input = "a b if c";
        assertEquals(result, textTokenizer.tokenize(input));
    }

}
