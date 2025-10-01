package sia.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FluxCreatorTests {

    private FluxCreator fluxCreator;

    public FluxCreatorTests() {
        fluxCreator = new FluxCreator();
    }

    @Test
    public void testCreateFlux() {
        Flux<String> fruitFlux = Flux
                .just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void testCreateFluxFromArray() {
        String[] fruits = new String[] {
                "Apple", "Orange", "Grape", "Banana", "Strawberry" };
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void testCreateFluxRange() {
        Flux<Integer> flux = Flux.range(1, 5);

        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

    }

    @Test
    public void testFluxInterval() {
        Flux<Long> intervalFlux =
                Flux.interval(Duration.ofSeconds(1))
                        .take(5);
        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    @Test
    public void testMergeFluxes() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));

        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("Garfield")
                .expectNext("Lasagna")
                .expectNext("Kojak")
                .expectNext("Lollipops")
                .expectNext("Barbossa")
                .expectNext("Apples")
                .verifyComplete();
    }

    @Test
    public void testZipFluxes() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p ->
                        p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
                .expectNextMatches(p ->
                        p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
                .expectNextMatches(p ->
                        p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
                .verifyComplete();
    }

    @Test
    public void testZipFluxesToObject() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");

        Flux<String> zippedFlux =
                Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

        StepVerifier.create(zippedFlux)
                .expectNext("Garfield eats Lasagna")
                .expectNext("Kojak eats Lollipops")
                .expectNext("Barbossa eats Apples")
                .verifyComplete();
    }

    @Test
    public void testFirstFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));

        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);

        StepVerifier.create(firstFlux)
                .expectNext("hare")
                .expectNext("cheetah")
                .expectNext("squirrel")
                .verifyComplete();
    }

    @Test
    public void testSkipFlux() {
        Flux<String> skipFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .skip(3);

        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void testSkipSecondsFlux() {
        Flux<String> skipFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void testTakeFlux() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon",
                        "Zion", "Grand Teton")
                .take(3);

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void testTakeSecondsFlux() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon",
                        "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void testFilterFlux() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon",
                        "Zion", "Grand Teton")
                .filter(np -> !np.contains(" "));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    public void testDistinctFlux() {
        Flux<String> animalFlux = Flux.just(
                        "dog", "cat", "bird", "dog", "bird", "anteater")
                .distinct();

        StepVerifier.create(animalFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    @Test
    public void testMap() {
        Flux<Integer> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(String::length);

        StepVerifier.create(playerFlux)
                .expectNext(14)
                .expectNext(14)
                .expectNext(10)
                .verifyComplete();
    }

    @Test
    public void testFlatMap() {
        Flux<Integer> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippenn", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(String::length)
                        .subscribeOn(Schedulers.parallel())
                );

        List<Integer> playerList = Arrays.asList(14, 15, 10);

        StepVerifier.create(playerFlux)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(playerList::contains)
                .verifyComplete();
    }

    @Test
    public void testBuffer() {
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");

        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

        StepVerifier
                .create(bufferedFlux)
                .expectNext(Arrays.asList("apple", "orange", "banana"))
                .expectNext(Arrays.asList("kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void testBufferFlatMap() {
        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
                .buffer(3)
                .flatMap(x ->
                        Flux.fromIterable(x)
                                .map(String::toUpperCase)
                                .subscribeOn(Schedulers.parallel())
                                .log()
                ).subscribe();
    }

    @Test
    public void testCollectList() {
        Flux<String> fruitFlux = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");

        Mono<List<String>> fruitListMono = fruitFlux.collectList();

        StepVerifier
                .create(fruitListMono)
                .expectNext(Arrays.asList(
                        "apple", "orange", "banana", "kiwi", "strawberry"))
                .verifyComplete();
    }

    @Test
    public void testCollectMap() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Map<Character, String>> animalMapMono =
                animalFlux.collectMap(a -> a.charAt(0));

        StepVerifier
                .create(animalMapMono)
                .expectNextMatches(map ->
                        map.size() == 3 &&
                                map.get('a').equals("aardvark") &&
                                map.get('e').equals("eagle") &&
                                map.get('k').equals("kangaroo")
                )
                .verifyComplete();
    }

    @Test
    public void testAllFlux() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));

        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));

        StepVerifier.create(hasKMono)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    public void testAnyFlux() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasAMono = animalFlux.any(a -> a.contains("t"));
        StepVerifier.create(hasAMono)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> hasZMono = animalFlux.any(a -> a.contains("z"));
        StepVerifier.create(hasZMono)
                .expectNext(false)
                .verifyComplete();
    }
}
