public class TextJustifier {
    public static String makeLine(String[] words, int start, int end, int maxWidth, boolean isLastLine)
    {
        if (isLastLine || start == end)
        {
            String line = words[start];
            for (int i = start + 1; i <= end; i++)
            {
                line += " " + words[i];
            }
            while (line.length() < maxWidth) {
                line += " ";
            }
            return line;
        }

        int totalLength = 0;
        for (int i = start; i <= end; i++) {
            totalLength += words[i].length();
        }

        int totalSpaces = maxWidth - totalLength;
        int numGaps = end - start;

        String result = new String();

        for (int i = start; i < end; i++) {
            result += words[i];
            int spacesToAdd = totalSpaces / numGaps;
            if (i - start < totalSpaces % numGaps) {
                spacesToAdd++;
            }
            for (int j = 0; j < spacesToAdd; j++) {
                result += " ";
            }
        }
        result += words[end];

        return result;
    }

    public static String[] justifyText(String[] words, int maxWidth) {
        int i = 0;
        int n = words.length;
        int lineCount = 0;

        while (i < n) {
            int j = i;
            int lineLength = 0;

            while (j < n && lineLength + words[j].length() + (j - i) <= maxWidth) {
                lineLength += words[j].length();
                j++;
            }
            lineCount++;
            i = j;
        }

        String[] result = new String[lineCount];
        i = 0;
        int lineIndex = 0;

        while (i < n) {
            int j = i;
            int lineLength = 0;

            while (j < n && lineLength + words[j].length() + (j - i) <= maxWidth) {
                lineLength += words[j].length();
                j++;
            }

            boolean isLastLine = j == n;

            result[lineIndex] = makeLine(words, i, j - 1, maxWidth, isLastLine);
            lineIndex++;
            i = j;
        }

        return result;
    }
}
