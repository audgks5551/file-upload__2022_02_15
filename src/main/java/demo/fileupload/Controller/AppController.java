package demo.fileupload.Controller;

import demo.fileupload.repository.DocumentRepository;
import demo.fileupload.Entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final DocumentRepository documentRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {

        List<Document> listDocs = documentRepository.findAll();

        // view로 데이터 전달
        // addAttribute()로 전달한 데이터는 새로고침을 해도 파라미터에 남아있다.
        // Model객체는 request영역만 가능하기 때문에 Session영역에 값 저장은 불가능
        model.addAttribute("listDocs", listDocs);

        return "home";
    }

    // RedirectAttributes를 사용하면 파라미터가 붙지않고 값을 전달할 수 있다.
    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("document") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes) throws IOException {

        // Spring에서 제공하는 cleanPath()를 통해서 ../ 또는 \의 내부 점들에 대해서 청소해준다
        // 예) /Users/a../sample_data\example_csv.csv => /Users/a/sample_data/example_csv.csv
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Document document = new Document();

        // 데이터 담기
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());

        documentRepository.save(document);

        // 성공 여부를 뷰에 넘겨줄 때 사용
        // rttr.addFlashAttribute로 전달한 값은 url뒤에 붙지 않는다.
        // 일회성이라 리프레시할 경우 데이터가 소멸한다.
        // 또한 2개이상 쓸 경우, 데이터는 소멸한다.
        // 따라서 맵을 이용하여 한번에 값전달해야한다.
        redirectAttributes.addFlashAttribute("message", "파일이 정상적으로 업로드 되었습니다");

        return "redirect:/";
    }
}
