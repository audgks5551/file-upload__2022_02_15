package demo.fileupload.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 번호 지정
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String name; // 파일 이름

    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;

    @Lob // `LONGBLOB`으로 설정됨
    private byte[] content; // 업로드 파일

    public Document(Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }
}
