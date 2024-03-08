package ksr1.ksrproject1;
import ksr1.ksrproject1.DataOperations.DataExtarctor;
class App2
{
    public static void main( String[] args )
    {
        DataExtarctor dataExtarctor = new DataExtarctor();
        dataExtarctor.readFromFile();
        System.out.println("Liczba artykułów z odpowiednią etykietą PLACE: " + dataExtarctor.getArticlesCount());
        System.out.println("Koniec");


    }

}