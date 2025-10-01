public class Main {
    public static void main(String[] args) {
        IFindMinUniqCharsWord strFinder = new StrFinderLambdaAndStreamApi();

        String result1 = strFinder.findMinUniqueCharsWord("apple banana kiwi orange");
        String result2 = strFinder.findMinUniqueCharsWord("aaa bb c dddd");
        String result3 = strFinder.findMinUniqueCharsWord("hello world java code");

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);

    }
}


