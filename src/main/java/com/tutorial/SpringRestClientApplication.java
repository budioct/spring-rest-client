package com.tutorial;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;


@SpringBootApplication
public class SpringRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestClientApplication.class, args);
    }

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl("https://jsonplaceholder.typicode.com/todos/").build(); // Builder baseUrl(String baseUrl) // Konfigurasikan URL dasar untuk permintaan yang dilakukan melalui klien.
    }

    record Todo(Long userId, Long id, String title, Boolean completed) {
    }

    interface TodoClient {
        @GetExchange
        List<Todo> todos();
    }

    @Bean
    TodoClient todoClient(RestClient restClient) {
        // HttpServiceProxyFactory // factory untuk membuat proksi klien dari antarmuka layanan HTTP dengan @HttpExchangemetode.
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(
                RestClientAdapter.create(restClient)).build(); // builderFor(HttpExchangeAdapter exchangeAdapter) // Kembalikan pembuat yang diinisialisasi dengan klien tertentu.

        return factory.createClient(TodoClient.class); // <S> S createClient(Class<S> serviceType) // return proksi yang mengimplementasikan antarmuka layanan HTTP tertentu untuk melakukan permintaan HTTP dan mengambil respons melalui klien HTTP.
    }

    @Bean
    ApplicationRunner applicationRunner(TodoClient todoClient) {
        return args -> {

//            ResponseEntity<Todo> response = restClient.get()
//                    .uri("/{id}/", 10)
//                    .retrieve()
//                    .toEntity(Todo.class); // restclient return responseentity
            /**
             * endpoint: https://jsonplaceholder.typicode.com/todos/10
             * get detail
             * {
             * userId: 1,
             * id: 10,
             * title: "illo est ratione doloremque quia maiores aut",
             * completed: true
             * }
             * response :
             * Todo[userId=1, id=10, title=illo est ratione doloremque quia maiores aut, completed=true]
             */

            var response = todoClient.todos(); // restclient return list
            System.out.println(response);
            /**
             * endpoint: https://jsonplaceholder.typicode.com/todos
             * get list
             * [
             *  {
             *  userId: 1,
             *  id: 10,
             *  title: "illo est ratione doloremque quia maiores aut",
             *  completed: true
             *  },
             *  {
             *  userId: 1,
             *  id: 10,
             *  title: "illo est ratione doloremque quia maiores aut",
             *  completed: true
             *  },
             * ]
             * response :
             * [ Todo[userId=1, id=1, title=delectus aut autem, completed=false], Todo[userId=1, id=2, title=quis ut nam facilis et officia qui, completed=false]]
             */
        };
    }

}
