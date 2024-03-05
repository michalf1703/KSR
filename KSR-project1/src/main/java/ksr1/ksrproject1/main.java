package ksr1.ksrproject1;
import ksr1.ksrproject1.DataOperations.DataExtarctor;
class App2
{
    public static void main( String[] args )
    {
        DataExtarctor dataExtarctor = new DataExtarctor();
        dataExtarctor.readfromFile("PLACES");
        System.out.println("Liczba artykułów: " + dataExtarctor.getArticles());
        System.out.println("Koniec");

    }

}
