package demo.fileupload.repository;


import demo.fileupload.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Override
    @Query("SELECT new Document(d.id, d.name, d.size)" +
            "FROM Document d " +
            "ORDER BY d.uploadTime DESC ")
    List<Document> findAll();
}