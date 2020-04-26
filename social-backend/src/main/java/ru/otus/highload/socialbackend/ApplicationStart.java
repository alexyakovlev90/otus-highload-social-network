package ru.otus.highload.socialbackend;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.user.UserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Log4j2
@RequiredArgsConstructor
public class ApplicationStart implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final List<String> names = Arrays.asList(
            "Александр", "Алексей", "Анатолий", "Андрей", "Антон", "Аркадий", "Артем", "Борислав", "Вадим", "Валентин",
            "Валерий", "Василий", "Виктор", "Виталий", "Владимир", "Вячеслав", "Геннадий", "Георгий", "Григорий", "Даниил",
            "Денис", "Дмитpий", "Евгений", "Егор", "Иван", "Игорь", "Илья", "Кирилл", "Лев", "Максим", "Михаил", "Никита",
            "Николай", "Олег", "Семен", "Сергей", "Станислав", "Степан", "Федор", "Юрий"
    );

    private final List<String> lastNames = Arrays.asList(
            "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов", "Михайлов", "Новиков", "Фёдоров",
            "Морозов", "Волков", "Алексеев", "Лебедев", "Семёнов", "Егоров", "Павлов", "Козлов", "Степанов", "Николаев",
            "Орлов", "Андреев", "Макаров", "Никитин", "Захаров"
    );

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        for (int i = 0; i < 1000; i++) {
//            final int m = i;
//            List<User> users = IntStream.range(0, 1000)
//                    .mapToObj(j -> newRandomUser(j + m * 1000))
//                    .collect(Collectors.toList());
//            userRepository.saveAll(users);
//            log.info("{} users inserted",  i * 1000);
//        }
    }

    private User newRandomUser(int index) {
        Random rand = new Random(index);
        int nameInt = rand.nextInt(names.size() - 1);
        int lastNameInt = rand.nextInt(lastNames.size() - 1);
        return new User()
                .setFirstName(names.get(nameInt))
                .setLastName(lastNames.get(lastNameInt))
                .setRegisterDate(new Date())
                .setPassword("Super_secure_pass")
                .setAge(nameInt + 18)
                .setLogin("user" + index)
                .setCity("Moscow")
                .setInterest("Sex, Drugs & Rock'n'Roll");
    }

}
