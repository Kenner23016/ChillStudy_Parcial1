package com.libcode.crud.crud;

import com.libcode.crud.crud.users.entities.Curso;
import com.libcode.crud.crud.users.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// @Component  // ❌ Comentada para que no se ejecute
public class DataSeeder implements CommandLineRunner {

    private final CursoRepository cursoRepo;

    public DataSeeder(CursoRepository cursoRepo) {
        this.cursoRepo = cursoRepo;
    }

    @Override
    public void run(String... args) {
        if (cursoRepo.count() == 0) {
            Curso c1 = new Curso();
            c1.setNombre("Matemáticas I");

            Curso c2 = new Curso();
            c2.setNombre("Inteligencia Artificial");

            Curso c3 = new Curso();
            c3.setNombre("Programación Web");

            cursoRepo.save(c1);
            cursoRepo.save(c2);
            cursoRepo.save(c3);
        }
    }
}
