package demo.fileupload.repository;

import demo.fileupload.Entity.Document;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Replace.NONE: h2 데이터베이스를 사용하지 않고 실제 데이터베이스를 사용하게 해줌
public class RepositoryTest {

    @Autowired
    private DocumentRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testInsertDocument() throws IOException {

        String pathname = "/Users/a../sample_data/example_csv.csv";
        File file = new File(pathname);

        Document document = new Document();
        document.setName(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath()); // 파일 읽기

        document.setContent(bytes);

        long fileSize = bytes.length;
        document.setSize(fileSize);

        Document savedDoc = repo.save(document);

        Document existDoc = entityManager.find(Document.class, savedDoc.getId());

        System.out.println("file = " + file);

        System.out.println("byte = " + bytes);

        System.out.println("document.name = " + document.getName());
        System.out.println("document.content = " + document.getContent());
        System.out.println("document.size = " + document.getSize());

        Assertions.assertThat(existDoc.getSize()).isEqualTo(fileSize);
    }
}
