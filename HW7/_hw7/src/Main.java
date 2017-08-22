import java.util.*;

public class Main {
    public static void main(String args[]) {
        System.out.println("Q1Test\n------");
        Q1Test();
        System.out.println("Q2Test\n------"); // put,get and remove all work as normally... Two same keys are not allowed !!!
        Q2Test();
        System.out.println("\nMY TEST Q1\n------\n"); //all methods where implemented. Was short of time to teat all...
        MyTestQ1();
    }

    public static void MyTestQ1(){
        NavigableMap<Integer, Character>  map = new BinaryNavMap<>();
        for (int i = 0; i < 20; ++i)
            map.put(i, (char) ('a' + i));

        printTest(map);

        map = map.subMap(5,true,15,true);
        printTest(map);

        map = map.descendingMap();
        printTest(map);

        map = map.subMap(15,true,5,true);
        printTest(map);
    }

    public static void printTest(NavigableMap<Integer,Character> map) {
        System.out.println("Map size = " + map.size() + "; Map: " + map );
        Map.Entry<Integer,Character> firstEntry = map.firstEntry();
        Map.Entry<Integer,Character> lastEntry = map.lastEntry();
        System.out.println("firstEntry = " + firstEntry);
        System.out.println("lastEntry = " + lastEntry);
        System.out.println("ceilingEntry of firstEntry - 1= " + map.ceilingEntry(firstEntry.getKey() - 1));
        System.out.println("floorEntry of lastEntry + 1 = " + map.floorEntry(lastEntry.getKey() + 1));
        System.out.println("higherEntry of firstEntry + 1 = " + map.higherEntry(firstEntry.getKey() + 1));
        System.out.println("lowerEntry of lastEntry - 1 = " + map.lowerEntry(lastEntry.getKey()-1));
        System.out.println("get lastEntry = " + map.get(lastEntry.getKey()));
        System.out.println("get lastEntry + 10 = " + map.get(lastEntry.getKey()+10));
        System.out.println("contains firstEntry = " + map.containsKey(firstEntry.getKey()));
        System.out.println("contains firstEntry - 10 = " + map.containsKey(firstEntry.getKey() - 10));
        System.out.println("pollFirstEntry " + map.pollFirstEntry());
        System.out.println("pollLastEntry " + map.pollLastEntry());
        System.out.println("Map size = " + map.size() + "; Map: " + map );
        NavigableSet<Integer> set = map.navigableKeySet();
        System.out.println("navigableKeySet size = " + set.size() + "; navigableKeySet: " + set);
        set = set.descendingSet();
        System.out.println("descendingSet size = " + set.size() + "; descendingSet: " + set);
        set = set.subSet(set.first()-1,true,set.last()+1,true);
        System.out.println("subSet from first - 1 to last + 1 size = " + set.size() + "; subSet: " + set);
        System.out.println("subSet pollFirst " + set.pollFirst());
        System.out.println("subSet pollLast " + set.pollLast());
        System.out.println("subSet size = " + set.size() + "; navigableKeySet: " + set);
        System.out.println("Map size = " + map.size() + "; Map: " + map );
        System.out.println("---");
    }

    public static Boolean Q1Test() {

        NavigableMap<String, String> turkey = new BinaryNavMap<>();
        turkey.put("uskudar", "istanbul");
        turkey.put("kadıkoy", "istanbul");
        turkey.put("cekirge", "bursa");
        turkey.put("gebze", "kocaeli");
        turkey.put("niksar", "tokat");
        turkey.put("kecıoren", "ankara");
        turkey.put("aksaray", "istanbul");
        turkey.put("foca", "izmir");
        turkey.put("manavgat", "antalya");
        turkey.put("kahta", "adıyaman");
        turkey.put("biga", "canakkale");

        System.out.println("The original set odds is " + turkey);
        NavigableMap<String, String> m = turkey.subMap("gebze",true,"uskudar" , false); // from and to order was changed, "gebze" < "uskudar", otherwise exception
        System.out.println("The ordered set m is " + m);
        System.out.println("The first entry is " + turkey.firstEntry());

        //you should write more test function to show your solution
        //your test must contain all methods to get full points!!!
        //you also may need to overwrite some methods to provide BST RULES

        /* *some links to help you

           https://docs.oracle.com/javase/8/docs/api/java/util/NavigableMap.html
           https://docs.oracle.com/javase/8/docs/api/java/util/AbstractMap.html

        * */
        return Boolean.TRUE;

    }

    public static Boolean Q2Test() {
        HashMap<String, String> turkey = new HashTableChaining<String, String>();
        System.out.println("Size before any put is : " + turkey.size());
        System.out.println("isEmpty: " + turkey.isEmpty());
        turkey.put("edremit", "balikesir");
        turkey.put("edremit", "van");
        turkey.put("kemalpasa", "bursa");
        turkey.put("kemalpasa", "izmir");
        turkey.put("ortakoy", "istanbul");//we assume a district
        turkey.put("ortakoy", "aksaray");
        turkey.put("ortakoy", "corum");
        turkey.put("kecıoren", "ankara");
        turkey.put("pinarbasi", "kastamonu");
        turkey.put("pinarbasi", "kayseri");
        turkey.put("eregli", "konya");
        turkey.put("eregli", "tekirdag");
        turkey.put("eregli", "zonguldak");
        turkey.put("golbasi", "adıyaman");
        turkey.put("golbasi", "ankara");
        turkey.put("biga", "canakkale");
        System.out.println("Size after puts is : " + turkey.size());
        System.out.println("isEmpty: " + turkey.isEmpty());
        System.out.println("get edremit: " + turkey.get("edremit"));
        System.out.println("get grozny: " + turkey.get("grozny"));
        System.out.println("remove ortakoy: " + turkey.remove("ortakoy"));
        System.out.println("remove grozny: " + turkey.remove("grozny"));
        System.out.println("get eregli: " + turkey.get("eregli"));
        System.out.println("remove pinarbasi: " + turkey.remove("pinarbasi"));

        return Boolean.TRUE;
    }


}
