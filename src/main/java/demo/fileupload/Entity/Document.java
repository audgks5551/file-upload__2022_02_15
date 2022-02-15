package demo.fileupload.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Getter @Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String name; // 파일 이름

    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;

    private byte[] content; // 업로드 파일

}
