package com.cxp.springboot2webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
class Springboot2WebfluxApplicationTests {

	@Test
	void contextLoads() {

	}

	public static void main(String[] args) {
	    subscribe();
	}

	public static void textFlux(){
        Flux.just("hello",111).subscribe(System.out::println);
        Flux.fromArray(new Integer[]{11,13,15})
                .subscribe(
                        System.out::print,	//数据消费方法
                        System.out::print,	//异常处理
                        ()->System.out.println("完成输出")	//方法执行完毕回调
                        ,subscription->{	//背压操作
                            subscription.request(Integer.MAX_VALUE);	//请求元素的数据
                        }
                );
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        System.out.println("=========");
        Flux.range(1, 10)
                .timeout(Flux.never(), v -> Flux.never())
                .subscribe(System.out::println);
        System.out.println("=========");
//		Flux.interval(Duration.ofSeconds(2)).doOnNext(System.out::println).blockLast();

        Flux.generate(sink -> {
            sink.next("Hello");
            sink.complete();
        }).subscribe(System.out::print);


        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::print);

        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::print);
    }

	public static void subscribe(){
		Flux.just(11).map(value->"+"+value).subscribe(new Subscriber<String>() {
		    private Subscription subscription;
			@Override
			public void onSubscribe(Subscription s) {
                subscription = s;
				subscription.request(1);
			}

			@Override
			public void onNext(String s) {
                System.out.println(s);
			}

			@Override
			public void onError(Throwable t) {
                System.out.println(t.getMessage());
			}

			@Override
			public void onComplete() {
                System.out.println("完成");
			}
		});

        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.ofMillis(100)).bufferTimeout(5,Duration.ofMillis(400)).take(5).toStream().forEach(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);

        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);

        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);

        Flux.merge(Flux.interval(Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);

	}
}
