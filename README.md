# Displaying-data-from-the-website
Kurs Języki Programowania - Lab04 - stopień I, rok II, semestr III - Informatyka Techniczna

Podczas laboratorium należy zbudować aplikację o przyjaznym, graficznym interfejsie użytkownika, pozwalającą na przeglądanie danych udostępnionych w Internecie poprzez otwarte API. Aplikacja ma być zbudowana z wykorzystaniem klas SWING bądź JavaFX. Wyświetlane na jej interfejsie dane pochodzić mają z portalu GIOŚ. 

Opis wystawionego tam API znajduje się na stronie: [https://powietrze.gios.gov.pl/pjp/content/api](https://powietrze.gios.gov.pl/pjp/content/api)  
Aplikacja powinna 
- pozwalać na przeglądanie danych stacji pomiarowych
- wybór stanowiska pomiarowego
- wizualizować dane pomiarowe (wykres w osi czasu) oraz indeks jakości powietrza (kolory odpowiadające indeksowy). 
Proszę zastanowić się, jak będzie wyglądał interfejs użytkownika (czy użyć tabel, czy też zwykłych pól tekstowych; czy użyć okna dialogowe, czy też zakładki; itp.). Proszę też rozważyć przypadek niepełnych/niedostępnych danych.  
  
Aplikacja ma 
- być modułowa (tj. ma powstać z wykorzystaniem JPMS (ang. Java Platform Module System)),
- powinna posiadać module-info.java z odpowiednimi wpisami,
- należy zadbać o właściwe zmodyfikowanie ścieżek modułów oraz komendy uruchomieniowej.

Stosunkowo prosto do aplikacji modułowych podłącza się klasy SWING. Trudniej jest z JavaFX - to osobny runtime, wymagający osobnej instalacji, a później, odpowiedniej parametryzacji wywołania wirtulnej maszyny (z modyfikacją ścieżki modułów oraz wskazaniem wykorzystanych modułów: --module-path "\path to javafx\lib" --add-modules javafx.controls,javafx.fxml). 

Podczas implementacji będzie trzeba zająć się pozyskiwaniem danych poprzez API oraz ich parsowaniem.
- Pozyskanie danych można zaimplementować na różne sposoby, korzystając z różnych klas dostępnych w JDK oraz w zewnętrznych bibliotekach. 
- Na stronie: [https://www.wiremock.io/post/java-http-client-comparison](https://www.wiremock.io/post/java-http-client-comparison) przedstawiono porównanie popularnych klientów, zaś na stronie: [https://www.baeldung.com/java-9-http-client](https://www.baeldung.com/java-9-http-client) opisano, jak użyć HTTPClient (dostępny w JDK).
- Parsowanie danych może odbywać się z wykorzystaniem bibliotek do przetwarzania danych w formacie JSON. Krótki tutorial dotyczący tego tematu zajduje się pod adresem: [https://www.baeldung.com/java-json](https://www.baeldung.com/java-json).

Implementując aplikację proszę rozdzielić ją na dwie części, budowane do osobnych plików jar:
- lab04_client - ta część odpowiadać ma za logikę biznesową (wysyłanie zapytań, parsowanie odpowiedzi),
- lab04_gui - ta część odpowiadać ma za graficzny interfejs użytkownika (wizualizuje dane, korzysta z lab04_client).
