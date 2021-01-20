package pl.uwr.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.uwr.server.repository.StudentRepository;
import pl.uwr.server.repository.TokenRepository;

@Component
public class BootstrapData implements CommandLineRunner {

    StudentRepository studentRepository;
    TokenRepository tokenRepository;

    public BootstrapData(StudentRepository studentRepository, TokenRepository tokenRepository) {
        this.studentRepository = studentRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("App has started!");
        /*
        Token token = new Token(299008L, Calendar.getInstance(), "esandmoas231mokvsaZASmsadmk232");

        Student siemtomek = new Student(299008L, token);
        token.setStudent(siemtomek);
        studentRepository.save(siemtomek);
        tokenRepository.save(siemtomek.getToken());
        */
        //System.out.println(token.getStudent().getId());
    }
}
