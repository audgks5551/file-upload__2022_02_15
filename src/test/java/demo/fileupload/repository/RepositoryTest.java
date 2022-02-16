package demo.fileupload.repository;

import demo.fileupload.DocumentRepository;
import demo.fileupload.Document;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Replace.NONE: h2 데이터베이스를 사용하지 않고 실제 데이터베이스를 사용하게 해줌
// 아래와 같이 변경
@SpringBootTest
@Transactional // 트랜잭션 처리
@Rollback(false) //  = @Commit
public class RepositoryTest {

    @Autowired
    private DocumentRepository repo;

    @PersistenceContext
    private EntityManager em;

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

        Document existDoc = em.find(Document.class, savedDoc.getId());

        System.out.println("file = " + file);

        System.out.println("byte = " + bytes);

        System.out.println("document.name = " + document.getName());
        System.out.println("document.content = " + document.getContent());
        System.out.println("document.size = " + document.getSize());

        Assertions.assertThat(existDoc.getSize()).isEqualTo(fileSize);
    }
}
