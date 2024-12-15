package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utc.edu.thesis.domain.dto.UploadImage;

import java.io.IOException;

@Service
public interface CloudinaryService {
    UploadImage uploadFile(MultipartFile multipartFile) throws IOException;

}
