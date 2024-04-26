import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger beautifulWordsLength3 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsLength4 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = new String[100_000];
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadPalindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        beautifulWordsLength3.incrementAndGet();
                    } else if (text.length() == 4) {
                        beautifulWordsLength4.incrementAndGet();
                    } else if (text.length() == 5) {
                        beautifulWordsLength5.incrementAndGet();
                    }
                }
            }
        }); threadPalindrome.start();

        Thread threadSameLetters = new Thread(() -> {
            for (String text : texts) {
                if (hasSameLetters(text)) {
                    if (text.length() == 3) {
                        beautifulWordsLength3.incrementAndGet();
                    } else if (text.length() == 4) {
                        beautifulWordsLength4.incrementAndGet();
                    } else if (text.length() == 5) {
                        beautifulWordsLength5.incrementAndGet();
                    }
                }
            }
        }); threadSameLetters.start();

        Thread threadIncreasingLetters = new Thread(() -> {
            for (String text : texts) {
                if (hasIncreasingLetters(text)) {
                    if (text.length() == 3) {
                        beautifulWordsLength3.incrementAndGet();
                    } else if (text.length() == 4) {
                        beautifulWordsLength4.incrementAndGet();
                    } else if (text.length() == 5) {
                        beautifulWordsLength5.incrementAndGet();
                    }
                }
            }
        }); threadIncreasingLetters.start();


        try {
        threadPalindrome.join();
        threadSameLetters.join();
        threadIncreasingLetters.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

        System.out.println("Красивых слов с длиной 3: " + beautifulWordsLength3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsLength4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsLength5 + " шт.");
}

public static String generateText(String letters, int length) {
    Random random = new Random();
    StringBuilder text = new StringBuilder();
    for (int i = 0; i < length; i++) {
        text.append(letters.charAt(random.nextInt(letters.length())));
    }
    return text.toString();
}

public static boolean isPalindrome(String text) {
    return text.equals(new StringBuilder(text).reverse().toString());
}

public static boolean hasSameLetters(String text) {
    return text.chars().distinct().count() == 1;
}

public static boolean hasIncreasingLetters(String text) {
    for (int i = 0; i < text.length() - 1; i++) {
        if (text.charAt(i) > text.charAt(i+1)) {
            return false;
        }
    }
    return true;
}
}