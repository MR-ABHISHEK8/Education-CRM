package in.sh.main.services;

import in.sh.main.entities.Course;
import in.sh.main.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CourseService {
    private String UPLOAD_DIR ="src/main/resources/static/uploads/";
    private String IMAGE_URL="http://localhost:8080/uploads/";
    @Autowired
    private CourseRepository courseRepository;
    public List<Course> getAllCourseDetails(){
        return courseRepository.findAll();
    }

    public void addCourse(Course course, MultipartFile courseImg) throws IOException {
        String imgName= courseImg.getOriginalFilename();
        Path imgPath= Paths.get(UPLOAD_DIR+imgName); // Path where the image will be saved
        Files.write(imgPath,courseImg.getBytes());   // Write the image file to the disk

        String imgUrl=IMAGE_URL+imgName;         //stored in the database
        course.setImageUrl(imgUrl);

        courseRepository.save(course);
    }
    public Course getCourseDetails(String courseName){
        return courseRepository.findByName(courseName);
    }
    public void updateCourseDetails(Course course){
        courseRepository.save(course);
    }

}